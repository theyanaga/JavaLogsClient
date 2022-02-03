package parsing.helpers;

import net.bytebuddy.description.method.ParameterList;
import parsing.entities.LocalChecksTest;
import parsing.entities.RowFromServer;

import java.util.*;
import java.util.stream.Collectors;

public class AndrewOutputProcessor {

    private static final String ATTEMPTS = "attempts";
    private static final String FINAL_STATUS = "finished as";

    public static List<LocalChecksTest> processInput(List<String> strings) {

        Map<String, LocalChecksTest> testMap = new HashMap<>();

        for (String string : strings) {
            String[] splitString = string.split(" ",2);
            String testName = splitString[0];
            String attributeAndValue = splitString[1];
            if (testMap.containsKey(testName)) {
                setAttributeToValue(attributeAndValue, testMap.get(testName));
            }
            else {
                LocalChecksTest aTest = LocalChecksTest.ofName(testName);
                testMap.put(testName, aTest);
                setAttributeToValue(attributeAndValue, aTest);
            }
        }

        // Sort the values before you return them to make sure that all of the values come in the same order.
        return testMap.values().stream()
                .sorted(Comparator.comparing(LocalChecksTest::getName)).collect(Collectors.toList());
    }

    private static void setAttributeToValue(String attributeAndValue, LocalChecksTest test) {
        String[] splitAttributeAndValue = attributeAndValue.split(":");
        String attribute = splitAttributeAndValue[0];
        String value = splitAttributeAndValue[1];
        if (attribute.equalsIgnoreCase(ATTEMPTS)) {
            test.setAttempts(Float.parseFloat(value));
        }
        else if (attribute.equalsIgnoreCase(FINAL_STATUS)) {
            test.setStatus(value);
        }
    }

}
