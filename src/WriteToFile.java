import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
    private BufferedWriter out;

    public WriteToFile(String filename) {
        try {
            out = new BufferedWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String text) throws IOException {
        try {
            out.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
