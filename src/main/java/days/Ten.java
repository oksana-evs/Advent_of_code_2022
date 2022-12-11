package days;

import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Ten {
    public int getStrength(List<Integer> cycles) throws IOException {
        ArrayList<String> lines = Utils.getLines("10.12.txt");
        int currentValue = 1;
        int currentCycle = 0;
        Map<Integer, Integer> map = new LinkedHashMap<>();
        for (String line : lines) {
            if (line.startsWith("addx")) {
                int valueToAdd = Integer.parseInt(line.split(" ")[1]);
                map.put(++currentCycle, currentValue);
                map.put(++currentCycle, currentValue);
                currentValue+=valueToAdd;
            } else {
                map.put(++currentCycle, currentValue);
            }
        }
        return map.entrySet()
                .stream()
                .filter(t -> cycles.contains(t.getKey()))
                .mapToInt(t -> t.getKey()*t.getValue())
                .sum();

    }

    public void draw() throws IOException {
        ArrayList<String> lines = Utils.getLines("10.12.txt");
        int currentValue = 1;
        int currentCycle = 0;
        int currentSpriteStart = 0;
        String row = "";
        for (String line: lines) {
            if (line.startsWith("addx")) {
                if (currentSpriteStart == currentValue || currentSpriteStart + 1 == currentValue || currentSpriteStart + 2 == currentValue) {
                    row = row + "#";
                } else {
                    row = row + ".";
                }
                currentCycle++;
                currentSpriteStart++;
                int valueToAdd = Integer.parseInt(line.split(" ")[1]);
                currentValue+=valueToAdd;
                row = getString(currentValue, currentSpriteStart, row);
                currentCycle++;
                currentSpriteStart++;
            } else {
                if (currentSpriteStart == currentValue || currentSpriteStart + 1 == currentValue || currentSpriteStart + 2 == currentValue) {
                    row = row + "#";
                } else {
                    row = row + ".";
                }
                currentCycle++;
                currentSpriteStart++;
            }

            //new line
            if ((currentCycle) % 40 == 0) {
                currentSpriteStart = 0;
                System.out.println(row);
                row = "";
            }
        }

    }

    private static String getString(int currentValue, int currentSpriteStart, String row) {
        if (currentSpriteStart == currentValue || currentSpriteStart + 1 == currentValue || currentSpriteStart + 2 == currentValue) {
            row = row + "#";
        } else {
            row = row + ".";
        }
        return row;
    }
}
