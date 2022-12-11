package days;

import utils.Utils;

import java.io.IOException;
import java.util.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Eleven {

    public static final long BILLION = 1_00;

    public long getLevel() throws IOException {
        ArrayList<String> lines = Utils.getLines("11.12.txt");
        List<Monkey> monkeys = getMonkeys(lines);
        for (long i = 0; i < 20; i++) {
            for (Monkey monkey : monkeys) {
                Iterator<Item> iterator = monkey.items.iterator();
                while (iterator.hasNext()) {
                    Item item = iterator.next();
                    monkey.count[0]++;
                    getNewWorryLevel(item, monkey.operation);
                    item.remainder = ((int) Math.floor((double)item.remainder / 3));
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
        private long billionCount;
        private long remainder;
        private Item(long remainder) {
            this.remainder = remainder;
        }


        @Override
        public int hashCode() {
            return Objects.hash(billionCount * BILLION + remainder);
        }

        @Override
        public String toString() {
            return "Item{" +
                    "billionCounts=" + billionCount +
                    ", remainder=" + remainder +
                    '}';
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
                case PLUS -> item.remainder += term;
                case MULTIPLY -> {
                    item.remainder *= term;
                    item.billionCount *= term;
                }
            }
        } else {
            switch (operation.action) {
                case PLUS -> {
                    item.remainder *= 2;
                    item.billionCount *= 2;
                }
                case MULTIPLY -> {
                    item.remainder = (item.remainder * item.remainder) + (2 * item.remainder * item.billionCount * BILLION);
                    item.billionCount = item.billionCount * item.billionCount * BILLION;
                }
            }
        }
        if (item.remainder >= BILLION) {
            item.billionCount += item.remainder / BILLION;
            item.remainder = item.remainder % BILLION;
        }
    }

    private void throwToMonkey(Monkey monkey, Item item) {
        monkey.items.add(item);
    }
//check this
    private boolean isDivisible(Item item, long value) {
        if (item.billionCount == 0) {
            return item.remainder % value == 0;
        }
        if (item.remainder % value == 0 && (BILLION % value == 0 || item.billionCount % value == 0)) {
            return true;
        }
        return ((BILLION % value) * (item.billionCount % value) + (item.remainder % value)) % value == 0;

    }
}


