package api.calls.helpers;

import parsing.entities.RowFromServer;
import parsing.entities.User;
import parsing.relations.Assignment;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.ZonedDateTime;
import java.util.List;

@Path("/times")
public class TimeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTimes() {

        List<User> users = User.getEntityManager().createQuery("select distinct t.user from RowFromServer t where t.assignment =:assignment", User.class)
                .setParameter("assignment", Assignment.findById((long)163824)).getResultList();

        for (User user : users) {
            List<ZonedDateTime> times = RowFromServer.getEntityManager().createQuery("select r.time from RowFromServer r where r.assignment=:assignmentId and r.user=:user order by r.time", ZonedDateTime.class)
                    .setParameter("assignmentId", Assignment.findById((long)163824))
                    .setParameter("user", user)
                    .getResultList();

        }

        return "potato";
    }
}
