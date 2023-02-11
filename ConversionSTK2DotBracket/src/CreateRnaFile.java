import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CreateRnaFile {
    File file;

    public CreateRnaFile(String location, String name) throws IOException {
        file = new File(location + "\\" + name + ".txt");//RNA file path
        try {
            if (file.createNewFile())
                System.out.println("File created!");
        } catch (IOException e) {
            throw e;
        }
    }

    public void writeToFile(String primary, String secondary) {
        BufferedWriter bf = null;

        try {
            bf = new BufferedWriter(new FileWriter(file));


            bf.write(primary);
            bf.newLine();
            bf.write(secondary);

            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            try {
                bf.close();
            } catch (Exception e) {
            }
        }
    }


}
