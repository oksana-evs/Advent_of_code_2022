package days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import utils.Utils;

public class Twelve {
  public int getStepNumbers() throws IOException {
    ArrayList<String> lines = Utils.getLines("12.12.txt");
    char[][] grid = getGrid(lines);
    int[] init = getCoordinatesOf('S', grid);
    int[] target = getCoordinatesOf('E', grid);
    Solution solution = getSolution(grid, init, target);
    System.out.println(solution.isSuccessful);
    System.out.println(Arrays.deepToString(solution.getSolution()));

    return 0;
  }


  private static char[][] getGrid(ArrayList<String> lines) {
    char[][] grid = new char[lines.size()][lines.get(0).length()];
    for (int i = 0; i < lines.size(); i++) {
      char[] chars = lines.get(i).toCharArray();
      for (int j = 0; j < chars.length; j++) {
        grid[i][j] = chars[j];
      }
    }
    return grid;
  }

  private static boolean canGo(char from, char to) {
    if (from == 'S') {
      from = 'a';
    }
    if (to == 'S') {
      to = 'a';
    }
    if (from == 'E') {
      from = 'z';
    }
    if (to == 'E') {
      to = 'z';
    }
    int difference = Character.getNumericValue(to) - Character.getNumericValue(from);
    return difference == 0
            || difference == 1;
  }

  private static int[] getCoordinatesOf(char ch, char[][] chars) {
    for (int i = 0; i < chars.length; i++) {
      for (int j = 0; j < chars[i].length; j++) {
        if (chars[i][j] == ch)
          return new int[] {i, j};
      }
    }
    throw new RuntimeException();
  }

  private Solution getSolution(char[][] grid, int[] initPosition, int[] targetPosition) {
    char[][] solution = new char[grid.length][grid[0].length];
    int i = initPosition[0];
    int j = initPosition[1];
    while (i != targetPosition[0] || j != targetPosition[1]) {
      boolean canGoLeft =  j > 0 && canGo(grid[i][j], grid[i][j-1]) && (solution[i][j] == '\u0000' || solution[i][j] == '>');
      boolean canGoRight =  j < grid.length - 1 && canGo(grid[i][j], grid[i][j+1]) && (solution[i][j] == '\u0000' || solution[i][j] == '<');
      boolean canGoUp =  i > 0 && canGo(grid[i][j], grid[i-1][j]) && (solution[i][j] == '\u0000' || solution[i][j] == 'v');
      boolean canGoDown =  j < grid[0].length - 1 && canGo(grid[i][j], grid[i+1][j]) && (solution[i][j] == '\u0000' || solution[i][j] == '^');
      if (canGoRight) {
        solution[i][j] = '>';
        j = j + 1;}
      else if (canGoLeft) {
        solution[i][j] = '<';
        j = j - 1;
      } else if (canGoUp) {
        solution[i][j] = '^';
        i = i -1;
      } else if (canGoDown) {
        solution[i][j] = 'v';
        i = i+ 1;
      } else
        return new Solution(solution, false);
    }
    return new Solution(solution, true);
  }

  private final class Solution {
    private char[][] solution;
    private boolean isSuccessful;

    public Solution(char[][] solution, boolean isSuccessful) {
      this.solution = solution;
      this.isSuccessful = isSuccessful;
    }

    public char[][] getSolution() {
      return solution;
    }

    public void setSolution(char[][] solution) {
      this.solution = solution;
    }

    public boolean isSuccessful() {
      return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
      isSuccessful = successful;
    }
  }


}
