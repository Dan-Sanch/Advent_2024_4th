import utils.FileReaderTools;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
//        String fileName = "resources\\test1";
        String fileName = "resources\\Puzzle Input";
        String[] inputLines = FileReaderTools.readFileAsArray(fileName);

        int cardsTotalScore = getCardsTotalScore(inputLines);

        System.out.println("Cards total score: " + cardsTotalScore);
    }

    private static int getCardsTotalScore(String[] cardsInputLines) {
        int total = 0;
        for(String line : cardsInputLines) {
            total += getCardScore(line);
        }
        return total;
    }

    private static int getCardScore(String cardLine) {
        List<Integer> yourNumbers = parseYourNumbers(cardLine);
        TreeSet<Integer> winningNumbers = parseWinningNumbers(cardLine);
        Collection<Integer> yourWinningNumbers = getYourWinningNumbers(yourNumbers, winningNumbers);

        int winnerCount = yourWinningNumbers.size();
        return winnerCount==0? 0 : (int) Math.pow(2, (winnerCount-1));
    }

    private static List<Integer> parseYourNumbers(String cardLine) {
        return Arrays.stream(
                        cardLine
                                .substring(cardLine.indexOf(':') + 1, cardLine.indexOf('|')).trim()
                                .split("[\\D]+")
                ).map(Integer::valueOf)
                .toList();
    }

    private static TreeSet<Integer> parseWinningNumbers(String cardLine) {
        return Arrays.stream(
                        cardLine
                                .substring(cardLine.indexOf('|') + 1).trim()
                                .split("[\\D]+")
                ).map(Integer::valueOf)
                .collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
    }

    private static Collection<Integer> getYourWinningNumbers(List<Integer> yourNumbers, TreeSet<Integer> winningNumbers) {
        return yourNumbers.stream()
                .filter(winningNumbers::contains)
                .toList();
    }
}