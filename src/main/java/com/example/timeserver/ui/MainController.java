package com.example.timeserver.ui;

import com.example.timeserver.components.*;
import com.example.timeserver.interfaces.Subject;
import com.example.timeserver.timeserver.TimeServer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController {

    private Subject timeServer;

    private TimeLabelComponent timeLabelComponent;
    private MusicPlayerComponent musicPlayerComponent;
    private ConditionalBlinkComponent blinkComponent;

    @FXML private Button startButton;
    @FXML private Button stopButton;
    @FXML private Button resetButton;

    @FXML private Label timeDisplayLabel;
    @FXML private VBox component1Panel;

    @FXML private Button playMusicButton;
    @FXML private Slider musicIntervalSlider;
    @FXML private Label musicIntervalLabel;
    @FXML private VBox component2Panel;

    @FXML private Pane blinkPane;
    @FXML private Button blinkButton;
    @FXML private Slider blinkConditionSlider;
    @FXML private Label blinkConditionLabel;
    @FXML private VBox component4Panel;

    @FXML private Label statusBarLabel;

    @FXML
    public void initialize() {
        timeServer = new TimeServer();

        initializeComponents();
        setupEventHandlers();
    }

    private void initializeComponents() {
        timeLabelComponent = new TimeLabelComponent(timeDisplayLabel);
        timeServer.attach(timeLabelComponent);

        musicPlayerComponent = new MusicPlayerComponent();
        timeServer.attach(musicPlayerComponent);

        musicIntervalSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int interval = newVal.intValue();
            musicPlayerComponent.setPlayInterval(interval);
            musicIntervalLabel.setText("Интервал: " + interval + " сек");
        });

        // Голубой цвет для анимации (мигание)
        blinkPane.setStyle("-fx-background-color: #E3F2FD; -fx-background-radius: 12; -fx-border-color: #42A5F5; -fx-border-width: 2;");
        blinkComponent = new ConditionalBlinkComponent(blinkPane);
        timeServer.attach(blinkComponent);

        blinkConditionSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int condition = newVal.intValue();
            blinkComponent.setBlinkCondition(condition);
            blinkConditionLabel.setText("Условие: каждые " + condition + " сек");
        });
    }

    private void setupEventHandlers() {
        startButton.setOnAction(e -> {
            timeServer.start();
            statusBarLabel.setText("Сервер запущен");
        });

        stopButton.setOnAction(e -> {
            timeServer.stop();
            statusBarLabel.setText("Сервер остановлен");
        });

        resetButton.setOnAction(e -> {
            if (timeServer instanceof TimeServer) {
                ((TimeServer) timeServer).reset();
                statusBarLabel.setText("Сервер сброшен");
            }
        });

        playMusicButton.setOnAction(e -> {
            boolean isActive = playMusicButton.getText().equals("Включить будильник");
            if (isActive) {
                musicPlayerComponent.setActive(true);
                playMusicButton.setText("Выключить будильник");
            } else {
                musicPlayerComponent.setActive(false);
                playMusicButton.setText("Включить будильник");
            }
        });

        blinkButton.setOnAction(e -> {
            boolean isActive = blinkButton.getText().equals("Включить анимацию");
            if (isActive) {
                blinkComponent.setActive(true);
                blinkButton.setText("Выключить анимацию");
            } else {
                blinkComponent.setActive(false);
                blinkButton.setText("Включить анимацию");
            }
        });
    }
}