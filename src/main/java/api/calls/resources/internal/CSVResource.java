package api.calls.resources.internal;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import parsing.entities.RowFromServer;
import parsing.entities.User;
import parsing.relations.Assignment;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/csv")
public class CSVResource {

    /**
     *
     * Use this method to create CSV files out of the data in the database
     *
     * @return
     * @throws IOException
     */
    @GET
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/real-data")
    public String createCSVFileForUser()
        throws IOException {

        List<User> users = User.getEntityManager().createQuery("select distinct t.user from RowFromServer t where t.assignment =:assignment", User.class)
                .setParameter("assignment", Assignment.findById((long)163824)).getResultList();

        for (User user : users) {

            List<RowFromServer> serverRows = RowFromServer.getEntityManager().createQuery("select r from RowFromServer r where r.assignment=:assignment and r.user=:user order by r.time", RowFromServer.class)
                    .setParameter("assignment", Assignment.findById((long) 163824)).setParameter("user", user).getResultList();

            // Uncomment this if you would like to use headers!
            ///String headers = "#,Time,%Passes,Change,Test,Pass,Partial,Fail,Untested,SessionNumber,SessionRunNumber,IsSuite,SuiteTests,PrerequisiteTests,ExtraCreditTests,TestScores,FailFromPreReq\n";

            FileWriter out = new FileWriter("./src/main/resources/student-logs/" + user.firstName + "-" + user.lastName + ".Assignment1Suite.csv");
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT);

           // printer.printRecord((Object[]) headers.split( ","));

            List<RowFromServer> filteredServerRows = new ArrayList<>();

            filteredServerRows.add(serverRows.get(0));

            int currRowValue = Integer.parseInt(filteredServerRows.get(0).getRow());

            for (int i = 1; i < serverRows.size(); i++) {
                int currRow = Integer.parseInt(serverRows.get(i).getRow());

                if (currRow > currRowValue) {
                    filteredServerRows.add(serverRows.get(i));
                    currRowValue = currRow;
                }
            }

            for (RowFromServer row : filteredServerRows) {
                String[] csvRow = row.createCSVLineFromRow().split(",");
                printer.printRecord((Object[]) csvRow);
            }

            printer.close(true);
            out.close();
        }

        return "Files Created";
    }

}
