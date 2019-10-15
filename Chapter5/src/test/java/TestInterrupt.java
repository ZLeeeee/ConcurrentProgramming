import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class TestInterrupt {
    @Test
    public void test1(){
        new Thread(()->{
            throw new RuntimeException();
        }).start();
    }
    @Test
    public void test2(){
        ScheduledExecutorService scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        scheduledThreadPoolExecutor.schedule(()->{
            throw new RuntimeException();
        },1, TimeUnit.SECONDS);
        class TimeRun implements Runnable{
            private Throwable throwable;
            @Override
            public void run() {
                try{
                    throw new RuntimeException();
                }catch (Throwable t){
                    this.throwable = t;
                }
            }
            public void reThrow() throws Throwable {
                throw throwable;
            }
        }
        TimeRun timeRun = new TimeRun();
        Thread thread = new Thread(timeRun);
        thread.start();
        scheduledThreadPoolExecutor.schedule(thread::interrupt,1,TimeUnit.SECONDS);
        try {
            thread.join(1000);
            timeRun.reThrow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Throwable t){
            t.printStackTrace();
        }
    }
    @Test
    public void test3(){
        final Random random= new Random();
        final ThreadLocal<String> threadLocal = ThreadLocal.withInitial(()->String.valueOf(random.nextInt(66)));
        threadLocal.set("7777");
        for (int i = 0; i < 10; i++) {
            final int j = i;
            new Thread(()->{
                //threadLocal.set(String.valueOf(j));
                System.out.println(threadLocal.get());
            }).start();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(()->System.out.println("666")));
    }
    @Test
    public void test4(){
        List<Integer> listSync = new ArrayList<Integer>() {
            @Override
            public synchronized boolean add(Integer e) {
                return super.add(e);
            }
        };
        List<Integer> listRW = new ArrayList<Integer>() {
            final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
            @Override
            public boolean add(Integer e) {
                try {
                    reentrantReadWriteLock.writeLock().lock();
                    return super.add(e);
                }finally {
                    reentrantReadWriteLock.writeLock().unlock();
                }
            }
            @Override
            public Integer get(int i){
                try {
                    reentrantReadWriteLock.readLock().lock();
                    return super.get(i);
                }finally {
                    reentrantReadWriteLock.readLock().unlock();
                }
            }
        };
        Long aLong = timeTest(listSync);
        Long aLong1 = timeTest(listRW);
        System.out.println("RW:"+aLong1);
        System.out.println("sync:"+aLong);
    }
    public Long timeTest(List<Integer> listSync){
        CountDownLatch endLatch = new CountDownLatch(100);
        CountDownLatch startLatch = new CountDownLatch(1);
        for (int i = 0; i < 10000; i++) {
            listSync.add(i);
        }
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                Random random = new Random();
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (random.nextInt(10)>3){
                    for (int j = 0; j < 10000; j++) {
                        System.out.println(Thread.currentThread().getName()+" : "+j);
                    }
                }else{
                    for (int j = 0; j < 10000; j++) {
                        listSync.add(j);
                    }
                }
                endLatch.countDown();
            }).start();
        }
        try {
            startLatch.countDown();
            long startTime = System.currentTimeMillis();
            endLatch.await();
            long endTime = System.currentTimeMillis();
            long l = endTime - startTime;
            System.out.println("Time Millis :"+l);
            return l;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
