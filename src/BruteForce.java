import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class BruteForce {

    public static void breaking(String uri, Scanner in) throws IOException {
        List<Character> chars = FileWork.getCharList(uri);
        List<String> listOfResults = new ArrayList<>();
        boolean notFind = true;
        for (int k = 1; k < Main.alphabet.size(); k++) {
            String bruteForcedString;
            int key = k;

            Stream<Character> codedStream = chars.stream();
            bruteForcedString = codedStream.filter(x -> Main.alphabet.contains(x))
                    .map(x -> getChar(Main.alphabet.indexOf(x), key))
                    .collect(Collector.of(
                            StringBuilder::new,
                            StringBuilder::append,
                            StringBuilder::append,
                            StringBuilder::toString));

            System.out.println("Вариант взлома номер " + k + ": " + bruteForcedString);

            boolean isFirstUpper = Character.isUpperCase(bruteForcedString.charAt(0));
            boolean isLastLower = Character.isLowerCase(bruteForcedString.charAt(bruteForcedString.length() - 1));
            String lastChar = Character.toString(bruteForcedString.charAt(bruteForcedString.length() - 1));

            if (isFirstUpper && whatIsLastChar(isLastLower, endOfSentence(lastChar)) && bruteForcedString.contains(", "))  {
                FileWork.setFile(bruteForcedString, uri, 'b');
                notFind = false;
                break;
            }
            listOfResults.add(bruteForcedString);
        }
        if (notFind) {
            System.out.println("Программе не удолоась самостоятельо подобрать вариант разшифровки");
            System.out.println("Если среди вариантов выше есть верный вариант разшифровки введите его номер:");

            while (true){
                int index = Main.checkForInt(in)-1;
                if (index <= listOfResults.size()){
                    FileWork.setFile(listOfResults.get(index), uri, 'b');
                    break;
                } else
                    System.out.println(Main.MESSAGE);
            }
        }
    }

    static char getChar(int index, int key) {
        if ((index - key) >= 0) {
            return Main.alphabet.get(index - key);
        } else {
            return Main.alphabet.get((index - key) + Main.alphabet.size());
        }
    }

    static boolean endOfSentence(String str) {
        return str.equals(".") || str.equals("!") || str.equals("?");
    }

    static boolean whatIsLastChar(boolean lower, boolean end) {
        return lower || end;
    }
}
