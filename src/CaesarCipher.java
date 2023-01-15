import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class CaesarCipher {
    static int key;
    static boolean isLeft;
    static char methodSimbol;

//    ДЕДЛАЙН ДЛЯ ЭТОГО ПРОЕКТА 21 ЯНВАРЯ (22 КРАЙ)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

//      C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt

    static char getMethodSimbol(Scanner in) {
        System.out.println("Шифровка\\Разшифровка (c\\d):");
        char result = 'c';
        String methodToDo = "";
        while (!methodToDo.equals("c") && !methodToDo.equals("d")) {
            methodToDo = in.nextLine();
            if (!methodToDo.equals("c") && !methodToDo.equals("d")) {
                System.out.println(Main.MESSAGE);
            } else if (methodToDo.equals("d")) {
                result = 'd';
            }
        }
        return result;
    }

    static int getKey(Scanner in) {
        System.out.println("Сдвиг на (натуральное не отрицательное число):");
        int optionKey;
        while (true) {
            try {
                optionKey = Integer.parseInt(in.next());
                if (optionKey <= 0) {
                    System.out.println(Main.MESSAGE);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(Main.MESSAGE);
            }
        }
        return optionKey;
    }

    static boolean isLeftShift(Scanner in) {
        System.out.println("Сдвиг в право\\лево (l\\r):");
        boolean result = false;
        String shiftSimbol = in.nextLine();
        while (!shiftSimbol.equals("l") && !shiftSimbol.equals("r")) {
            shiftSimbol = in.nextLine();
            if (!shiftSimbol.equals("l") && !shiftSimbol.equals("r")) {
                System.out.println(Main.MESSAGE);
            } else if (shiftSimbol.equals("l")) {
                result = true;
            }
        }
        return result;
    }

    //    C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt
//    C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\textCoded.txt
    static public void converting(String uri) {
        try (Scanner in = new Scanner(System.in)) {
            methodSimbol =getMethodSimbol(in);
            key = getKey(in);
            isLeft = isLeftShift(in);

            Stream<Character> textFile = FileWork.getCharList(uri).stream();
            String encryptedText;

            if (methodSimbol == 'd') {
                if (!isLeft) {
                    key = Main.alphabet.size() - (key % Main.alphabet.size());
                }
            } else {
                if (isLeft) {
                    key = Main.alphabet.size() - (key % Main.alphabet.size());
                }
            }
            encryptedText = coding(textFile, key);

            FileWork.setFile(encryptedText, uri, methodSimbol);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String coding(Stream<Character> textStream, int key){
        return textStream.filter(x -> Main.alphabet.contains(x)).
                map(x -> Main.alphabet.get((Main.alphabet.indexOf(x) + key) % Main.alphabet.size()))
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }

}