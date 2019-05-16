package be.kapture.hl7sender.gui;

import javafx.scene.paint.Color;

public class UserNotification {
	private String message;
	private Color color;

	public UserNotification(String message, Color color) {
		this.message = message;
		this.color = color;
	}

	public String getMessage() {
		return message;
	}

	public Color getColor() {
		return color;
	}
}
