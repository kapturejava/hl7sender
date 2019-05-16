package be.kapture.hl7sender.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import static java.util.Collections.emptyMap;

public class Configuration {
	private static String DEFAULT_FILE_NAME = "outgoing_message.hl7";
	private static Configuration configuration;

	private String version;
	private String exportLocation;
	private String defaultFileName;
	private String fileExtension;

	protected Configuration() {}

	public static Configuration getDefaultConfiguration() {
		if (configuration == null) {
			Configuration newConfig = new Configuration();
			String filePath = newConfig.getClass().getResource("/config/config.yml").getFile();

			configuration = getNewConfiguration(new File(filePath));
		}

		return configuration;
	}

	public static Configuration getNewConfiguration(File file) {
		Map<String, String> settings = parseYamlFile(file);

		try {
			configuration = new ConfigurationBuilderImpl().setVersion(settings.get("version"))
			                                                     .setExportLocation(settings.get("exportLocation"))
			                                                     .setDefaultFileName(settings.get("defaultFileName"))
			                                                     .setFileExtension(settings.get("fileExtension"))
			                                                     .getConfiguration();

			return configuration;
		} catch (NullPointerException e) {
			System.out.println(e.getStackTrace());

			return new Configuration();
		}
	}

	void setVersion(String version) {
		this.version = version;
	}

	void setExportLocation(String exportLocation) {
		this.exportLocation = exportLocation;
	}

	void setDefaultFileName(String defaultFileName) {
		this.defaultFileName = defaultFileName;
	}

	void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getVersion() {
		return version;
	}

	public String getExportLocation() {
		return exportLocation;
	}

	public String getDefaultFileName() {
		return defaultFileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	private static Map<String, String> parseYamlFile(File file) {
		Yaml yaml = new Yaml();

		try {
			InputStream input = new FileInputStream(file);

			return (Map<String, String>) yaml.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return emptyMap();
	}
}
