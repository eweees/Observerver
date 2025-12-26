package com.example.timeserver.timeserver;

import com.example.timeserver.interfaces.Observer;
import com.example.timeserver.interfaces.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeServer implements Subject {
    private int timeState = 0;
    private boolean running = false;
    private Timer timer;
    private List<Observer> observers;

    private static final int DELAY = 1000;
    private static final int PERIOD = 1000;

    public TimeServer() {
        this.observers = new ArrayList<>();
        this.timer = new Timer(true);
    }

    private void tick() {
        if (running) {
            timeState++;
            notifyAllObservers(timeState, true);
            System.out.println("Passive Tick: " + timeState + " seconds");
        }
    }

    @Override
    public void notifyAllObservers(int currentTime, boolean serverRunning) {
        List<Observer> observersCopy = new ArrayList<>(observers);
        for (Observer observer : observersCopy) {
            try {
                observer.update(currentTime, serverRunning);
            } catch (Exception e) {
                System.err.println("Error notifying observer: " + e.getMessage());
            }
        }
    }

    public void notifyAllObservers() {
        notifyAllObservers(timeState, running);
    }

    @Override
    public void start() {
        if (!running) {
            running = true;
            timer.cancel();
            timer = new Timer(true);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    tick();
                }
            }, DELAY, PERIOD);

            System.out.println("Passive TimeServer started");
            notifyAllObservers(timeState, true);
        }
    }

    @Override
    public void stop() {
        if (running) {
            running = false;
            timer.cancel();
            timer = new Timer(true);
            System.out.println("Passive TimeServer stopped");
            notifyAllObservers(timeState, false);
        }
    }

    @Override
    public void reset() {
        boolean wasRunning = running;
        if (running) stop();
        timeState = 0;
        notifyAllObservers(0, wasRunning);
        if (wasRunning) start();
        System.out.println("Passive TimeServer reset to 0");
    }

    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer attached to passive server: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public void detach(Observer observer) {
        boolean removed = observers.remove(observer);
        if (removed) {
            System.out.println("Observer detached from passive server: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public int getState() {
        return timeState;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}