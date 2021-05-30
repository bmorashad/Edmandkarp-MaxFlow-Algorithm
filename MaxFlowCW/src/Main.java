/* *****************************************************************************
 *  Name:    Mohomed Rashad
 *  UOW ID: 1930176   
 *  IIT ID: 2018470
 *
 *  Description: This program uses Edmand Karp algorithm to find max flow in
 *  a given network. A input file or a directory which contains input files
 *  should be used to pass the network flow.
 *
 *  Written:       27/03/2021
 *  Last updated:  29/03/2021
 *
 *  Build
 * 	Working Dir: ./
 *  % javac -sourcepath ./ -d out/ -classpath ./graph/networkflow/maxflow/*.java
 *  % javac -d out Main.java
 *
 *  Directory input
 *  % java -cp out/ Main -dir '../benchmarks/'
 *
 *  File input
 *  % java -cp out/ Main -file '../benchmarks/ladder_1.txt'
 *
 **************************************************************************** */

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import graph.networkflow.maxflow.EdmandKarp;
import graph.networkflow.maxflow.FlowGraph;

public class Main {
	static String BOLD = "\033[0;1m";
	static String NORMAL = "\033[0;0m";
	static String BLUE = "\u001b[34m";
	static String GREEN = "\u001b[32m";
	static String YELLOW = "\u001b[33m";
	static String RED = "\u001b[31m";
	static String MAGENTA = "\u001b[35m";
	static String CYAN = "\u001b[36m";
	static String WHITE = "\u001b[37m";

	public static void main(String[] args) {
		if (args.length > 1) {
			if (args[0].equals("-dir")) {
				getMaxFlowByInputs(args[1], "DIR");
			} else if (args[0].equals("-file")) {
				getMaxFlowByInputs(args[1], "FILE");
			} else {
				System.out.println("Invalid argument. Available args: [-dir, -file]");
			}
		} else {
			System.out.println("No inputs given");
		}
	}

	public static void getMaxFlowByInputs(String file, String inputType) {
		if (inputType.equals("DIR")) {
			File dir = new File(file);
			if (dir.isDirectory()) {
				File[] inputFiles = dir.listFiles();
				Arrays.sort(inputFiles);
				if (inputFiles != null) {
					printBenchmarkHeader();
					for (File input : inputFiles) {
						EdmandKarpBenchmark b = getMaxFlowWBenchmarkByFile(input.toString(), 3, false);
						printBenchmark(b);
					}
				} else {
					System.out.println("No input files in the given directory");
				}
			} else {
				System.out.println("No such directory exists!(" + dir.toPath().toString() + ")");
			}

		} else if (inputType.equals("FILE")) {
			if (new File(file).isFile()) {
				System.out.println("");
				EdmandKarpBenchmark b = getMaxFlowWBenchmarkByFile(file, 3, true);
				printBenchmarkHeader();
				printBenchmark(b);
				System.out.println("");
				// printMaxFlow(file);
			} else {
				System.out.println("No such a file: " + file);
			}
		} else {
			System.out.println("No such input type. Available input types: [DIR, FILE]");
		}
	}

	private static EdmandKarpBenchmark getMaxFlowWBenchmarkByFile(String inputFile, int iterations, boolean print) {
		double duration = 0;
		double startTime;
		double endTime;
		FlowGraph graph;
		long maxFlow;
		for (int i = 0; i < iterations - 1; i++) {
			graph = FlowGraph.parse(inputFile);
			startTime = System.nanoTime();
			maxFlow = new EdmandKarp(graph).getMaxFlow();
			endTime = System.nanoTime();
			duration += (endTime - startTime) / 1000000;
		}
		graph = FlowGraph.parse(inputFile);
		startTime = System.nanoTime();
		maxFlow = new EdmandKarp(graph).getMaxFlow(print);
		endTime = System.nanoTime();
		duration += (endTime - startTime) / 1000000;

		double avgDuration = Math.round((duration / 3.0) * 100.0) / 100.0;

		return new EdmandKarpBenchmark(inputFile, graph.size(), graph.edgeCount(), maxFlow, avgDuration);
	}

	// private static void printMaxFlow(String inputFile, boolean print) {
	// EdmandKarpBenchmark benchmark = getMaxFlowWBenchmark(inputFile, 3, print);
	// printBenchmark(new String[] { fileName, vertices, edges, maxFlow, avgDuration
	// });
	// System.out.println(BOLD + CYAN + fileName + ":\t" + BOLD + YELLOW + maxFlow +
	// NORMAL);
	// }

	private static void printBenchmark(EdmandKarpBenchmark benchmark) {
		int colWidth = 16;
		int fileColWidth = 16;
		String[] filePaths = benchmark.inputFile.split("/");
		String file = filePaths[filePaths.length - 1];
		String fileName = compactWord(file, 12);
		String vertices = benchmark.vertices + "";
		String edges = benchmark.edges + "";
		String maxFlow = benchmark.maxFlow + "";
		String duration = benchmark.avgDuration + "";

		System.out.printf("|%-16s|", "  " + fileName);
		System.out.printf("%16s|", vertices + "  ");
		System.out.printf("%16s|", edges + "  ");
		System.out.printf("%16s|", maxFlow + "  ");
		System.out.printf("%16s|", duration + "  ");
		System.out.print("\n");

		String colSep = stringInto("-", colWidth);
		String fileColSep = stringInto("-", fileColWidth);
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		sb.append(fileColSep + "+");
		for (int i = 1; i < 5; i++) {
			sb.append(colSep + "+");
		}
		System.out.println(sb.toString());

	}

	private static void printBenchmarkHeader() {
		String[] header = new String[] { "File name", "Vertices", "Edges", "Max Flow", "Duration(ms)" };
		int colWidth = 16;
		int fileColWidth = 16;
		String colSep = stringInto("-", colWidth);
		String fileColSep = stringInto("-", fileColWidth);
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		sb.append(fileColSep + "+");
		for (int i = 1; i < header.length; i++) {
			sb.append(colSep + "+");
		}
		String file = compactWord(header[0], 12);
		String vertices = centerWord(header[1], colWidth);
		String edges = centerWord(header[2], colWidth);
		String maxFlow = centerWord(header[3], colWidth);
		String duration = centerWord(header[4], colWidth);
		System.out.println(sb.toString());

		System.out.printf("|%-16s|", "  " + file);
		System.out.printf("%16s|", vertices);
		System.out.printf("%16s|", edges);
		System.out.printf("%16s|", maxFlow);
		System.out.printf("%16s|", duration);
		System.out.print("\n");

		System.out.println(sb.toString());

	}

	private static String centerWord(String word, int width) {
		if (word.length() > width) {
			word = compactWord(word, width);
		}
		String pad = " ";
		int widthDiff = width - word.length();
		int leftPadWidth = widthDiff / 2;
		int rightPadWidth = widthDiff / 2 + widthDiff % 2;

		String leftPad = stringInto(pad, leftPadWidth);
		String rightPad = stringInto(pad, rightPadWidth);
		word = leftPad + word + rightPad;
		return word;
	}

	private static String compactWord(String word, int maxLength) {
		if (word.length() > maxLength) {
			word = word.substring(0, maxLength - 3);
			word = word.trim();
			word = word + "...";
		}
		return word;
	}

	private static String stringInto(String str, int inTo) {
		String initialString = str;
		for (int i = 1; i < inTo; i++) {
			str += initialString;
		}
		return str;
	}

}
