import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class TestOOM {
    /***
     * JVM Args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
     */
    @Test
    public void heapOOM(){
        LinkedList<Object> objects = new LinkedList<>();
        while(true){objects.add(new Object());}
    }
    /***
     * JVM Args:-Xss128k
     */
    @Test
    public void stackOOM(){
        stackOOM();
    }
    /***
     * JVM Args:-Xss2m
     */
    @Test
    public void stackOOMByThread(){
        while (true) new Thread(()->{while (true){}}).start();
    }

    /***
     * JVM Args:-XX:PermSize=10m -XX:MaxPermSize=10m
     */
    @Test
    public void permOOM(){
        LinkedList<String> strings = new LinkedList<>();
        int i = 0;
        while (true) {
            strings.add(String.valueOf(i++).intern());
        }
    }
    @Test
    public void testPerm(){
        String s = new StringBuilder().append("计算").append("技术").toString();
        System.out.println(s.intern() == s);
        String s2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(s2.intern() == s2);
        String s3 = new StringBuilder().append("66").append("66").toString();
        System.out.println(s3.intern() == s3);

    }

    /***
     * JVM Args:-Xmx20M -XX:MaxDirectMemorySize=10M
     * @throws Exception
     */
    @Test
    public void directMemoryOOM()throws Exception {
        Field unsafe = Unsafe.class.getDeclaredFields()[0];
        unsafe.setAccessible(true);
        Unsafe u = (Unsafe)unsafe.get(null);
        int _1MB = 1024*1024;
        while (true){
            u.allocateMemory(_1MB);
        }
    }

}
