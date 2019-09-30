package search;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

public class FileIndexer implements Runnable {
    private final BlockingDeque<File> blockingDeque;
    private final Map<String,String> indexMap;
    public FileIndexer(BlockingDeque<File> blockingDeque){
        indexMap = new HashMap<>();
        this.blockingDeque = blockingDeque;
    }
    @Override
    public void run() {
        try {

            while (true) {
                indexFile(blockingDeque.take());
            }
        }catch (Exception e){
            System.out.println("thread interrupted...");
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        System.out.println("thread quit!");
    }
    private void indexFile(File file){
        String name = file.getName();
        String path = file.getPath();
        System.out.println("创建索引："+name+" path:"+path);
        indexMap.put(name,path);
    }
    public BlockingDeque<File> getBlockingDeque(){
        return blockingDeque;
    }
}
