import utils.FileReaderTools;

public class Main {
    public static void main(String[] args) {
        String fileName = "resources\\test1";
//        String fileName = "resources\\Puzzle Input";
        String[] inputLines = FileReaderTools.readFileAsArray(fileName);

        System.out.println("Print result here");
    }
}