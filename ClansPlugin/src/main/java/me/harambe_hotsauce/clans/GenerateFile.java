package me.harambe_hotsauce.clans;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GenerateFile {

    static String path;
    static String directory;

    public GenerateFile(String directory, String fileName) {
        this.directory = directory;
        try {
            File file1 = new File("plugins/" + directory);
            if (file1.exists()) {
                //do nothing
            } else {
                file1.mkdir();
            }
            File file = new File("plugins/" + directory + "/" + fileName);
            path = file.getPath();
            if (file.createNewFile()) {
                System.out.println(fileName + " created!");
            } else {
                System.out.println(fileName + " exists!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFilePath() {
        return path;
    }
}
