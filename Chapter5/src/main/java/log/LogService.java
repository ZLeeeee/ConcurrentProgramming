package log;

import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class LogService {
    private final BlockingDeque<String> blockingDeque = new LinkedBlockingDeque<>();
    private volatile boolean isShutdown = false;
    private volatile int reservations = 0;
    private final LogThread logThread = new LogThread();

    public void log(String info){
        try {
            synchronized (LogService.this) {
                if (isShutdown) {
                   throw new IllegalStateException();
                }else {
                    reservations++;
                }
            }
            blockingDeque.put(info);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class LogThread extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (LogService.this) {
                    if (isShutdown && reservations == 0) {
                        break;
                    }
                }
                try {
                    System.out.println(blockingDeque.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LogService.this) {
                    reservations--;
                }
            }
        }
    }
}
