package days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import utils.Utils;

public class Eight {

  public long countTrees() throws IOException {
    ArrayList<String> lines = Utils.getLines("8.12.txt");

    int outerTrees = (lines.size() - 2) * 2 + lines.get(0).length() * 2;
    int visibleTrees = 0;
    long scenicScoreMax = 0;
    for (int i = 0; i < lines.size(); i++) {
      List<Integer> horizontalIntegers = Arrays.stream(lines.get(i).split(""))
              .map(Integer::parseInt)
              .toList();
      for (int j = 0; j < horizontalIntegers.size(); j++) {
        ArrayList<Integer> verticalIntegers = new ArrayList<>();
        for (String line : lines) {
          verticalIntegers.add(Character.getNumericValue(line.charAt(j)));
        }
        visibleTrees = getVisibleTrees(visibleTrees, i, horizontalIntegers, j, verticalIntegers);

        int currentHeight = horizontalIntegers.get(j);

        long leftView = 0;

        List<Integer> leftList = new ArrayList<>(horizontalIntegers.subList(0, j).stream().toList());
        Collections.reverse(leftList);
        for (int integer : leftList) {
          leftView++;
          if (integer >= currentHeight) {
            break;
          }
        }

        long rightView =  0;

        for (int integer : horizontalIntegers.subList(j +1, horizontalIntegers.size()) ) {
          rightView++;
          if (integer >= currentHeight) {
            break;
          }
        }

        long topView = 0;

        List<Integer> topList = new ArrayList<>(verticalIntegers.subList(0, i));
        Collections.reverse(topList);
        for (Integer integer : topList) {
          topView++;
          if (integer >= currentHeight) {
            break;
          }
        }


        long bottomView = 0;

        List<Integer> bottomList = verticalIntegers.subList(i + 1, verticalIntegers.size());
        for (Integer integer : bottomList) {
          bottomView++;
          if (integer >= currentHeight) {
            break;
          }
        }

        long scenicScore = leftView * rightView * topView * bottomView;
        if(scenicScore > scenicScoreMax) {
          scenicScoreMax = scenicScore;
        }
      }
    }
//    return outerTrees + visibleTrees;
    return scenicScoreMax;
  }

  private static int getVisibleTrees(int visibleTrees, int i, List<Integer> horizontalIntegers, int j, ArrayList<Integer> verticalIntegers) {
    if ((i > 0 && i < horizontalIntegers.size() - 1)
    && (j > 0 && j < verticalIntegers.size() - 1)) {
      boolean visibleLeft = Collections.max(
              horizontalIntegers.subList(0, j)) < horizontalIntegers.get(j);
      boolean visibleRight = Collections.max(horizontalIntegers.subList(j +1, horizontalIntegers.size())) < horizontalIntegers.get(j);

      boolean visibleTop = Collections.max(verticalIntegers.subList(0, i)) < verticalIntegers.get(i);
      boolean visibleBottom = Collections.max(verticalIntegers.subList(i +1, horizontalIntegers.size())) < verticalIntegers.get(i);
      if (visibleLeft || visibleRight || visibleTop || visibleBottom) {
        visibleTrees++;
      }
    }
    return visibleTrees;
  }


}
