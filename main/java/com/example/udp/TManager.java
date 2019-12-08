package com.example.udp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;


public class TManager  {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int KEEP_ALIVE_TIME = 50;
    private static TManager managerInstance = null;
    private int taskCounter = 0;
    Future<?> FutureArray[] ;
     BlockingQueue<Runnable> WorkQueue;
    private ThreadPoolExecutor threadPoolExecutor;
    static {
        managerInstance = new TManager();
    }
    private TManager() {
        FutureArray = new Future[20];
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
    public void Cancel()
    {
        if(FutureArray.length == 0)
            return;
        FutureArray[taskCounter-1].cancel(true);
    }
    public void submitTask(Runnable run)
    {

       Future<?> tempFut = threadPoolExecutor.submit(run);
       if(FutureArray == null)
       {
           FutureArray[0] =tempFut;
           taskCounter++;
       }else {
           FutureArray[taskCounter] = tempFut;
           taskCounter++;

       }


    }

}
