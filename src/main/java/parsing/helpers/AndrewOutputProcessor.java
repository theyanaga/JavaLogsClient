package parsing.helpers;

import parsing.relations.Assignment;
import parsing.entities.LocalTest;

import java.util.*;
import java.util.stream.Collectors;

public class AndrewOutputProcessor {

    private static final String ATTEMPTS = "attempts";
    private static final String FINAL_STATUS = "finished as";

    public static List<LocalTest> processInput(List<String> strings, Assignment assignment) {

        Map<String, LocalTest> testMap = new HashMap<>();

        for (String string : strings) {
            String[] splitString = string.split(" ",2);
            String testName = splitString[0];
            String attributeAndValue = splitString[1];
            if (testMap.containsKey(testName)) {
                setAttributeToValue(attributeAndValue, testMap.get(testName));
            }
            else {
                LocalTest aTest = LocalTest.ofName(testName);
                aTest.setAssignmentId(assignment.id);
                testMap.put(testName, aTest);
                setAttributeToValue(attributeAndValue, aTest);
            }
        }

        // Sort the values before you return them to make sure that all of the values come in the same order.
        return testMap.values().stream()
                .sorted(Comparator.comparing(LocalTest::getName)).collect(Collectors.toList());
    }

    private static void setAttributeToValue(String attributeAndValue, LocalTest test) {
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
