package com.github.frettarenan.mavendeployfiles.strategies;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.frettarenan.mavendeployfiles.configs.CommandLineArgumentsConfigs;

public class CmdCommandsGeneratorStrategy implements ICommandsGeneratorStrategy {
	
	private final String resultFileName = ".\\maven-deploy-libs.cmd";

	private CommandLineArgumentsConfigs cla;

	private List<File> pomFiles;

	private List<String> commands = new ArrayList<String>();

	public CmdCommandsGeneratorStrategy(CommandLineArgumentsConfigs cla, List<File> pomFiles) {
		this.cla = cla;
		this.pomFiles = pomFiles;
	}

	@Override
	public void fileStart() {
		commands.add("@echo off" + System.lineSeparator());
	}

	@Override
	public void fileEnd() {
		commands.add("pause");
	}

	@Override
	public void addCdCommand(String dirPath) {
		commands.add("cd " + dirPath);
	}

	@Override
	public void addMvnCommand(String mvnCommand) {
		commands.add("call mvn " + mvnCommand + System.lineSeparator());
	}

	@Override
	public CommandLineArgumentsConfigs getCommandLineArgumentsConfigs() {
		return cla;
	}

	@Override
	public List<File> getPomFiles() {
		return pomFiles;
	}

	@Override
	public List<String> getCommands() {
		return commands;
	}

	@Override
	public String getResultFileName() {
		return resultFileName;
	}

}
