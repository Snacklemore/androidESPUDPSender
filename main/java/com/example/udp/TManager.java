package com.example.udp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TManager  {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int KEEP_ALIVE_TIME = 50;
    private static TManager managerInstance = null;
    final BlockingQueue<Runnable> WorkQueue;
    private final ThreadPoolExecutor threadPoolExecutor;
    static {
        managerInstance = new TManager();
    }
    private TManager() {
        WorkQueue = new LinkedBlockingDeque<Runnable>();
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS,WorkQueue);
    }
    public void runTask(Runnable runnable)
    {
        threadPoolExecutor.execute(runnable);
    }
    public static TManager getManagerInstance()
    {
        return managerInstance;
    }
}
