package concurrent;

import jobs.CityJob;

public class CityThreadPool {
    public CityThreadPool(int threads) { }
    public void submit(CityJob job) { }
    public void scheduleRepeating(CityJob job, long intervalMs) { }
    public void shutdown() { }
}

