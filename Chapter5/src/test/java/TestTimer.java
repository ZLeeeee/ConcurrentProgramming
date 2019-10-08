import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TestTimer extends TimerTask {
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TestTimer(),1);
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e){

        }
        timer.schedule(new TestTimer(),1);
        try {
            TimeUnit.SECONDS.sleep(5);
        }catch (Exception e){

        }
    }
}
