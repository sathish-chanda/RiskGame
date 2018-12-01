package game.model;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;


public class FileHelper {

    public static  void writeFileHepler(String context) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
            out.write(context);
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
