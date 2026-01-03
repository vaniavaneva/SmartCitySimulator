package org.citysim.concurent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CityThreadPool {
    private final ScheduledExecutorService scheduler;

    public CityThreadPool(int threads){
        scheduler = Executors.newScheduledThreadPool(threads);
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public void shutdown(){
        scheduler.shutdownNow();
    }

    public void shutdownNow() {
        scheduler.shutdownNow();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        return scheduler.awaitTermination(timeout, unit);
    }

    public boolean isTerminated() {
        return scheduler.isTerminated();
    }
}