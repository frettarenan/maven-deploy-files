package com.github.frettarenan.mavendeployfiles.configs;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CommandLineArgumentsConfigs {

	private static final String ARG_REPOSITORY_PATH = "repositoryPath";

	private static final String ARG_MVN_DEPLOY_REPOSITORY_ID_PATH = "mvnDeplyRepositoryId";
	private static final String ARG_MVN_DEPLOY_REPOSITORY_URL_PATH = "mvnDeplyRepositoryUrl";

	private String repositoryPath;

	private String mvnDeplyRepositoryId;
	private String mvnDeplyRepositoryUrl;

	public CommandLineArgumentsConfigs(String[] args) {
		try {
			Options options = new Options();
			options.addOption(Option.builder(ARG_REPOSITORY_PATH).required(true).hasArg(true).build());

			options.addOption(Option.builder(ARG_MVN_DEPLOY_REPOSITORY_ID_PATH).required(true).hasArg(true).build());
			options.addOption(Option.builder(ARG_MVN_DEPLOY_REPOSITORY_URL_PATH).required(true).hasArg(true).build());

			CommandLine commandLine = new DefaultParser().parse(options, args);
			this.repositoryPath = commandLine.getOptionValue(ARG_REPOSITORY_PATH);

			this.mvnDeplyRepositoryId = commandLine.getOptionValue(ARG_MVN_DEPLOY_REPOSITORY_ID_PATH);
			this.mvnDeplyRepositoryUrl = commandLine.getOptionValue(ARG_MVN_DEPLOY_REPOSITORY_URL_PATH);

		} catch (Throwable e) {
			System.out.println("Application parameterization error: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public String getRepositoryPath() {
		return repositoryPath;
	}

	public String getMvnDeplyRepositoryId() {
		return mvnDeplyRepositoryId;
	}

	public String getMvnDeplyRepositoryUrl() {
		return mvnDeplyRepositoryUrl;
	}

}
