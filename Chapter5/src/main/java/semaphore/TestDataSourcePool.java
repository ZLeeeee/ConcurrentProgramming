package semaphore;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;

public class TestDataSourcePool implements Closeable {
    private final Queue<Integer> queue;
    private final Semaphore semaphore;
    public TestDataSourcePool(int capacity){
        semaphore = new Semaphore(capacity);
        queue = new LinkedBlockingDeque<>();
        init(capacity);
    }
    private void init(int capacity){
        for(int i = 1;i <= capacity;i++)
            queue.add(i);
    }
    public Integer getConnection(){
        try {
            semaphore.acquire();
            Integer poll = queue.poll();
            queue.add(poll);
            return poll;
        }catch (InterruptedException e){
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    public void close() throws IOException {
        semaphore.release();
    }
}
