package parsing.helpers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class OutputTest {

    public static void main(String[] args) {

        String test = "A1ConfigurationProvided";

        String input = "A1ConfigurationProvided attempts:4.0";

        System.out.println(input.contains(test));
    }
}
