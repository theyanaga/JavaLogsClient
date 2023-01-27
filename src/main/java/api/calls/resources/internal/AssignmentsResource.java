package api.calls.resources.internal;

import parsing.entities.Season;
import parsing.relations.Assignment;
import parsing.relations.Course;
import parsing.pojos.AssigmentCreationDTO;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/assignment-information")
public class AssignmentsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Assignment> getAssignments() {
        return Assignment.listAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Assignment createAssignment(AssigmentCreationDTO assignmentCreationDTO) {
        Season season = Season.valueOf(assignmentCreationDTO.getSeason());
        Course course = Course.of(assignmentCreationDTO.getClassName(), season, assignmentCreationDTO.getYear());
        return Assignment.of(assignmentCreationDTO.getAssignmentNumber(), course);
    }

}
