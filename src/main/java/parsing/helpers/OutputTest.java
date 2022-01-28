package parsing.helpers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class OutputTest {

    public static void main(String[] args) {

        String test = "A1ConfigurationProvided-(0.0/10.0)";

        String[] testAndScore = test.split("-");
        String[] testScores = testAndScore[1].replaceAll("\\(", "")
                .replaceAll("\\)", "").split("/");
    }
}
