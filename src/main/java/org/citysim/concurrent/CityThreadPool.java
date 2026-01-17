package org.citysim.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CityThreadPool {
    private final ScheduledExecutorService scheduler;

    public CityThreadPool(int threads) {
        this.scheduler = Executors.newScheduledThreadPool(threads);
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public void scheduleAtFixedRate(Runnable task, long initialDelay,
                                    long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public void shutdown() {
        scheduler.shutdown();
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

    public void safeShutdown(long timeout, TimeUnit unit) {
        try {
            scheduler.shutdown();

            if (!scheduler.awaitTermination(timeout, unit)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}