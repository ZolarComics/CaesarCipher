import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class CaesarCipher {
    //"C:\\Users\\srgjz\\IdeaProjects\\CaesarCipher\\src\\alphabet"
    static int key;
    static boolean isLeft;
    static char methodSimbol;

//    ДЕДЛАЙН ДЛЯ ЭТОГО ПРОЕКТА 21 ЯНВАРЯ (22 КРАЙ)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

//      C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt

    public static void getCaesarCipherData() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Шифровка\\Разшифровка (c\\d):");
            methodSimbol = 'c';
            String methodToDo = "";
            while (!methodToDo.equals("c") && !methodToDo.equals("d")) {
                methodToDo = in.nextLine();
                if (!methodToDo.equals("c") && !methodToDo.equals("d")) {
                    System.out.println("Неверный ввод");
                } else if (methodToDo.equals("d")) {
                    methodSimbol = 'd';
                }
            }

//          Попробовать сделать проверку на не натуральное число
            System.out.println("Сдвиг на (натуральное не отрицательное число):");
            while (true) {
                key = in.nextInt();
                if (key <= 0) {
                    System.out.println("Число должно быть положительным и не должно равняться нулю");
                } else {
                    break;
                }
            }

            System.out.println("Сдвиг в право\\лево (l\\r):");
            isLeft = false;
            String shiftSimbol = in.nextLine();
            while (!shiftSimbol.equals("l") && !shiftSimbol.equals("r")) {
                shiftSimbol = in.nextLine();
                if (!shiftSimbol.equals("l") && !shiftSimbol.equals("r")) {
                    System.out.println("Неверный ввод");
                } else if (shiftSimbol.equals("l")) {
                    isLeft = true;
                }
            }
        }
    }

//    Вместо ByteBuffer writeBuffer попробовать использовать буферы, которые сразу хронят char
//    Попробовать использовать для чтения файла класс Files, так как там есть метод который читает весь файл и овзвращает его в виде строкиё

    //    C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt
//    C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\textCoded.txt
    static public void encrypt(String uri) {
        try {
            getCaesarCipherData();
            List<Character> textFile = FileWork.getCharList(uri);
            String encryptedText;

            if (methodSimbol == 'd') {
                if (isLeft) {
                    encryptedText = RightCoding(textFile);
                } else {
                    encryptedText = LeftCoding(textFile);
                }
            } else {
                if (isLeft) {
                    encryptedText = LeftCoding(textFile);
                } else {
                    encryptedText = RightCoding(textFile);
                }
            }

            FileWork.setFile(encryptedText, uri, methodSimbol);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String LeftCoding(List<Character> inputText) {
        Stream<Character> stream = inputText.stream();
        key = Main.alphabet.size() - (key % Main.alphabet.size());
        return stream.filter(x -> Main.alphabet.contains(x)).
                map(x -> Main.alphabet.get((Main.alphabet.indexOf(x) + key) % Main.alphabet.size()))
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }

    public static String RightCoding(List<Character> inputText) {
        Stream<Character> stream = inputText.stream();
        return stream.filter(x -> Main.alphabet.contains(x)).
                map(x -> Main.alphabet.get((Main.alphabet.indexOf(x) + key) % Main.alphabet.size()))
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }
}