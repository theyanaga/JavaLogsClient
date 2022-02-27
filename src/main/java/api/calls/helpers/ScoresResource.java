package api.calls.helpers;

import parsing.entities.User;
import parsing.entities.UserWithTests;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/scores")
public class ScoresResource {

    @Inject
    EntityManager em;

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserWithTests> getScores() {

        List<User> users = User.listAll();

        List<UserWithTests> retVal = new ArrayList<>();

        Query scoreQuery = em.createQuery("select t.pointsGained from LocalTest t where t.userId = :userId");

        for (User user : users) {
            List points = scoreQuery.setParameter("userId", user.id).getResultList();

            float sumOfPoints = 0;

            for (Object p : points) {
                sumOfPoints = sumOfPoints + (float) p;
            }

            UserWithTests userWithTests = UserWithTests.of(user, sumOfPoints);

            retVal.add(userWithTests);
        }

        return retVal;
    }

}
