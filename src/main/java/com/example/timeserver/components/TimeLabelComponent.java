package com.example.timeserver.components;

import com.example.timeserver.interfaces.Observer;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class TimeLabelComponent implements Observer {
    private Label timeLabel;

    public TimeLabelComponent(Label label) {
        this.timeLabel = label;
        this.timeLabel.setText("Прошло: 0 с");
    }

    @Override
    public void update(int currentTime, boolean serverRunning) {
        Platform.runLater(() -> {
            timeLabel.setText("Прошло: " + currentTime + " с");
            System.out.println("Passive TimeLabel received: " + currentTime + ", running: " + serverRunning);
        });
    }
}