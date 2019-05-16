package be.kapture.hl7sender.configuration;

public class ConfigurationBuilderImpl implements ConfigurationBuilder {
	Configuration config = new Configuration();

	@Override
	public Configuration getConfiguration() {
		return config;
	}

	@Override
	public ConfigurationBuilder setVersion(String version) {
		config.setVersion(version);

		return this;
	}

	@Override
	public ConfigurationBuilder setDefaultFileName(String defaultFileName) {
		config.setDefaultFileName(defaultFileName);

		return this;
	}

	@Override
	public ConfigurationBuilder setExportLocation(String exportLocation) {
		config.setExportLocation(exportLocation);

		return this;
	}

	@Override
	public ConfigurationBuilder setFileExtension(String fileExtension) {
		config.setFileExtension(fileExtension);

		return this;
	}
}
