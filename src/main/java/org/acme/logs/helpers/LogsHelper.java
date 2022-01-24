package org.acme.logs.helpers;

import org.acme.logs.entities.Log;
import org.acme.logs.entities.ServerOutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogsHelper {


    public static String deleteUselesQuotationMark(String eclipseLogs) {
        return eclipseLogs.replaceFirst("startTimestamp=\"0","startTimestamp=\"0\"");
    }

    public static String formatMachineIdToUserId(String machineId) {
        machineId = machineId.replaceAll(" ", "");
        machineId = machineId.replaceAll(",", "");
        machineId = machineId.replaceAll("\\]", "");
        machineId = machineId.replaceAll("-","A");
        return machineId.replaceAll("\\[", "");
    }

    public static void createFile(ServerOutput serverOutput, String path) throws IOException {
        File directory = new File(path + LogsHelper.formatMachineIdToUserId(serverOutput.getMachineId()));

        int fileNumber;

        if (!directory.exists()) {
            directory.mkdir();
            fileNumber = 0;
        }
        else {
            if (directory.listFiles() == null) {
                fileNumber = 0;
            }
            else {
                fileNumber = directory.listFiles().length;
            }
        }

        FileOutputStream stream = new FileOutputStream(path +
                LogsHelper.formatMachineIdToUserId(serverOutput.getMachineId())
                + "/" + LogsHelper.formatMachineIdToUserId(serverOutput.getMachineId())
                + "." + fileNumber + LogsResource.FILE_TYPE);

        String log = serverOutput.getLog().getJson();

        String logWithRemovedQuotation = LogsHelper.deleteUselesQuotationMark(log);

        stream.write(logWithRemovedQuotation.getBytes());

        stream.close();
    }

}
