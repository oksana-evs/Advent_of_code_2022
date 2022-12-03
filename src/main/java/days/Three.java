package days;

import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Three {
    public int getPrioritiesSum() throws IOException {
        ArrayList<Character> result = new ArrayList<>();
        ArrayList<String> lines = Utils.getLines("3.12.txt");
        for (String line : lines) {
            String first = line.substring(0, (line.length() / 2));
            String second = line.substring(line.length() / 2);
            result.addAll(getCommonChars(first, second));
        }
        return result
                .stream()
                .mapToInt(this::getValue)
                .sum();

    }

    public int getGroupSum() throws IOException {
        ArrayList<Character> result = new ArrayList<>();
        ArrayList<String> lines = Utils.getLines("3.12.txt");
        for (int i = 1; i <= lines.size(); i++) {
            if (i % 3 == 0) {
                String first = lines.get(i - 3);
                String second = lines.get(i - 2);
                String third = lines.get(i - 1);
                result.addAll(getCommonChars(first, second, third));
            }
        }
        return result
                .stream()
                .mapToInt(this::getValue)
                .sum();
    }

    private List<Character> getCommonChars(String first, String second) {
        List<Character> list1 = first.chars().mapToObj(c -> (char) c).toList();
        List<Character> list2 = second.chars().mapToObj(c -> (char) c).toList();
        return list1.stream()
                .filter(list2::contains)
                .distinct()
                .toList();
    }

    private List<Character> getCommonChars(String first, String second, String third) {
        List<Character> list1 = first.chars().mapToObj(c -> (char) c).toList();
        List<Character> list2 = second.chars().mapToObj(c -> (char) c).toList();
        List<Character> list3 = third.chars().mapToObj(c -> (char) c).toList();
        return list1.stream()
                .filter(list2::contains)
                .filter(list3::contains)
                .distinct()
                .toList();
    }

    private int getValue(Character ch) {
        return Character.isUpperCase(ch) ? ch.hashCode() - 38 : ch.hashCode() - 96;

    }

}
