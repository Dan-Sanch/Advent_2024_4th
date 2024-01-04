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

        long totalCardsCount = getTotalCardsCount(inputLines);
        System.out.println("Total scratchcards won: " + totalCardsCount);
    }

    private static int getCardsTotalScore(String[] cardsInputLines) {
        int total = 0;
        for(String line : cardsInputLines) {
            total += getCardScore(line);
        }
        return total;
    }

    private static int getCardScore(String cardLine) {
        int winnerCount = getCardWinningCount(cardLine);
        return winnerCount==0? 0 : (int) Math.pow(2, (winnerCount-1));
    }

    private static int getCardWinningCount(String cardLine) {
        List<Integer> yourNumbers = parseYourNumbers(cardLine);
        TreeSet<Integer> winningNumbers = parseWinningNumbers(cardLine);
        return getYourWinningNumbers(yourNumbers, winningNumbers).size();
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

    private static long getTotalCardsCount(String[] cardsInputLines) {
        int[] winnersByCard = new int[cardsInputLines.length];
        for (int i=0; i < cardsInputLines.length; i++) {
            winnersByCard[i] = getCardWinningCount(cardsInputLines[i]);
        }

        int[] cardCopies = calcCardCopies(winnersByCard);
        return Arrays.stream(cardCopies).mapToLong(Long::valueOf).sum();
    }

    private static int[] calcCardCopies(int[] winnersByCard) {
        int[] cardCopies = new int[winnersByCard.length];
        Arrays.fill(cardCopies, 1);

        for (int i=0; i<cardCopies.length; i++) {
            for (int j=i+1; j <= i+winnersByCard[i] && j<cardCopies.length; j++)
                cardCopies[j] += cardCopies[i];
        }

        return cardCopies;
    }
}