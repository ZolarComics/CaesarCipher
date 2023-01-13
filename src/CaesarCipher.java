import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CaesarCipher {

    static List<Character> simbolsText = getArrayList();
    static String uri;
    static int key = 0;
    static boolean isLeft;
    static boolean isDecoding;

//    ДЕДЛАЙН ДЛЯ ЭТОГО ПРОЕКТА 21 ЯНВАРЯ (22 КРАЙ)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static void main(String[] args) {
//      C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt
//      Перенести мейн в отдельный класс, в логику ниже прописать в констркуторе.
//      Вынести все цыклы в отдельные методы, чтобы в конструкторе они только вызыввались
        System.out.println(simbolsText.size());
        try (Scanner in = new Scanner(System.in)) {

            while (true) {
                System.out.println("Введите путь к файлу:");
                uri = in.nextLine();
                File file = new File(uri);
                if (file.exists()) {
                    break;
                } else {
                    System.out.println("Такого файла не сущечвует");
                }
            }

            while (key <= 0) {
                System.out.println("Сдвиг на (натуральное не отрицательное число):");
                key = in.nextInt();
                if (key <= 0) {
                    System.out.println("Число должно быть положительным и не должно равняться нулю");
                }
            }
            isLeft = getShift();
            isDecoding = getMethod();

            System.out.println(key);
            System.out.println(isLeft);

            encrypt(uri,key, isLeft, isDecoding);
        }
    }

    static public boolean getMethod() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Шифровка\\Разшифровка (c\\d):");
            boolean result = false;
            String methodToDo = "";
            while (!methodToDo.equals("c") && !methodToDo.equals("d")) {
                methodToDo = in.nextLine();
                if (!methodToDo.equals("c") && !methodToDo.equals("d")) {
                    System.out.println("Неверный ввод");
                }else if (methodToDo.equals("d")) {
                    result = true;
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static public boolean getShift() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Сдвиг в право\\лево (l\\r):");
            boolean result = false;
            String shiftSimbol = "";
            while (!shiftSimbol.equals("l") && !shiftSimbol.equals("r")) {
                shiftSimbol = in.nextLine();
                if (!shiftSimbol.equals("l") && !shiftSimbol.equals("r")) {
                    System.out.println("Неверный ввод");
                }else if (shiftSimbol.equals("l")) {
                    result = true;
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    Вынести код с нправлениями сдвига в отдельные методы и сделать проверку на шифровку и на дешифровку

//    Вместо ByteBuffer writeBuffer попробовать использовать буферы, которые сразу хронят char
//    Попробовать использовать для чтения файла класс Files, так как там есть метод который читает весь файл и овзвращает его в виде строкиё

//    C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt
    static public void encrypt(String uri, int key, boolean rotation, boolean isDecoding) {
        try (Reader file = new FileReader(uri)) {
            BufferedReader buffReader = new BufferedReader(file);

            List<Character> charList = new ArrayList<>();
            while (buffReader.ready()) {
                charList.add((char) buffReader.read());
            }
            System.out.println(charList);

            if (isDecoding) {
                if (rotation) {
                    RightCoding(charList);
                } else {
                    LeftCoding(charList);
                }
            } else {
                if (rotation){
                    LeftCoding(charList);
                } else {
                    RightCoding(charList);
                }
            }

            StringBuilder builder = new StringBuilder();
            for (char aChar: charList) {
                builder.append(aChar);
            }
            String encryptedText = builder.toString();
            System.out.println(encryptedText);

            ByteBuffer writeBuffer = ByteBuffer.allocate(encryptedText.getBytes().length);
            writeBuffer.put(encryptedText.getBytes());

            Path encryptedFile = Files.createFile(Path.of(getNewFileName(uri)));
            if(Files.notExists(encryptedFile)){
                Files.createFile(encryptedFile);
            }
            try (FileChannel partInputChannel = FileChannel.open(encryptedFile, StandardOpenOption.WRITE)) {
                writeBuffer.flip();
                partInputChannel.write(writeBuffer);
                writeBuffer.clear();
            }
            System.out.println("Файл зашифрован");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LeftCoding(List<Character> inputText) {
        for (int i = 0; i < inputText.size(); i++) {
            int encryptedSimbolIndex = simbolsText.indexOf(inputText.get(i)) - key;
            while (encryptedSimbolIndex < 0) {
                encryptedSimbolIndex = encryptedSimbolIndex + simbolsText.size();
            }
            inputText.set(i, simbolsText.get(encryptedSimbolIndex));
        }
    }
    public static void RightCoding(List<Character> inputText) {
        for (int i = 0; i < inputText.size(); i++) {
            int encryptedSimbolIndex = simbolsText.indexOf(inputText.get(i)) + key;
            while (encryptedSimbolIndex >= simbolsText.size()) {
                encryptedSimbolIndex = encryptedSimbolIndex - simbolsText.size();
            }
            inputText.set(i, simbolsText.get(encryptedSimbolIndex));
        }
    }

//    Изменить метод так, чтобы в зависимости от действия создавалось определенное имя (coded\decoded)
    public static String getNewFileName(String oldFileName) {
        int dotIndex = oldFileName.lastIndexOf(".");
        String newFileNmae = oldFileName.substring(0, dotIndex) + "Coded" + oldFileName.substring(dotIndex);
        int count = 0;
        while (Files.exists(Path.of(newFileNmae))) {
            count++;
            newFileNmae = oldFileName.substring(0, dotIndex) + "Coded" + count + oldFileName.substring(dotIndex);
        }
        return newFileNmae;
    }

     public static List<Character> getArrayList() {
        String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ.,\":-! ?";
        ArrayList<Character> chars = new ArrayList<>();
        for (char ch: alphabet.toCharArray()) {
            chars.add(ch);
        }
        return chars;
    }
}
