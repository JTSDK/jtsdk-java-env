/* 
 * Class: JTEnvJV
 * 
 * Desctiption: Prints Java Related Environment Variables
 *
 * Usage: javac JTEnvJV.java
 *        java JTEnvJV
 * 
 * Date: 7/22/2018
 * 
 * Author: Greg Beam, KI7MT <ki7mt@yahoo.com>
 * 
 * Version: 3.0.1
 * 
 * Licesne: GPL-3
 * 
 * Dependencies: Apache Commons CLI
 * 
 * 
*/
package com.jtsdk.jtenv.cli;

// Java Common Imports
import java.util.ArrayList;

// Apache Commons CLI Imports
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class JTEnvJV {

	private final static String APP_NAME = "JTEnvJV";
	private final static String APP_VERSION = "3.0.1";

	// Main Method
	public static void main(String[] args) {

		// Java List
		ArrayList<String> javaList = new ArrayList<String>();
		javaList.add("JTSDK_HOME");
		javaList.add("JAVA_TOOL_OPTIONS");
		javaList.add("JAVA_HOME");
		javaList.add("MAVEN_HOME");
		javaList.add("M2_HOME");
		javaList.add("GRADLE_HOME");

		// System List
		ArrayList<String> systemList = new ArrayList<String>();
		systemList.add("COMPUTERNAME");
		systemList.add("PROCESSOR_ARCHITECTURE");
		systemList.add("COMSPEC");
		systemList.add("PROCESSOR_IDENTIFIER");

		// User List
		ArrayList<String> userList = new ArrayList<String>();
		userList.add("USERNAME");
		userList.add("USERPROFILE");
		userList.add("LOCALAPPDATA");
		userList.add("TEMP");

		// User List
		ArrayList<String> jtsdkList = new ArrayList<String>();
		jtsdkList.add("JTSDK_HOME");
		jtsdkList.add("JTSDK_APPS");
		jtsdkList.add("JTSDK_CONFIG");
		jtsdkList.add("JTSDK_DATA");

		// All Lists Combined
		ArrayList<String> all = new ArrayList<String>();
		all.addAll(userList);
		all.addAll(jtsdkList);
		all.addAll(javaList);
		all.addAll(systemList);

		// create the command line parser
		CommandLineParser parser = new DefaultParser();

		// create the Options for parser
		Options options = new Options();

		// Add the options
		options.addOption("h", "help", false, "display help message");
		options.addOption("a", "all", false, "display all environment secionts");
		options.addOption("j", "java", false, "display Java environment variables");
		options.addOption("s", "system", false, "display System envrionment variables");
		options.addOption("u", "user", false, "display User envrionment variables");
		options.addOption("z", "jtsdk", false, "display JTSDK environment variables");
		options.addOption("v", "version", false, "display version information");

		// Start Parsing Arguments
		try {

			// Selection List
			ArrayList<String> selection = new ArrayList<String>();
			
			// always clean the screen first
			clearScreen();

			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			// help options
			if (line.hasOption("help")) {
				mainHeader("Help Screen");
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp(APP_NAME, options);
				System.exit(0);
			}

			// all lists
			if (line.hasOption("all")) {
				mainHeader("All Environment Variables");
				sectionHeader("All Variables");
				selection.addAll(all);
				LoopArrayList(selection);
				System.exit(0);
			}

			// java List
			if (line.hasOption("java")) {
				mainHeader("Java Environment Variables");
				sectionHeader("Java");
				LoopArrayList(javaList);
				System.exit(0);
			}

			// user list
			if (line.hasOption("user")) {
				mainHeader("User Environment variables");
				sectionHeader("User");
				LoopArrayList(userList);
				System.exit(0);
			}

			// system list
			if (line.hasOption("system")) {
				mainHeader("System Environment variables");
				sectionHeader("System");
				LoopArrayList(systemList);
				System.exit(0);
			}
			
			// jtsdk list
			if (line.hasOption("jtsdk")) {
				mainHeader("JTSDK Environment variables");
				sectionHeader("System");
				LoopArrayList(jtsdkList);
				System.exit(0);
			}

			// display version
			if (line.hasOption("version")) {
				mainHeader("Version Informaiton");
				System.exit(0);
			}
			
			// Null or only one value
			if (line.getArgs().length == 0 || line.getArgs().length == 1) {
				mainHeader("Invalid Option");
				System.err.println("Use " + APP_NAME + " -h for help\n");
				System.exit(1);
			}
			
		} catch (ParseException exp) {
			mainHeader(exp.getMessage());
			System.err.println("Parsing failed. Reason: " + exp.getMessage());
			System.exit(1);
		}

	} /* End - Main Method */

	// -------------------------------------------------------------------------
	// MAIN CLASS METHODS
	// --------------------------------------------------------------------------

	// clear console command based os.name
	private final static void clearScreen() {
		final String os = System.getProperty("os.name");

		try {
			if (os.contains("Windows")) {

				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

			} else {

				Runtime.getRuntime().exec("clear");

			}
		} catch (final Exception e) {
			System.err.println(e);
		}

	} // End - clearConsole()

	// Print main header header
	private static void mainHeader(String message) {

		System.out.println("\nName        : " + APP_NAME);
		System.out.println("Version     : " + APP_VERSION);
		System.out.println("Description : " + message + "\n");

	} // End - mainHeader()

	// Print main header header
	private static void sectionHeader(String section) {

		System.out.printf("%-23s %-30s%n", "Variable", "Path");
		System.out.println("------------------------------------------------------");

	} // End - sectionHeader(String section)

	// loop through the array list
	private static void LoopArrayList(ArrayList<String> array) {

		// print list item(s) and their associated value
		for (String item : array) {
			System.out.printf("%-23s %-30s%n", item, System.getenv(item));
		}
	} // End - LoopArrayList(ArrayList<String> array)

} // End - JTEnvJV