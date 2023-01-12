import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class CaesarCipher {

    static List<Character> simbolsText = getArrayList();

//    ДЕДЛАЙН ДЛЯ ЭТОГО ПРОЕКТА 21 ЯНВАРЯ (22 КРАЙ)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static void main(String[] args) {
//      C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt
//      Перенести мейн в отдельный класс, в логику ниже прописать в констркуторе.
        System.out.println(simbolsText.size());
        String uri;
        int key = 0;
        boolean isMinus;
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

            isMinus = shift();

            System.out.println(key);
            System.out.println(isMinus);

            encrypt(uri,key, isMinus);
        }
    }

    static public boolean shift() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Сдвиг в право\\лево (l\\r):");
            boolean result = false;
            String sdvig = "";
            while (!sdvig.equals("l") && !sdvig.equals("r")) {
                sdvig = in.nextLine();
                if (!sdvig.equals("l") && !sdvig.equals("r")) {
                    System.out.println("Неверный ввод");
                }else if (sdvig.equals("l")) {
                    result = true;
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    Надо найти вариант, как реализовать этот метод так, чтобы не повторялся код, иначе создавать отдельный метод с таким же
//    кодом было бы иррационально.

//    Вместо ByteBuffer writeBuffer попробовать использовать буферы, которые сразу хронят char
//    Попробовать использовать для чтения файла класс Files, так как там есть метод который читает весь файл и овзвращает его в виде строкиё

//    C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt
    static public void encrypt(String uri, int key, boolean rotation) {
        try (Reader file = new FileReader(uri)) {
            BufferedReader buffReader = new BufferedReader(file);

            List<Character> charList = new ArrayList<>();
            while (buffReader.ready()) {
                charList.add((char) buffReader.read());
            }
            System.out.println(charList);

            if (rotation){
                for (int i = 0; i < charList.size(); i++) {
                    int encryptedSimbolIndex = simbolsText.indexOf(charList.get(i)) - key;
                        while (encryptedSimbolIndex < 0) {
                            encryptedSimbolIndex = encryptedSimbolIndex + simbolsText.size();
                        }
                    charList.set(i, simbolsText.get(encryptedSimbolIndex));
                }
            } else {
                for (int i = 0; i < charList.size(); i++) {
                    int encryptedSimbolIndex = simbolsText.indexOf(charList.get(i)) + key;
                        while (encryptedSimbolIndex >= simbolsText.size()) {
                            encryptedSimbolIndex = encryptedSimbolIndex - simbolsText.size();
                        }
                    charList.set(i, simbolsText.get(encryptedSimbolIndex));
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

    public static String getNewFileName(String oldFileName) {
        int dotIndex = oldFileName.lastIndexOf(".");
        return oldFileName.substring(0, dotIndex) + "Encrypted" + oldFileName.substring(dotIndex);
    }

     public static List<Character> getArrayList() {
        String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-! ?";
        ArrayList<Character> chars = new ArrayList<>();
        for (char ch: alphabet.toCharArray()) {
            chars.add(ch);
        }
        return chars;
    }
}
