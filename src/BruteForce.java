import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class BruteForce {

    public static void breaking(String uri) throws IOException {
            List<Character> chars = FileWork.getCharList(uri);

            for (int k = 0; k < Main.alphabet.size(); k++) {
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
                boolean isLastADot = Character.toString(bruteForcedString.charAt(bruteForcedString.length() - 1)).equals(".");
//                Дописать условие, чтобы была не только запята
                if (bruteForcedString.contains(", ") && isFirstUpper && whatIsLastChar(isLastLower, isLastADot)) {
                    FileWork.setFile(bruteForcedString, uri, 'b');
                    break;
                }
            }
            System.out.println("Программе не удолоась самостоятельо подобрать вариант разшифровки");
    }

    static char getChar(int index, int key) {
        if ((index - key) >= 0) {
            return Main.alphabet.get(index - key);
        } else {
            return Main.alphabet.get((index - key) + Main.alphabet.size());
        }
    }

    static boolean whatIsLastChar(boolean lower, boolean dot) {
        return lower || dot;
    }
}
