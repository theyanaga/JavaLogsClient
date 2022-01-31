package api.calls.helpers;

import api.calls.entities.ServerInputWrapper;
import api.calls.entities.ServerOutputWrapper;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import javax.inject.Inject;
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
        public int run(String[] args) throws Exception {

            for(int i = 0; i < 100; i++) {
                System.out.println(i);
            }

            Quarkus.waitForExit();
            return 0;
        }

    }
}
