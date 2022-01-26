package org.acme.logs.parsing.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignmentTestManager {

    // Contains the name of all tests in the assignment
    private List<TestSession> allTestSessions = new ArrayList<>();

    private Map<Integer, TestSession> mapFromIdToSession = new HashMap<>();

    private AssignmentTestManager(String aAssignmentName) {
        this.assignmentName = aAssignmentName;
    }

    public static AssignmentTestManager ofName(String name) {
        return new AssignmentTestManager(name);
    }

    private String assignmentName;

    public String getAssignmentName() {
        return assignmentName;
    }

    public List<TestSession> getAllTestSessions() {
        return allTestSessions;
    }

    public void addNewTestName(String testName, int id) {
        for (TestSession session : allTestSessions) {
            if (session.getSessionId() == id) {
                if (!session.getAllTestNames().contains(testName)){
                    session.getAllTestNames().add(testName);
                }
            }
        }
    }

    public void addNewTestRun(TestRun testRun) {
        if (mapFromIdToSession.containsKey(testRun.getSessionId())) {
            TestSession session = mapFromIdToSession.get(testRun.getSessionId());
            session.addTestRun(testRun);
            session.setTimesRan(testRun.getRunId());
        }
        else {
            TestSession aNewSession = TestSession.ofId(testRun.getSessionId());
            aNewSession.addTestRun(testRun);
            aNewSession.setTimesRan(testRun.getRunId());
            mapFromIdToSession.put(testRun.getSessionId(), aNewSession);
            this.allTestSessions.add(aNewSession);
        }
    }

}
