package days;

import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class Four {
    public int[] getNumberOfRanges() throws IOException {
        int included = 0;
        int overlapped = 0;
        ArrayList<String> lines = Utils.getLines("4.12.txt");
        for (String line : lines) {
            String[] split = line.split(",");
            Range range1 = getRange(split[0]);
            Range range2 = getRange(split[1]);
            if (range1.contains(range2) || range2.contains(range1)) {
                included++;
            }
            if (range1.overlaps(range2)) {
                overlapped++;
            }
        }

        return new int[]{included, overlapped};
    }


    private Range getRange(String line) {
        String[] numbers = line.split("-");
        return new Range(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));

    }

    record Range(int start, int end) {
        public boolean contains(Range other) {
            return this.start <= other.start() && this.end >= other.end();
        }

        public boolean overlaps(Range other) {
            return this.start <= other.end() && other.start <= this.end();
        }
    }
}
