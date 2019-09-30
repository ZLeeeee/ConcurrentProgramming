package barrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CellularAutomata {
    private final CyclicBarrier barrier;
    private final Worker[] workers;
    private final List<Integer> results;
    private Integer result = null;
    private final CountDownLatch endLatch;
    public CellularAutomata(){
        int i = Runtime.getRuntime().availableProcessors();
        workers = new Worker[i];
        endLatch = new CountDownLatch(i+1);
        results = Collections.synchronizedList(new ArrayList<>(i));
        barrier = new CyclicBarrier(i,()->{
            mergeResult();
        });
        for (int j = 0; j < i; j++) {
            workers[j] = new Worker(j);
        }
    }
    private void mergeResult(){
        result = results.stream().reduce(Integer::sum).get();
        endLatch.countDown();
    }
    public void start(){
        for (Worker worker : workers) {
            new Thread(worker).start();
        }
    }
    public int getResult(){
        try {
            endLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    private class Worker implements Runnable{
        private Worker(int index){
            this.index = index;
        }
        private int index;
        @Override
        public void run() {
            int j = 0;
            for (int i = 1; i <= 1000; i++) {
                j+=i;
            }
            results.add(j);
            try {
                endLatch.countDown();
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
