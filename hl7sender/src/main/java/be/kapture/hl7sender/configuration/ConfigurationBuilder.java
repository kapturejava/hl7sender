package be.kapture.hl7sender.configuration;

public interface ConfigurationBuilder {
	Configuration getConfiguration();

	ConfigurationBuilder setVersion(String version);

	ConfigurationBuilder setDefaultFileName(String defaultFileName);

	ConfigurationBuilder setExportLocation(String exportLocation);

	ConfigurationBuilder setFileExtension(String fileExtension);
}
