public class ThreadC extends Thread {

    private static final String FILENAME = "C.txt";

    private SharedMemory sharedMemory;
    private WriteToFile writeToFile;

    public ThreadC (SharedMemory sharedMemory) {
        this.sharedMemory = sharedMemory;
        this.writeToFile = new WriteToFile(FILENAME);
    }

    public void run() {
        try {
            while (sharedMemory.getCountLeft() != 0) {
                if (sharedMemory.readData() % 2 != 0) {
                    writeToFile.writeLine(sharedMemory.takeData());
                } else {
                    sharedMemory.finish();
                }
            }
            sharedMemory.finish();
            writeToFile.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
