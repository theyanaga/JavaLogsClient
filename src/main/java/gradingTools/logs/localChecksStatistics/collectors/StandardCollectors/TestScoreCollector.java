package gradingTools.logs.localChecksStatistics.collectors.StandardCollectors;

import gradingTools.logs.localChecksStatistics.collectors.AbstractCollector;

import java.util.Arrays;

public class TestScoreCollector extends AbstractCollector  {

    private static float accumulatedScore;
    private static float totalPoints;

    @Override
    protected String getHeaderPhrase() {
        return " score";
    }

    public TestScoreCollector() {
        this.reqPass = 1;
    }
    @Override
    public void logData(String[] data) throws IllegalArgumentException {
        String testScoresString = data[TEST_SCORES_INDEX];
        String[] individualTestScores = testScoresString.split(" ");
        for (String test : individualTestScores) {
            test = test.trim();
            if (!test.equalsIgnoreCase("\"")) {
                String[] testAndScore = test.split("-");
                String[] testScores = testAndScore[1].replaceAll("\\(", "")
                        .replaceAll("\\)","").split("/");
                float testPoints = Float.parseFloat(testScores[0]);
                float testTotalPoints = Float.parseFloat(testScores[1]);
                String resultString = testPoints + "/" + testTotalPoints;
                if (findIndex(testAndScore[0]) >= 0) {
                    results[findIndex(testAndScore[0])] = resultString;
                }
            }
        }
    }

    private int findIndex(String testName) {
        for (int i =0; i < testNames.length; i++) {
            if (testNames[i].equalsIgnoreCase(testName)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean requiresTestNames() {
        return true;
    }
}
