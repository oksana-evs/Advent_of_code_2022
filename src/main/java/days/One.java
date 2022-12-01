package days;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class One {

    public int getMostNCalories(int n) throws IOException {
        ArrayList<Integer> list = getCaloriesList();
        list.sort(Comparator.reverseOrder());
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum+=list.get(i);
        }
        return sum;
    }

    private ArrayList<Integer> getCaloriesList() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("1.12_1.txt").getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String strLine;
        ArrayList<Integer> list = new ArrayList<>();
        int calories = 0;
        while ((strLine = br.readLine()) != null)   {
            if (strLine.equals("")) {
                list.add(calories);
                calories = 0;
                continue;
            }
            int value = Integer.parseInt(strLine);
            calories+=value;
        }
        return list;
    }
}
