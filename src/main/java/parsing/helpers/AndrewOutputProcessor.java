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
            for (String aString : splitString) {
                System.out.println(aString);
            }
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

        return new ArrayList<>(testMap.values());
    }

    private static void setAttributeToValue(String attributeAndValue, LocalChecksTest test) {
        String[] splitAttributeAndValue = attributeAndValue.split(":");
        String attribute = splitAttributeAndValue[0];
        String value = splitAttributeAndValue[1];
        if (attribute.equalsIgnoreCase(ATTEMPTS)) {
            System.out.println("This is the float value: " + Float.parseFloat(value));
            test.setAttempts(Float.parseFloat(value));
        }
        else if (attribute.equalsIgnoreCase(FINAL_STATUS)) {
            test.setStatus(value);
        }
    }

}
