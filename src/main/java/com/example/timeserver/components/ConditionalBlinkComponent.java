package com.example.timeserver.components;

import com.example.timeserver.interfaces.Observer;
import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ConditionalBlinkComponent implements Observer {
    private Pane blinkPane;
    private FadeTransition blinkAnimation;
    private int blinkCondition = 7;
    private boolean isActive = false;

    public ConditionalBlinkComponent(Pane pane) {
        this.blinkPane = pane;
        setupBlinkAnimation();
    }

    private void setupBlinkAnimation() {
        blinkAnimation = new FadeTransition(Duration.seconds(0.5), blinkPane);
        blinkAnimation.setFromValue(1.0);
        blinkAnimation.setToValue(0.3);
        blinkAnimation.setAutoReverse(true);
        blinkAnimation.setCycleCount(4);
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void setBlinkCondition(int seconds) {
        this.blinkCondition = seconds;
    }

    @Override
    public void update(int currentTime, boolean serverRunning) {
        if (!isActive) return;

        if (currentTime > 0 && currentTime % blinkCondition == 0) {
            javafx.application.Platform.runLater(() -> {
                blinkAnimation.stop();
                blinkAnimation.playFromStart();
                System.out.println("Мигание: произошло в " + currentTime + " секунд(ы)");
            });
        }
    }
}