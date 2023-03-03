package com.github.frettarenan.mavendeployfiles.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.github.frettarenan.mavendeployfiles.configs.CommandLineArgumentsConfigs;

public class MavenUtil {

	public static List<File> findPomFiles(CommandLineArgumentsConfigs cla) {
		File dir = new File(cla.getRepositoryPath());
		File[] files = dir.listFiles();
		List<File> pomFiles = new ArrayList<File>();
		organizeFiles(files, pomFiles);
		return pomFiles;
	}

	private static void organizeFiles(File[] files, List<File> pomFiles) {
		for (File file : files) {
			if (file.isDirectory())
				organizeFiles(file.listFiles(), pomFiles);
			else
				addPomFileToList(file, pomFiles);
		}
	}

	private static void addPomFileToList(File file, List<File> pomFiles) {
		String fileName = file.getName();
		int dotIndex = fileName.lastIndexOf('.');
		String fileExtension = fileName.substring(dotIndex + 1);

		if (!fileExtension.equalsIgnoreCase("pom")) {
			return;
		}

		pomFiles.add(file);
	}

	public static PomPojo readPomFile(File pomFile) {
		try {
			String content = fixInvalidCharacters(pomFile);
			InputSource inputSource = new InputSource(new StringReader(content));

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = dBuilder.parse(inputSource);
			doc.getDocumentElement().normalize();

			Element projectElement = doc.getDocumentElement();

			String pomGroupId = projectElement.getElementsByTagName("groupId").item(0).getTextContent();
			String pomArtifactId = projectElement.getElementsByTagName("artifactId").item(0).getTextContent();
			String pomVersion = projectElement.getElementsByTagName("version").item(0).getTextContent();
			String pomPackaging = "jar";

			try {
				pomPackaging = doc.getElementsByTagName("packaging").item(0).getTextContent();
			} catch (Exception e) {
			}

			return new PomPojo(pomGroupId, pomArtifactId, pomVersion, pomPackaging);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String fixInvalidCharacters(File pomFile) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(pomFile.getAbsolutePath())), StandardCharsets.UTF_8);
		content = StringEscapeUtils.unescapeHtml4(content);
		return content;
	}

	public static class PomPojo {
		private String pomGroupId;
		private String pomArtifactId;
		private String pomVersion;
		private String pomPackaging;

		public PomPojo(String pomGroupId, String pomArtifactId, String pomVersion, String pomPackaging) {
			this.pomGroupId = pomGroupId;
			this.pomArtifactId = pomArtifactId;
			this.pomVersion = pomVersion;
			this.pomPackaging = pomPackaging;
		}

		public String getPomGroupId() {
			return pomGroupId;
		}

		public String getPomArtifactId() {
			return pomArtifactId;
		}

		public String getPomVersion() {
			return pomVersion;
		}

		public String getPomPackaging() {
			return pomPackaging;
		}

	}

}
