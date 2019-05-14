package be.kapture.hl7sender.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static be.kapture.hl7sender.gui.Wordings.*;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;


@Controller
public class Hl7Controller {
    final FileChooser fileChooser = new FileChooser();

    @FXML
    private TextArea hl7MessageField;

    @FXML
    private TextField exportLocation;

    @FXML
    private Label successOrFailureMessage;

    @FXML
    void setExportLocation(ActionEvent event) {
        Window window = getWindow(event);
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            exportLocation.setText(file.getAbsolutePath());
        }
    }

    private Window getWindow(ActionEvent event) {
        Node rootNode = (Node) event.getSource();
        return rootNode.getScene().getWindow();
    }

    @FXML
    void processMessage (ActionEvent event) {
        try {
            createHl7Message();

            successOrFailureMessage.setTextFill(GREEN);
            successOrFailureMessage.setText(SUCCESFUL.wording);
        } catch (Exception e) {
            successOrFailureMessage.setTextFill(RED);
            successOrFailureMessage.setText(FAILED.wording + e.getMessage());
        }
    }

    private void createHl7Message() throws Exception{
        String path = exportLocation.getText();
        String message = hl7MessageField.getText();

        if (!(path.isEmpty() || message.isEmpty())) {
            byte[] hl7Bytes = message.getBytes();

            try (FileOutputStream os = new FileOutputStream(path)){
                os.write(hl7Bytes);
            } catch (IOException e) {
                throw e;
            }
        } else {
            throw new IllegalArgumentException(ERROR.wording);
        }
    }
}
