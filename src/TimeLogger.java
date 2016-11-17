import java.io.*;

/**
 * Simple class for saving system time in a file every 20 minutes
 * */
public class TimeLogger {
    /**
     * Writer to use for write info in a file
     * */
    private final BufferedWriter writer;

    /**
     * Latency between writing cycles in milliseconds. 20 minutes by default.
     */
    private long latency = 20 * 60 * 1000;

    /**
     * Thread to be used for writing in a file and then sleeping 20 minutes
     * */
    private Thread thread;

    /**
     * @param stream any output stream implementation
     */
    public TimeLogger(final OutputStream stream) {
        this.writer = new BufferedWriter(new OutputStreamWriter(stream));
    }


    /**
     * @param file the file object that represents a file where to store timestamps
     * @param append boolean value: true for appending, false for overwrite
     * @throws IOException if the file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason
     */
    public TimeLogger(final File file, final boolean append) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(file, append));
    }

    /**
     * @param fileName String value of the file name in the file system
     * @param append boolean value: true for appending, false for overwrite
     * @throws IOException if the file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason
     */
    public TimeLogger(final String fileName, final boolean append) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(fileName, append));
    }

    /**
     * @return current latency value in milliseconds
     */
    public long getLatency() {
        return latency;
    }

    /**
     * @param latency new latency in milliseconds
     */
    public void setLatency(long latency) {
        this.latency = latency;
    }

    /**
     * Starts saving timestamps in file in a separate thread
     */
    public void startLogging() {
        this.thread = new Thread("Thread for saving timestamps in a file") {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        writer.write("" + System.currentTimeMillis());      // prepare current timestamp in milliseconds
                        writer.newLine();
                        writer.flush();                                     // write prepared data in a file right now
                        Thread.sleep(latency);                              // sleep for 20 minutes
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        };
        this.thread.start();
    }

    /**
     * Stops writing timestamp in a file and closes that file
     */
    public void stopLogging() {
        this.thread.interrupt();
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
