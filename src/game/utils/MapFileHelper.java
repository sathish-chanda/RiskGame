package game.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapFileHelper {

    private static MapFileHelper instance = null;

    private MapFileHelper() {
        // private constructor needed for singleton class
    }

    public static MapFileHelper getInstance() {
        if (instance == null) {
            instance = new MapFileHelper();
        }
        return instance;
    }

    /**
     * This method is used to load the map file.
     */
    public void loadMapFile() {
        try {
            FileReader fileReader = new FileReader(Constants.FILE_DOMAIN_PATH + Constants.MAP_FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {

            }
        } catch (IOException e) {
            // TODO show dialog if file not found
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to save the map file
     */
    public void saveMapFile() {

    }

    /**
     * This method is used to validate map file
     */
    public void validateMapFile() {

    }

}
