package api.calls.resources.external;

import parsing.entities.LocalTest;
import parsing.entities.Session;
import parsing.entities.User;
import parsing.entities.UserWithTests;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Path("/scores")
public class ScoresResource {

    @Inject
    EntityManager em;

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserWithTests> getScores() {

        List<UserWithTests> retVal = new ArrayList<>();

        List<User> users = User.getEntityManager().createQuery("select distinct u from LocalTest t inner join User u on t.userId = u.id where t.assignmentId =:assignment", User.class)
                .setParameter("assignment", (long)163824).getResultList();

        TypedQuery<Integer> highestSessionNumberQuery = Session.getEntityManager().createQuery("select max(s.sessionId) from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId =: userId and lt.assignmentId = 163824", Integer.class);

        TypedQuery<Float> query  = LocalTest.getEntityManager().createQuery("select lt.pointsGained from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId =: userId and lt.assignmentId = 163824 and s.sessionId =:sessionId", Float.class);

        for (User user : users) {
            List<Float> points = query.setParameter("userId", user.id).setParameter("sessionId",highestSessionNumberQuery.setParameter("userId", user.id).getSingleResult()).getResultList();

            float sumOfPoints = 0;

            for (Float aFloat : points) {
                sumOfPoints = sumOfPoints + aFloat;
            }

            UserWithTests userWithTests = UserWithTests.of(user, sumOfPoints);

            retVal.add(userWithTests);
        }

        retVal = retVal.stream().sorted((Comparator.comparing(o -> o.getUser().getFullName()))).collect(Collectors.toList());

        return retVal;
    }

}
