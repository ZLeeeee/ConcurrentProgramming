package canclable;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

public interface CancelableTask<V> extends Callable<V> {
    void cancel();
    RunnableFuture<V> newTask();
}
