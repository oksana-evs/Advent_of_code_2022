package days;

import utils.Utils;

import java.io.IOException;
import java.util.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Eleven {

  private static long reduce = 1L;

  public long getLevel() throws IOException {
    ArrayList<String> lines = Utils.getLines("11.12.txt");
    List<Monkey> monkeys = getMonkeys(lines);
    reduce = monkeys.stream()
            .map(monkey -> monkey.divisibleBy)
            .distinct()
            .reduce(1L, (a, b) -> a * b);
    for (long i = 0; i < 10_000; i++) {
      for (Monkey monkey : monkeys) {
        Iterator<Item> iterator = monkey.items.iterator();
        while (iterator.hasNext()) {
          Item item = iterator.next();
          monkey.count[0]++;
          getNewWorryLevel(item, monkey.operation);
//        item.remainder = ((int) Math.floor((double)item.remainder / 3));
          boolean isDivisible = isDivisible(item, monkey.divisibleBy);
          Monkey monkeyThrowTo = monkeys.get(monkey.instructions.get(isDivisible));
          throwToMonkey(monkeyThrowTo, item);
          iterator.remove();
        }
      }
    }
    monkeys.sort(Comparator.comparingLong(m -> m.count[0]));
    Collections.reverse(monkeys);
    System.out.println(monkeys);

    return monkeys.get(0).count[0] * monkeys.get(1).count[0];
  }

  private List<Monkey> getMonkeys(ArrayList<String> lines) {
    List<Monkey> monkeys = new ArrayList<>();
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      if (line.startsWith("Monkey")) {
        long id = Integer.parseInt(line.split(" ")[1].substring(0, line.split(" ")[1].length() - 1));
        List<Item> items = getItems(lines.get(i + 1).trim());
        Operation operation = getOperation(lines.get(i + 2).trim());
        long divisibleBy = getDivisibleBy(lines.get(i + 3).trim());
        Map<Boolean, Integer> instructions = getInstructions(lines.get(i + 4).trim(), lines.get(i + 5).trim());
        monkeys.add(new Monkey(id, new long[]{0}, items, operation, divisibleBy, instructions));
      }
    }
    return monkeys;
  }

  private record Operation(String value, Action action) {
  }

  private enum Action {
    PLUS,
    MULTIPLY
  }

  private static final class Item {
    private long value;

    private Item(long remainder) {
      this.value = remainder;
    }

  }

  //this should be a class, not a record
  private record Monkey(long id, long[] count, List<Item> items, Operation operation, long divisibleBy,
                        Map<Boolean, Integer> instructions) {
  }

  private static List<Item> getItems(String str) {
    List<Item> result = new ArrayList<>();
    String[] split = str.split(" ");
    for (int i = 2; i < split.length; i++) {
      long worryLevel = split[i].contains(",") ? Integer.parseInt(split[i].substring(0, split[i].length() - 1)) : Integer.parseInt(split[i]);
      result.add(new Item(worryLevel));
    }
    return result;
  }

  private static Operation getOperation(String str) {
    String[] split = str.split(" ");
    Action action = getByChar(split[4]);
    return new Operation(split[5], action);
  }

  private static long getDivisibleBy(String str) {
    return Integer.parseInt(str.split(" ")[3]);
  }

  private Map<Boolean, Integer> getInstructions(String ifTrue, String ifFalse) {
    Map<Boolean, Integer> result = new HashMap<>();
    result.put(TRUE, Integer.parseInt(ifTrue.split(" ")[5]));
    result.put(FALSE, Integer.parseInt(ifFalse.split(" ")[5]));
    return result;
  }

  private static Action getByChar(String character) {
    return switch (character) {
      case "+" -> Action.PLUS;
      case "*" -> Action.MULTIPLY;
      default -> throw new RuntimeException();
    };
  }

  private void getNewWorryLevel(Item item, Operation operation) {
    if (!operation.value.equals("old")) {
      long term = Long.parseLong(operation.value);
      switch (operation.action) {
        case PLUS -> item.value += term;
        case MULTIPLY -> item.value *= term;
      }
    } else {
      switch (operation.action) {
        case PLUS -> item.value *= 2;
        case MULTIPLY -> item.value = (item.value * item.value);
      }
    }
    if (item.value >= reduce) {
      item.value = item.value % reduce;
    }
  }

  private void throwToMonkey(Monkey monkey, Item item) {
    monkey.items.add(item);
  }


  private boolean isDivisible(Item item, long value) {
    return item.value % value == 0;

  }
}


