package days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import utils.Utils;

public class Five {
    public String getTopCratesOld() throws IOException {
      ArrayList<Instruction> instructions = getInstruction();
      ArrayList<ArrayList<Character>> crates = getCrates();
      for (Instruction instruction : instructions) {
        moveOld(instruction.quantity, crates.get(instruction.from - 1), crates.get(instruction.to - 1));
      }
      ArrayList<Character> result = new ArrayList<>();
      for (ArrayList<Character> crate : crates) {
        result.add(crate.get(crate.size() - 1));
      }

      return result.stream()
              .map(Object::toString)
              .collect(Collectors.joining());
    }

  public String getTopCratesNew() throws IOException {
    ArrayList<Instruction> instructions = getInstruction();
    ArrayList<ArrayList<Character>> crates = getCrates();
    for (Instruction instruction : instructions) {
      moveNew(instruction.quantity, crates.get(instruction.from - 1), crates.get(instruction.to - 1));
    }
    ArrayList<Character> result = new ArrayList<>();
    for (ArrayList<Character> crate : crates) {
      result.add(crate.get(crate.size() - 1));
    }

    return result.stream()
            .map(Object::toString)
            .collect(Collectors.joining());
  }

  private static ArrayList<ArrayList<Character>> getCrates() throws IOException {
    ArrayList<String> crateLines = Utils.getLines("5.12_crates.txt");
    String indexLine = crateLines.get(crateLines.size() - 1);
    ArrayList<ArrayList<Character>> crates = new ArrayList<>();
    for (int i = 1; i <= 9; i++) {
      crates.add(new ArrayList<>());
      for (int j = crateLines.size() - 2; j >= 0; j--) {
        int index = indexLine.indexOf(String.valueOf(i));
        if (crateLines.get(j).length() >= index && crateLines.get(j).charAt(index) != ' ') {
          crates.get(i - 1).add(crateLines.get(j).charAt(index));
        }
      }
    }
    return crates;
  }

  private static ArrayList<Instruction> getInstruction() throws IOException {
    ArrayList<String> lines = Utils.getLines("5.12_instructions.txt");
    ArrayList<Instruction> instructions = new ArrayList<>();
    for (String line : lines) {
      String[] parts = line.split(" ");
      instructions.add(new Instruction(
              Integer.parseInt(parts[1]),
              Integer.parseInt(parts[3]),
              Integer.parseInt(parts[5])
              ));
    }
    return instructions;
  }

  private static void moveOld(int quantity, ArrayList<Character> from, ArrayList<Character> to) {
    for (int i = 1; i <= quantity; i++) {
      Character elementToMove = from.remove(from.size() - 1);
      to.add(elementToMove);
    }
  }

  private static void moveNew(int quantity, ArrayList<Character> from, ArrayList<Character> to) {
    for (int i = quantity; i > 0; i--) {
      Character elementToMove = from.remove(from.size() - i);
      to.add(elementToMove);
    }
  }

  record Instruction(int quantity, int from, int to) {}
}
