package days;

import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Ten {

    private int currentCycle = 0;
    private int currentValue = 1;
    private int currentSpriteStart = 0;
    private String row = "";

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
                currentValue += valueToAdd;
            } else {
                map.put(++currentCycle, currentValue);
            }
        }
        return map.entrySet()
                .stream()
                .filter(t -> cycles.contains(t.getKey()))
                .mapToInt(t -> t.getKey() * t.getValue())
                .sum();

    }

    public void draw() throws IOException {
        ArrayList<String> lines = Utils.getLines("10.12.txt");
        for (String line : lines) {
            row = doDraw(currentValue, currentSpriteStart, row);
            increaseCycle();
            if (line.startsWith("addx")) {
                int valueToAdd = Integer.parseInt(line.split(" ")[1]);
                currentValue += valueToAdd;
                row = doDraw(currentValue, currentSpriteStart, row);
                increaseCycle();
            }
        }

    }

    private void increaseCycle() {
        currentCycle++;
        currentSpriteStart++;
        if ((currentCycle) % 40 == 0) {
            currentSpriteStart = 0;
            System.out.println(row);
            row = "";
        }
    }

    private static String doDraw(int currentValue, int currentSpriteStart, String row) {
        if (currentSpriteStart == currentValue || currentSpriteStart + 1 == currentValue || currentSpriteStart + 2 == currentValue) {
            row = row + "#";
        } else {
            row = row + ".";
        }
        return row;
    }
}
