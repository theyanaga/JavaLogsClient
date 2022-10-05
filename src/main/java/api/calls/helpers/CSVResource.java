package api.calls.helpers;

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
import java.util.List;

@Path("/csv")
public class CSVResource {

    @GET
    @Path("/test")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public String createCSV()
        throws IOException {

        FileWriter out = new FileWriter("/Users/felipeyanaga/UNC/Felipe-CSV/potato.csv");
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT);

        String headers = "#,Time,%Passes,Change,Test,Pass,Partial,Fail,Untested,SessionNumber,SessionRunNumber,IsSuite,SuiteTests,PrerequisiteTests,ExtraCreditTests,TestScores,FailFromPreReq\n";

        String[] individualHeaders = headers.split(",");

        printer.printRecord((Object[]) individualHeaders);
        printer.printRecord((Object[]) "0,Tue Feb 08 08:34:18 EST 2022,2,2,PartitionerClassFactoryAndObject,PartitionerClass+ ,PartitionerFactory+ ,A2ConfigurationProvided+ PartitionerObject+ ,A2AccessModifiersMatched A2ExpectedCalls A2ExpectedGetters A2ExpectedSetters A2ExpectedSignatures A2InterfaceAsType A2MnemonicNames A2NamedConstants A2NoCheckstyleWarnings A2PublicMethodsOverride Barrier BarrierClass EarlyJoin JoinerClass LateJoin MultiThreadSumBoundedBuffer MultiThreadSumBulkPartialReduce MultiThreadSumMultiplePartialReduce MultiThreadSumMultipleRoundSynchronization MultiThreadSumResult MultiThreadSumThreads MultiThreadTokenCountResult StandAloneTokenCountPartition1Reduce StandAloneTokenCountPartition2Reduce StandAloneTokenCountPartition3Reduce SumBarrier SumJoiner TokenCountBarrier TokenCountBoundedBuffer TokenCountBulkPartialReduce TokenCountJoiner TokenCountMultiplePartialReduce TokenCountMultipleRoundSynchronization TokenCountThreads ,0,0,true,PartitionerClass PartitionerFactory PartitionerObject ,A2ConfigurationProvided , ,PartitionerClass-(5.0/5.0) PartitionerFactory-(4.0/10.0) PartitionerObject-(0.0/10.0)\n".split(","));


        printer.close(true);
        out.close();

        return "This got here!";
    }

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

            String headers = "#,Time,%Passes,Change,Test,Pass,Partial,Fail,Untested,SessionNumber,SessionRunNumber,IsSuite,SuiteTests,PrerequisiteTests,ExtraCreditTests,TestScores,FailFromPreReq\n";

            FileWriter out = new FileWriter("/Users/felipeyanaga/UNC/Felipe-CSV/JavaThreads/" + user.firstName + "-" + user.lastName + ".csv");
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT);

            printer.printRecord((Object[]) headers.split( ","));

            for (RowFromServer row : serverRows) {
                String[] csvRow = row.createCSVLineFromRow().split(",");
                printer.printRecord((Object[]) csvRow);
            }

            printer.close(true);
            out.close();
        }

        return "Files Created";
    }

}
