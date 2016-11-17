import java.io.IOException;

public class MainApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        TimeLogger logger = new TimeLogger("D:\\time.txt", true);
        logger.setLatency(2 * 1000);    // every 2 seconds
        logger.startLogging();

        for (int i = 1; i <= 60; i++) {
            System.out.println(i);
            Thread.sleep(1000);
        }
        logger.stopLogging();
    }
}
