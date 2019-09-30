import search.FileCrawler;
import search.FileIndexer;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class TestFileCrawler {
    public static void main(String[] args) {
        BlockingDeque<File> blockingDeque = new LinkedBlockingDeque<>();
        FileCrawler fileCrawler = new FileCrawler(blockingDeque, new File("D:\\赤湾东方APP\\2019_TM APP开发\\APP项目一期"), (p)->{
            System.out.println(p);
            return true;
        });
        FileIndexer fileIndexer = new FileIndexer(blockingDeque);
        new Thread(fileCrawler).start();
        new Thread(fileIndexer).start();
        try {

            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("1111111");
    }
}
