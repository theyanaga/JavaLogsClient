package gradingTools.logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import gradingTools.logs.localChecksStatistics.collectors.Collector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.*;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.CollectorManager;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.LocalLogDataAnalyzer;
import static gradingTools.logs.localChecksStatistics.compiledLogGenerator.LocalLogDataAnalyzer.ALL_ASSIGNMENTS;
import static gradingTools.logs.localChecksStatistics.compiledLogGenerator.LocalLogDataAnalyzer.runEvaluationFromDatabase;

public class LocalChecksLogData {
	private static CollectorManager collectors=null;
	private static boolean printOutput=true;
	private static final CollectorManager attemptsCollector, workTimeCollector;
	private static final long defaultBreakTime = 5000;
	private static final File defaultDirectory = new File("./");

	static {
		attemptsCollector = new CollectorManager(new AttemptsCollectorV2());
		workTimeCollector = new CollectorManager(new WorkTimeCollector(defaultBreakTime));
	}

	//General Setters
	public static void setPrintOutput(boolean b) {
		printOutput=b;
	}

	public static void setCollectors(Collector [] collectorsArray) {
		collectors=new CollectorManager(collectorsArray);
	}


	public static List<String> getData() {
		return LocalLogDataAnalyzer.getData(defaultDirectory,ALL_ASSIGNMENTS,collectors,printOutput,null);
	}

	public static List<String> getData(Collector [] collectorsArray) {
		return LocalLogDataAnalyzer.getData(defaultDirectory, ALL_ASSIGNMENTS,new CollectorManager(collectorsArray),printOutput,null);
	}

	public static List<String> getData(String assignmentNumber){
		return LocalLogDataAnalyzer.getData(defaultDirectory,assignmentNumber,collectors,printOutput,null);
	}

	public static List<String> getData(File eclipseDirectory) {
		return LocalLogDataAnalyzer.getData(eclipseDirectory,ALL_ASSIGNMENTS,collectors,printOutput,null);
	}

	public static List<String> getData(File eclipseDirectory, Collector [] collectorsArray) {
		return LocalLogDataAnalyzer.getData(eclipseDirectory,ALL_ASSIGNMENTS, new CollectorManager(collectorsArray),printOutput,null);
	}

	public static List<String> getData(File eclipseDirectory, String assignmentNumber){
		return LocalLogDataAnalyzer.getData(eclipseDirectory,assignmentNumber,collectors,printOutput,null);
	}

	public static List<String> getData(String assignmentNumber, Collector [] collectorsArray) {
		return LocalLogDataAnalyzer.getData(defaultDirectory, assignmentNumber,new CollectorManager(collectorsArray),printOutput,null);
	}

	public static List<String> getData(File eclipseDirectory, String assignmentNumber, Collector [] collectorsArray) {
		return LocalLogDataAnalyzer.getData(eclipseDirectory,assignmentNumber,new CollectorManager(collectorsArray),printOutput,null);
	}

	public static List<String> getAttemptsData(){
		return LocalLogDataAnalyzer.getData(defaultDirectory,ALL_ASSIGNMENTS, attemptsCollector,printOutput,null);
	}

	public static List<String> getAttemptsData(File eclipseDirectory){
		return LocalLogDataAnalyzer.getData(eclipseDirectory,ALL_ASSIGNMENTS, attemptsCollector,printOutput,null);
	}

	public static List<String> getAttemptsData(String assignmentNumber){
		return LocalLogDataAnalyzer.getData(defaultDirectory,assignmentNumber,attemptsCollector,printOutput,null);
	}

	public static List<String> getAttemptsData(File eclipseDirectory, String assignmentNumber){
		return LocalLogDataAnalyzer.getData(eclipseDirectory,assignmentNumber,attemptsCollector,printOutput,null);
	}

	public static List<String> getWorkTimeData(){
		return LocalLogDataAnalyzer.getData(defaultDirectory,ALL_ASSIGNMENTS,workTimeCollector,printOutput,null);
	}

	public static List<String> getWorkTimeData(File eclipseDirectory){
		return LocalLogDataAnalyzer.getData(eclipseDirectory,ALL_ASSIGNMENTS,workTimeCollector,printOutput,null);
	}

	public static List<String> getWorkTimeData(String assignmentNumber){
		return LocalLogDataAnalyzer.getData(defaultDirectory,assignmentNumber,workTimeCollector,printOutput,null);
	}

	public static List<String> getWorkTimeData(File eclipseDirectory, String assignmentNumber){
		return LocalLogDataAnalyzer.getData(eclipseDirectory,assignmentNumber,workTimeCollector,printOutput,null);
	}

	public static List<String> getData(File eclipseDirectory, String assignmentNumber, Collector [] collectorsArray, boolean printOutput, String ... desiredTests) {
		return LocalLogDataAnalyzer.getData(eclipseDirectory,assignmentNumber,new CollectorManager(collectorsArray),printOutput,desiredTests);
	}

	public static void writeDataToFile(List<String> data, File output) throws IOException {
		FileWriter writeTo = new FileWriter(output);
		for(String dataPoint:data)
			writeTo.write(dataPoint);
		writeTo.close();
	}


	public static void main(String[] args) throws IOException {
	}


}
