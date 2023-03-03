package com.github.frettarenan.mavendeployfiles;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.github.frettarenan.mavendeployfiles.configs.CommandLineArgumentsConfigs;
import com.github.frettarenan.mavendeployfiles.generators.CommandsGenerator;

public class MainApp {

	private static final String[] DEBUG_ARGS = { "-repositoryPath", "C:\\repository-test", "-mvnDeplyRepositoryId",
			"test", "-mvnDeplyRepositoryUrl", "http://localhost:8080/test", };

	private static boolean isDebugMode;

	private static CommandLineArgumentsConfigs cla;

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		initCommandLineArgumentsConfigs(args);
		execute();
	}

	private static void initCommandLineArgumentsConfigs(String[] args) {
		isDebugMode = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
		cla = new CommandLineArgumentsConfigs(isDebugMode ? DEBUG_ARGS : args);
	}

	private static void execute() {
		CommandsGenerator.generator(cla);
	}

}
