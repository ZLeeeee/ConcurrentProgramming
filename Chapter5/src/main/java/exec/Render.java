package exec;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Render {
    private final Executor executor;
    public Render(Executor executor){
        this.executor = executor;
    }
    public void renderPage(){
        List<Integer> list = scanImages();
        Random random = new Random();
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executor);
        for (Integer integer : list) {
            completionService.submit(()->{
                TimeUnit.SECONDS.sleep(random.nextInt(10));
                return integer+666;
            });
        }
        renderText();

        for (int i = 0; i < list.size(); i++) {
            Future<Integer> f = null;
            try {
                f = completionService.take();
                renderImage(f.get(0,TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e){
                e.getCause().printStackTrace();
            } catch (TimeoutException e){
                e.printStackTrace();
                f.cancel(true);
                renderImage(555);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    List<Integer> scanImages(){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(i);
        }
        return list;
    }
    void renderText (){}
    void renderImage(Integer i){
        System.out.println(i);
    }
}
