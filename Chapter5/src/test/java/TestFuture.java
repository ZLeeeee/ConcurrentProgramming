import future.PreLoad;

public class TestFuture {
    public static void main(String[] args) {
        PreLoad preLoad = new PreLoad();
        preLoad.start();
        System.out.println(preLoad.get());
    }
}
