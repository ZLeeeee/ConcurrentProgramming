package future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class PreLoad {
    private final FutureTask<Integer> futureTask = new FutureTask<>(()->{
        int i = 0;
        for (; i < 1000000; i++){

        }
        return i;
    });
    private final Thread thread = new Thread(futureTask);
    public void start(){
        thread.start();
    }
    public int get(){
        try {
            return futureTask.get();
        }catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if(cause instanceof IllegalArgumentException)
                throw (IllegalArgumentException)cause;
            else
                throw launderThrowable(cause);
        }catch (InterruptedException i){
            Thread.currentThread().interrupt();
            return -1;
        }
    }
    public static RuntimeException launderThrowable(Throwable throwable){
        if(throwable instanceof RuntimeException)
            return (RuntimeException)throwable;
        else if(throwable instanceof Error)
            throw (Error)throwable;
        else
            throw new IllegalStateException("Not Checked");
    }
}
