package com.example.timeserver.interfaces;

public interface Subject {
    void start();
    void stop();
    void reset();
    void attach(Observer observer);
    void detach(Observer observer);
    int getState();
    boolean isRunning();
    void notifyAllObservers(int currentTime, boolean serverRunning);
}