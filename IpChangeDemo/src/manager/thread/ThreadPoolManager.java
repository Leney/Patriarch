package manager.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {

    private static volatile ThreadPoolManager instance = null;

    /** 线程池中线程的数量*/
    private static final int CORE_THREAD_SIZE = 3;

    private ExecutorService threedPool;

    private ThreadPoolManager(){
        threedPool = Executors.newFixedThreadPool(CORE_THREAD_SIZE);
    }

    public static ThreadPoolManager getInstance(){
        if(instance == null){
            synchronized (ThreadPoolManager.class){
                if(instance == null){
                    instance = new ThreadPoolManager();
                }
            }
        }
        return instance;
    }


    /**
     * 添加一个线程任务
     * @param task
     */
    public void addTask(Runnable task){
//        requestAdTasks.add(task);
        threedPool.submit(task);
    }
}
