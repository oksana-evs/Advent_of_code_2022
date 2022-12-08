package days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import utils.Utils;

public class Seven {
  private static final String BACK = "$ cd ..";
  private static final String ROOT = "/";

  public int getSum() throws IOException {
    ArrayList<String> lines = Utils.getLines("7.12.txt");
    Map<String, ArrayList<String>> directoryWithContent = new LinkedHashMap<>();
    String currentDirectory = ROOT;
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      if (line.equals(BACK)) {
        int lastSlashIndex = currentDirectory.lastIndexOf('/');
        currentDirectory = currentDirectory.substring(0, lastSlashIndex);
      }
      if (line.startsWith("$ cd")) {
        ArrayList<String> content = new ArrayList<>();
        String directoryName = line.split(" ")[2];
        if(!Objects.equals(directoryName, "..")) {
          currentDirectory = currentDirectory.equals(ROOT) ? currentDirectory + directoryName : currentDirectory + "/" + directoryName;
          int j = i + 2;
          while (j < lines.size() && !lines.get(j).startsWith("$ cd")) {
            if (lines.get(j).contains("dir")) {
              content.add(currentDirectory + "/" + lines.get(j).split(" ")[1]);
            } else {
              content.add(currentDirectory + "/" + lines.get(j));
            }
            j++;
          }
          directoryWithContent.put(currentDirectory, content);
        }

      }
    }
    Map<String, Integer> directoryPerSize = directoryWithContent
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> getDirectorySize(e.getValue(), directoryWithContent)))
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
            Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    int total = directoryPerSize.get("//");
    int freeSpace = 70000000 - total;
    int toFree = 30000000 - freeSpace;
    return directoryPerSize
            .values()
            .stream()
            .filter(e -> e >= toFree)
            .mapToInt(Integer::intValue)
            .sorted()
            .findFirst()
            .getAsInt();
  }


  private static int getDirectorySize(ArrayList<String> content, Map<String, ArrayList<String>> directories) {
    int size = 0;
    for (String line : content) {
      if(line.contains(" ")) {
        String filename = line.split(" ")[0];
        int index = filename.lastIndexOf("/");
        size+= Integer.parseInt(filename.substring(index+1));
      } else {
        size+= getDirectorySize(directories.get(line), directories);
      }
    }
    return size;
  }
}
