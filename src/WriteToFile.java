import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class for writing lines of text/numbers to a file.
 */
public class WriteToFile {
    private BufferedWriter out;

    /**
     * Creates a new file.
     * @param filename The path/name of the file.
     */
    public WriteToFile(String filename) {
        try {
            out = new BufferedWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a single line to the end of the file.
     * @param number
     */
    public void writeLine(int number) {
        try {
            out.write(Integer.toString(number));
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the file.
     */
    public void close() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
