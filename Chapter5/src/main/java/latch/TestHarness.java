package latch;

import java.util.concurrent.CountDownLatch;

public class TestHarness {
    public long timeTask(int nThreads,final Runnable task){
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            new Thread(()->{
                try {
                    startLatch.await();
                    task.run();
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }finally {
                    System.out.println("Thread "+Thread.currentThread().getName()+" finish");
                    endLatch.countDown();
                }
            }).start();
        }
        long startTime = System.currentTimeMillis();
        System.out.println("Threads ready to start!");
        startLatch.countDown();
        try {
            endLatch.await();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }finally {
            long endTime = System.currentTimeMillis();
            return endTime-startTime;
        }
    }
}
