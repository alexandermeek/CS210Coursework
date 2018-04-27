public class ConsumerThread extends Thread {

    public static final int B = 0;
    public static final int C = 1;
    public static final int D = 2;

    private static final String B_FILENAME = "B.txt";
    private static final String C_FILENAME = "C.txt";
    private static final String D_FILENAME = "D.txt";

    private int consumerType;
    private SharedMemory sharedMemory;
    private WriteToFile writeToFile;

    public ConsumerThread (int consumerType, SharedMemory sharedMemory) {
        this.sharedMemory = sharedMemory;
        this.consumerType = consumerType;
        switch (this.consumerType) {
            case B:
                this.writeToFile = new WriteToFile(B_FILENAME);
                break;
            case C:
                this.writeToFile = new WriteToFile(C_FILENAME);
                break;
            case D:
                this.writeToFile = new WriteToFile(D_FILENAME);
                break;
            default:
                this.writeToFile = new WriteToFile(B_FILENAME);
                break;
        }

    }

    public void run() {
        try {
            switch (this.consumerType) {
                case B:
                    bRun();
                    break;
                case C:
                    cRun();
                    break;
                case D:
                    dRun();
                    break;
                default:
                    bRun();
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void bRun() throws InterruptedException {
        while (sharedMemory.getCountLeft() != 0) {
            if (sharedMemory.readData() % 2 == 0) {
                writeToFile.writeLine(sharedMemory.takeData());
            } else {
                sharedMemory.finish();
            }
        }
        sharedMemory.finish();
        writeToFile.close();
    }

    private void cRun() throws InterruptedException {
        while (sharedMemory.getCountLeft() != 0) {
            if (sharedMemory.readData() % 2 != 0) {
                writeToFile.writeLine(sharedMemory.takeData());
            } else {
                sharedMemory.finish();
            }
        }
        sharedMemory.finish();
        writeToFile.close();
    }

    private void dRun() throws InterruptedException {
        while (sharedMemory.getCountLeft() != 0) {
            if (sharedMemory.readData() % 3 == 0) {
                writeToFile.writeLine(sharedMemory.takeData());
            } else {
                sharedMemory.finish();
            }
        }
        sharedMemory.finish();
        writeToFile.close();
    }
}

