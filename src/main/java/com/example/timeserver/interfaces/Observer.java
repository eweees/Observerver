package com.example.timeserver.interfaces;

public interface Observer {
    void update(int currentTime, boolean serverRunning);
}