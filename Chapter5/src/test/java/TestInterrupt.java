import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.*;


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
        ExecutorService executorService = Executors.newCachedThreadPool();
    }
}
