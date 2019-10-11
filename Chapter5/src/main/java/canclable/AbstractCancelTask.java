package canclable;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public abstract class AbstractCancelTask<V> implements CancelableTask<V> {
    private Closeable closeable;
    @Override
    public synchronized void cancel() {
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void setCloseable (Closeable closeable){
        this.closeable = closeable;
    }
    @Override
    public RunnableFuture<V> newTask() {
        return new FutureTask<V>(this){
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                AbstractCancelTask.this.cancel();
                return super.cancel(mayInterruptIfRunning);
            }
        };
    }
}
