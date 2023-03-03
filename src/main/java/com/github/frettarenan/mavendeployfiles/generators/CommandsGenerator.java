package com.github.frettarenan.mavendeployfiles.generators;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.frettarenan.mavendeployfiles.configs.CommandLineArgumentsConfigs;
import com.github.frettarenan.mavendeployfiles.strategies.CmdCommandsGeneratorStrategy;
import com.github.frettarenan.mavendeployfiles.strategies.ICommandsGeneratorStrategy;
import com.github.frettarenan.mavendeployfiles.strategies.ShCommandsGeneratorStrategy;
import com.github.frettarenan.mavendeployfiles.utils.MavenUtil;

public class CommandsGenerator {

	public static void generator(CommandLineArgumentsConfigs cla) {
		ArrayList<ICommandsGeneratorStrategy> commandsGeneratorStrategies = new ArrayList<ICommandsGeneratorStrategy>();
		addFileGeneratorStrategies(cla, commandsGeneratorStrategies);

		commandsGeneratorStrategies.parallelStream().forEach(strategy -> {
			strategy.fileStart();
			strategy.fileBody();
			strategy.fileEnd();
			strategy.createFile();
		});
	}

	private static void addFileGeneratorStrategies(CommandLineArgumentsConfigs cla, ArrayList<ICommandsGeneratorStrategy> commandsGeneratorStrategies) {
		List<File> pomFiles = MavenUtil.findPomFiles(cla);
		commandsGeneratorStrategies.add(new CmdCommandsGeneratorStrategy(cla, pomFiles));
		commandsGeneratorStrategies.add(new ShCommandsGeneratorStrategy(cla, pomFiles));
	}

}
