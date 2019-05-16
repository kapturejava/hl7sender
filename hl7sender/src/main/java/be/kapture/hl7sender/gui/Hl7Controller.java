package be.kapture.hl7sender.gui;

import be.kapture.hl7sender.configuration.Configuration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.springframework.stereotype.Controller;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static be.kapture.hl7sender.configuration.Configuration.getDefaultConfiguration;
import static be.kapture.hl7sender.configuration.Configuration.getNewConfiguration;
import static be.kapture.hl7sender.gui.Wordings.*;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;


@Controller
public class Hl7Controller {
	private static DirectoryChooser directoryChooser = new DirectoryChooser();
	private static FileChooser fileChooser = new FileChooser();
	private static Configuration config = getDefaultConfiguration();

	@FXML
	private TextArea hl7MessageField;

	@FXML
	private TextField importConfigurationField;

	@FXML
	private TextField exportLocationField;

	@FXML
	private Label successOrFailureMessage;

	@FXML
	protected void initialize() {
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("YAML files (*.yml)", "*.yml");
		fileChooser.getExtensionFilters().add(filter);

		if (!config.getExportLocation().isEmpty()) {
			exportLocationField.setText(config.getExportLocation());
		}
	}

	@FXML
	void setConfigurationLocation(ActionEvent event) {
		Window window = getWindow(event);
		File file = fileChooser.showOpenDialog(window);

		if (file != null) {
			importConfigurationField.setText(file.getAbsolutePath());
		}
	}

	@FXML
	void updateConfiguration(ActionEvent event) {
		String configField = importConfigurationField.getText();

		if (isValidFile(configField)) {
			config = getNewConfiguration(new File(configField));

			if (!config.getExportLocation().isEmpty()) {
				setExportLocationField(config.getExportLocation());
			}
		}
	}

	private boolean isValidFile(String fileName) {
		return !fileName.isEmpty() && new File(fileName).isFile();
	}

	@FXML
	void setExportLocationField(ActionEvent event) {
		Window window = getWindow(event);
		File file = directoryChooser.showDialog(window);

		if (file != null) {
			setExportLocationField(file.getAbsolutePath());
		}
	}

	private void setExportLocationField(String path) {
		exportLocationField.setText(path);
	}

	private Window getWindow(ActionEvent event) {
		Node rootNode = (Node) event.getSource();
		return rootNode.getScene().getWindow();
	}

	@FXML
	void processMessage(ActionEvent event) {
		UserNotification notification;

		try {
			byte[] messageBytes = getMessageContentAsBytes();
			createMessage(messageBytes);

			notification = new UserNotification(SUCCESFUL.wording, GREEN);
		} catch (Exception e) {
			notification = new UserNotification(FAILED.wording + ": " + e.getMessage(), RED);
		}

		displayMessage(notification);
	}

	private void displayMessage(UserNotification notification) {
		successOrFailureMessage.setTextFill(notification.getColor());
		successOrFailureMessage.setText(notification.getMessage());
	}

	private byte[] getMessageContentAsBytes() {
		try {
			String message = hl7MessageField.getText();

			return message.getBytes();
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(ERROR.wording);
		}
	}

	private void createMessage(byte[] messageBytes) throws Exception {
		String path = exportLocationField.getText();

		if (!path.isEmpty()) {
			try (FileOutputStream os = new FileOutputStream(path + "/" + config.getDefaultFileName() + ".hl7")) {
				os.write(messageBytes);
			} catch (IOException e) {
				throw e;
			}
		} else {
			throw new IllegalArgumentException(ERROR.wording);
		}
	}
}
