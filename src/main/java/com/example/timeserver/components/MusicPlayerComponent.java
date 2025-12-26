package com.example.timeserver.components;

import com.example.timeserver.interfaces.Observer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayerComponent implements Observer {
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private int playInterval = 5;
    private int lastPlayedAt = 0;

    public MusicPlayerComponent() {
        try {
            String soundFile = getClass().getResource("/com/example/timeserver/sounds/alarm.mp3").toString();
            Media media = new Media(soundFile);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(1);
        } catch (Exception e) {
            System.err.println("Ошибка загрузки звука: " + e.getMessage());
        }
    }

    public void setPlayInterval(int seconds) {
        this.playInterval = seconds;
    }

    public void setActive(boolean active) {
        this.isPlaying = active;
        if (!active && mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void update(int currentTime, boolean serverRunning) {
        if (!isPlaying || mediaPlayer == null) return;

        if (currentTime > 0 && currentTime % playInterval == 0 && currentTime != lastPlayedAt) {
            lastPlayedAt = currentTime;

            javafx.application.Platform.runLater(() -> {
                mediaPlayer.stop();
                mediaPlayer.play();
                System.out.println("Будильник: проиграл в " + currentTime + " секунд(ы)");
            });
        }
    }
}