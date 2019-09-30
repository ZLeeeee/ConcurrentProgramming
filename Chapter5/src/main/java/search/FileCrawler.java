package search;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingDeque;

public class FileCrawler implements Runnable {
    private final BlockingDeque<File> fileQueue;
    private final File root;
    private final FileFilter fileFilter;
    public FileCrawler(BlockingDeque<File> fileQueue,File root,FileFilter fileFilter){
        this.fileQueue = fileQueue;
        this.root = root;
        this.fileFilter = fileFilter;
    }
    private void crawler(File root) throws InterruptedException{
        File[] files = root.listFiles(fileFilter);
        if(files !=null){
            for (File file : files) {
                if (file.isDirectory()) {
                    crawler(file);
                }else{
                    fileQueue.put(file);
                }
            }
        }


    }
    @Override
    public void run() {
        try {
            crawler(root);
            System.out.println("扫描完毕！");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public BlockingDeque<File> getFileQueue(){
        return fileQueue;
    }
}
