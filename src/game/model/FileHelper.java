package game.model;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;


public class FileHelper {

    public void writeFileHepler(String context) {
        try {
            File writename = new File("C:\\output.txt");
            writename.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(context);
            out.flush();
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
