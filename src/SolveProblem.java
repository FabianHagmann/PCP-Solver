import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SolveProblem {
    private static final List<String> invalidCombinations = new ArrayList<>();
    private static final List<String> validCombinations = new ArrayList<>();

    public static void main(String[] args) {
        List<Wortpaar> paare = parseWortpaare(args);
        printParsedWortpaare(paare);
        solvePCP(paare);
        printFails(args);
        printSolutions(paare);
    }

    private static List<Wortpaar> parseWortpaare(String[] args) {
        return Arrays.stream(args)
                .map(String::trim)
                .filter(s -> s.matches("\\(([A-Za-z0-9)]+,[A-Za-z0-9)]+)\\)"))
                .map(Wortpaar::parseWortpaar)
                .collect(Collectors.toList());
    }

    private static void printParsedWortpaare(List<Wortpaar> paare) {
        System.out.println("Provided Pairs:");
        paare.forEach(p -> System.out.println("\t-\t" + p));
    }

    private static void solvePCP(List<Wortpaar> paare) {
        solvePCP("", paare);
    }

    private static void solvePCP(String currentCombinationIndexes, List<Wortpaar> paare) {
        if (currentCombinationIndexes.length() > 20) {
            return;
        }
        for (int i = 0; i < paare.size(); i++) {
            String newUpper = translateCombinationIndexesToUpper(currentCombinationIndexes.concat(Integer.toString(i)), paare, false);
            String newLower = translateCombinationIndexesToLower(currentCombinationIndexes.concat(Integer.toString(i)), paare, false);

            if (isValid(newUpper, newLower)) {
                validCombinations.add(currentCombinationIndexes + i);
                return;
            } else if (isInvalid(newUpper, newLower)) {
                invalidCombinations.add(currentCombinationIndexes + i);
            } else {
                solvePCP(currentCombinationIndexes + i, paare);
            }
        }
    }

    private static String translateCombinationIndexesToUpper(String currentCombinationIndexes, List<Wortpaar> paare, boolean withSpacers) {
        StringBuilder upper = new StringBuilder();
        for (char c : currentCombinationIndexes.toCharArray()) {
            int i = c - 48;
            upper.append(paare.get(i).getUpper());
            if (withSpacers) {
                upper.append("|");
            }
        }
        return  upper.toString();
    }

    private static String translateCombinationIndexesToLower(String currentCombinationIndexes, List<Wortpaar> paare, boolean withSpacers) {
        StringBuilder lower = new StringBuilder();
        for (char c : currentCombinationIndexes.toCharArray()) {
            int i = c - 48;
            lower.append(paare.get(i).getLower());
            if (withSpacers) {
                lower.append("|");
            }
        }
        return  lower.toString();
    }

    private static void printFails(String[] args) {
        boolean failsEnabled = false;
        int length = 10;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-f") || args[i].equals("--fails")) {
                failsEnabled = true;
                try {
                    length = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException ignored) {}
                break;
            }
        }
        if (failsEnabled) {
            printShortestFails(length);
        }
    }

    private static void printShortestFails(int length) {
        Comparator<String> compByLength = (aName, bName) -> aName.length() - bName.length();
        System.out.println("Shortest Invalid Solutions:");
        invalidCombinations.stream()
                .sorted(compByLength)
                .limit(length)
                .sorted(String::compareTo)
                .forEach(s -> System.out.println("\t-\t" + incrementCombination(s)));
    }

    private static String incrementCombination(String s) {
        return s.chars()
                .map(c -> c + 1)
                .mapToObj(Character::toString)
                .reduce("", String::concat);
    }

    private static void printSolutions(List<Wortpaar> paare) {
        System.out.println("Found Valid Solutions:");
        for (String solution : validCombinations) {
            printSingleSolution(solution, paare);
        }
    }

    private static void printSingleSolution(String solution, List<Wortpaar> paare) {
        System.out.println("\t-\t" + incrementCombination(solution));
        System.out.println("\t\t" + translateCombinationIndexesToUpper(solution, paare, true));
        System.out.println("\t\t" + translateCombinationIndexesToLower(solution, paare, true));
    }

    private static boolean isInvalid(String upper, String lower) {
        int minLength = Math.min(upper.length(), lower.length());
        for (int i = 0; i < minLength; i++) {
            if (upper.charAt(i) != lower.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValid(String upper, String lower) {
        if (upper.length() != lower.length()) {
            return false;
        }
        for (int i = 0; i < upper.length(); i++) {
            if (upper.charAt(i) != lower.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
