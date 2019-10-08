package semaphore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        TestDataSourcePool testDataSourcePool = new TestDataSourcePool(10);
        final List<Integer> list = Collections.synchronizedList(new ArrayList<>(1000));
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    Integer connection = testDataSourcePool.getConnection();
                    list.add(connection);
                    Thread.sleep(10);
                    testDataSourcePool.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
        }
        try{Thread.sleep(10000);}catch (Exception e){}
        int collect = list.stream().reduce(Integer::sum).get();
        System.out.println(collect);
    }
}
