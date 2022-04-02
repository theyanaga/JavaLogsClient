package api.calls.helpers;

import parsing.entities.LocalTest;
import parsing.entities.TestStatus;
import parsing.entities.User;
import parsing.entities.projections.LocalTestNameAndStatus;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/cluster")
public class ClusterResource {

    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Map<Integer, List<User>> getClusters() {

        List<Long> userIds = (List<Long>)em.createQuery("select u.id from User u").getResultList();

        Query query = em.createQuery("select tn.testNameId from TestNameRelation tn inner join TestStatusRelation ts on tn.testId = ts.testId inner join TestUserRelation tu" +
                " on tu.testId = tn.testId where tu.userId =:userId and ts.status =:status");

        Map<Integer, List<User>> map = new HashMap<>();

        for (Long id : userIds) {
            List<Long> testIds = query.setParameter("userId", id).setParameter("status", TestStatus.PASS).getResultList();
            Arrays.sort(testIds.toArray());
            System.out.println(testIds);
            int hash = testIds.hashCode();
            if (map.containsKey(hash)) {
                map.get(hash).add(User.findById(id));
            }
            else {
                List<User> users = new ArrayList<>();
                users.add(User.findById(id));
                map.put(hash, users);
            }
        }
        return map;
    }

}
