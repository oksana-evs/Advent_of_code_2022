package days;

import static days.Two.Option.A;
import static days.Two.Option.B;
import static days.Two.Option.C;
import static days.Two.Result.LOST;
import static days.Two.Result.DRAW;
import static days.Two.Result.WON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Two {

  public int getAssumedScore() throws IOException {
    ArrayList<String> lines = getLines();
    int score = 0;
    for (String line : lines) {
      String[] parts = line.split(" ");
      Option opponent = Option.valueOf(parts[0]);
      Option my = mapMyInput(parts[1]);
      score+=(getResult(opponent, my).value + my.value);
    }
    return score;
  }

  public int getRealScore() throws IOException {
    ArrayList<String> lines = getLines();
    int score = 0;
    for (String line : lines) {
      String[] parts = line.split(" ");
      Option opponent = Option.valueOf(parts[0]);
      Result result = mapResult(parts[1]);
      Option my = getMyInput(opponent, result);
      score+=(result.value + my.value);
    }
    return score;
  }

  private ArrayList<String> getLines() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("2.12.txt").getFile());
    BufferedReader br = new BufferedReader(new FileReader(file));
    String strLine;
    ArrayList<String> list = new ArrayList<>();
    while ((strLine = br.readLine()) != null)   {
      list.add(strLine);
    }
    return list;
  }


  private Result getResult(Option opponent, Option my) {
    if (opponent == my) {
      return DRAW;
    }
    return switch(my) {
      case A -> opponent == B ? LOST : WON;
      case B -> opponent == C ? LOST : WON;
      case C -> opponent == A ? LOST : WON;
    };
  }

  private Option getMyInput(Option opponent, Result result) {
    if (result == DRAW) {
      return opponent;
    }
    return switch(opponent) {
      case A -> result == WON ? B : C;
      case B -> result == WON ? C : A;
      case C -> result == WON ? A : B;
    };
  }

  private Option mapMyInput(String input) {
    return switch (input) {
      case "X" -> A;
      case "Y" -> B;
      case "Z" -> C;
      default -> throw new IllegalStateException("Unexpected value");
    };
  }

  private Result mapResult(String input) {
    return switch (input) {
      case "X" -> LOST;
      case "Y" -> DRAW;
      case "Z" -> WON;
      default -> throw new IllegalStateException("Unexpected value");
    };
  }

  public enum Option {
    A(1), // rock
    B(2), // paper
    C(3); // scissors

    public final int value;

    Option(int value) {
      this.value = value;
    }
  }

  public enum Result {
    WON(6),
    DRAW(3),
    LOST(0);

    public final int value;

    Result(int value) {
      this.value = value;
    }
  }
}
