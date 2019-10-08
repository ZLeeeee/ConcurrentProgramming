package cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer<A,V> implements Computable<A,V> {
    private final Map<A, FutureTask<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A,V> computable;
    public Memoizer(Computable<A,V> computable){
        this.computable = computable;
    }
    @Override
    public V compute(A a) {
        while (true) {
            FutureTask<V> vFutureTask1 = cache.get(a);
            if (vFutureTask1 == null) {
                FutureTask<V> vFutureTask = new FutureTask<>(() -> computable.compute(a));
                vFutureTask1 = cache.putIfAbsent(a, vFutureTask);
                if(vFutureTask1 == null){
                    vFutureTask1 = vFutureTask;
                    vFutureTask1.run();
                }

            }
            try {
                return vFutureTask1.get();
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
