package be.kapture.hl7sender.gui;

public enum Wordings {
    SUCCESFUL("Successfully send message."),
    FAILED("Failed to send message: "),
    ERROR("The export location and message can not be empty.");

    String wording;

    Wordings(String wording) {
        this.wording = wording;
    }
}
