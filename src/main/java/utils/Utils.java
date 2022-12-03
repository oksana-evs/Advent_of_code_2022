package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

    public static ArrayList<String> getLines(String fileName) throws IOException {
        ClassLoader classLoader = Utils.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String strLine;
        ArrayList<String> list = new ArrayList<>();
        while ((strLine = br.readLine()) != null)   {
            list.add(strLine);
        }
        return list;
    }
}
