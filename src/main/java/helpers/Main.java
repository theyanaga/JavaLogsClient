package helpers;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import gradingTools.gradingTools.entities.ServerInputWrapper;
import gradingTools.entities.ServerOutput;
import gradingTools.entities.ServerOutputWrapper;
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

            for (int i = 0; i < 30; i++) {
                ServerInputWrapper serverInputWrapper = ServerInputWrapper.createServerRequest(SKIP + (2 * i));

                ServerOutputWrapper serverOutputWrapper = logsService.getLogs(serverInputWrapper);

                LOG.debug("Server Output: " + serverOutputWrapper);

                for (ServerOutput output : serverOutputWrapper.getLogs()) {
                    try {
                        LOG.debug("Server Log: " + output);
                        LogsHelper.createFile(output, MY_PATH);
                    }
                    catch (Exception e) {
                        System.out.println("Null Pointer b/c Logs");
                        e.printStackTrace();
                    }
                }
            }

            Quarkus.waitForExit();
            return 0;
        }

    }
}
