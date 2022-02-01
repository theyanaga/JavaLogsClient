package api.calls.helpers;

import api.calls.entities.ServerInputWrapper;
import api.calls.entities.ServerOutputWrapper;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import parsing.entities.*;

import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.io.IOException;

@QuarkusMain
public class Main {

    // Pick a single log and extract things from it.

    public static void main(String[] args) throws IOException {
        Quarkus.run(MyApp.class,args);
    }

    public static class MyApp implements QuarkusApplication {

        public static final String MY_PATH = "/Users/felipeyanaga/UNC/Research/LocalCheckLogs/";

        public static final int SKIP = 250;

        @Inject
        @RestClient
        LogsService logsService;

        private static final Logger LOG = Logger.getLogger(Main.class);

        @Override
        @Transactional(Transactional.TxType.REQUIRED)
        public int run(String[] args) throws Exception {

            long skip = RowFromServer.count();

            for(int i = 0; i < 100; i++) {
                ServerOutputWrapper wrapper = logsService.getLogs(ServerInputWrapper.createServerRequest(
                        (int) (skip + i)
                ));

                String[] csvLine = wrapper.getLogs().get(0).getLog().getJson().split(",");

                Users users = Users.of(wrapper.getLogs().get(0).getMachineId());

                Course course = Course.of(Course.COMP_524, Season.FALL, 2021);

                Assignment assignment = Assignment.of(2, course);

                RowFromServer serverRow = RowFromServer.of(users, assignment, csvLine);
                serverRow.persistAndFlush();
            }

            Quarkus.waitForExit();
            return 0;
        }

    }
}
