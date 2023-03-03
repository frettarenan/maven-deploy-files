package com.github.frettarenan.mavendeployfiles.strategies;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.github.frettarenan.mavendeployfiles.configs.CommandLineArgumentsConfigs;
import com.github.frettarenan.mavendeployfiles.utils.MavenUtil;
import com.github.frettarenan.mavendeployfiles.utils.MavenUtil.PomPojo;

public interface ICommandsGeneratorStrategy {

	CommandLineArgumentsConfigs getCommandLineArgumentsConfigs();

	List<File> getPomFiles();

	List<String> getCommands();

	default void createFile() {
		List<String> commands = getCommands();
		File file = new File(getResultFileName());
		try {
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			for (String command : commands)
				writer.println(command);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	void fileStart();

	default void fileBody() {

		CommandLineArgumentsConfigs cla = getCommandLineArgumentsConfigs();
		List<File> pomFiles = getPomFiles();
		for (File pomFile : pomFiles) {
			PomPojo pomPojo = MavenUtil.readPomFile(pomFile);
			String pomFileName = pomFile.getName();

			String dirPath = pomFile.getParentFile().getAbsolutePath();
			this.addCdCommand(dirPath);

			String mvnDeployCommand = "deploy:deploy-file";
			mvnDeployCommand += " -DgeneratePom=" + "false";
			mvnDeployCommand += " -DgroupId=" + pomPojo.getPomGroupId();
			mvnDeployCommand += " -DartifactId=" + pomPojo.getPomArtifactId();
			mvnDeployCommand += " -Dversion=" + pomPojo.getPomVersion();
			mvnDeployCommand += " -Dpackaging=" + pomPojo.getPomPackaging();

			if (pomPojo.getPomPackaging().equalsIgnoreCase("pom")) {
				mvnDeployCommand += " -Dfile=" + pomFileName;
			} else {
				String libFileName = pomFileName.replace(".pom", "." + pomPojo.getPomPackaging());
				mvnDeployCommand += " -Dfile=" + libFileName;
				mvnDeployCommand += " -DpomFile=" + pomFileName;
			}

			mvnDeployCommand += " -DrepositoryId=" + cla.getMvnDeplyRepositoryId();
			mvnDeployCommand += " -Durl=" + cla.getMvnDeplyRepositoryUrl();

			this.addMvnCommand(mvnDeployCommand);
		}
	}

	void fileEnd();

	void addCdCommand(String dirPath);

	void addMvnCommand(String mvnCommand);
	
	String getResultFileName();

}
