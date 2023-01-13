import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CaesarCipher {

    static List<Character> simbolsText = Main.getArrayList();
    static String uri;
    static int key;
    static boolean isLeft;
    static boolean isDecoding;

//    ДЕДЛАЙН ДЛЯ ЭТОГО ПРОЕКТА 21 ЯНВАРЯ (22 КРАЙ)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

//      C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt

//      System.out.println((int) '.'); // Способ, с помощью которого можно получить числовое значение символа.
//      System.out.println(1040 + 31); // Соответсвенно, знаюя что можно сделтаь так, нужно переписать весь код с учетом этих знаний.
//      System.out.println((int) 'Я'); // Хотя по предварительнымм выводам рзницы особой нет, что там есть ключи, что там индексы.

    public static void getCaesarCipherData() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Шифровка\\Разшифровка (c\\d):");
            isDecoding = false;
            String methodToDo = "";
            while (!methodToDo.equals("c") && !methodToDo.equals("d")) {
                methodToDo = in.nextLine();
                if (!methodToDo.equals("c") && !methodToDo.equals("d")) {
                    System.out.println("Неверный ввод");
                }else if (methodToDo.equals("d")) {
                    isDecoding = true;
                }
            }

            System.out.println("Введите путь к файлу:");
            while (true) {
                uri = in.nextLine();
                File file = new File(uri);
                if (file.exists()) {
                    break;
                } else {
                    System.out.println("Такого файла не сущечвует");
                }
            }

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
                }else if (shiftSimbol.equals("l")) {
                    isLeft = true;
                }
            }
        }
    }

//    Вместо ByteBuffer writeBuffer попробовать использовать буферы, которые сразу хронят char
//    Попробовать использовать для чтения файла класс Files, так как там есть метод который читает весь файл и овзвращает его в виде строкиё

//    C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt
//    C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\textCoded.txt
    static public void encrypt() {
        try {
            getCaesarCipherData();
            List<Character> fileText = FileWork.getCharList(uri);

            if (isDecoding) {
                if (isLeft) {
                    RightCoding(fileText);
                } else {
                    LeftCoding(fileText);
                }
            } else {
                if (isLeft){
                    LeftCoding(fileText);
                } else {
                    RightCoding(fileText);
                }
            }

            FileWork.setEncryptFile(fileText, uri, isDecoding);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LeftCoding(List<Character> inputText) {
        for (int i = 0; i < inputText.size(); i++) {
            if (!simbolsText.contains(inputText.get(i)))
                continue;
            int encryptedSimbolIndex = simbolsText.indexOf(inputText.get(i)) - key;
            while (encryptedSimbolIndex < 0) {
                encryptedSimbolIndex = encryptedSimbolIndex + simbolsText.size();
            }
            inputText.set(i, simbolsText.get(encryptedSimbolIndex));
        }
    }
    public static void RightCoding(List<Character> inputText) {
        for (int i = 0; i < inputText.size(); i++) {
            if (!simbolsText.contains(inputText.get(i)))
                continue;
            int encryptedSimbolIndex = simbolsText.indexOf(inputText.get(i)) + key;
            while (encryptedSimbolIndex >= simbolsText.size()) {
                encryptedSimbolIndex = encryptedSimbolIndex - simbolsText.size();
            }
            inputText.set(i, simbolsText.get(encryptedSimbolIndex));
        }
    }


}
