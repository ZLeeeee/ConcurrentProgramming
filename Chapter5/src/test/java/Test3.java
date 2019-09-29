import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class Test3 {
    private String name;
    private static final String loc = "1";

    public static synchronized void foo(){
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        System.out.println("666");
    }
    public synchronized String getName() {

            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
            System.out.println(name + "getName");
            return name;

    }

    public synchronized void setName(String name) {

            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }

            this.name = name;
            System.out.println(name + "setName");

    }

    public static void main(String[] args) {
        Test3 test = new Test3();
        Test3 test1 = new Test3();
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }


            new Thread(()->{
                Iterator<Integer> iterator = integers.iterator();
                try {
            while (iterator.hasNext()){
                Thread.sleep(1000);
                System.out.println(iterator.next());
            }
            }catch (Exception e){
                e.printStackTrace();
            }
            }).start();
        new Thread(()->{
            Iterator<Integer> iterator = integers.iterator();
            try {
                while (iterator.hasNext()){

                    Thread.sleep(1000);
                    iterator.next();
                    iterator.remove();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
        new Thread(()->test.setName("66")).start();
        new Thread(()->test.getName()).start();
        new Thread(()->test1.setName("77")).start();
        new Thread(()->test1.getName()).start();
        new Thread(Test3::foo).start();
        new Thread(Test3::foo).start();
    }
}
