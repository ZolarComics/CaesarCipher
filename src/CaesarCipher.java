import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CaesarCipher {

    static List<Character> fileText = getArrayList();

    public static void main(String[] args) {
//      C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt
//      Перенести мейн в отдельный класс, в логику ниже прописать в констркуторе.
        String uri;
        int key = 0;
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
                    System.out.println("Число должно быть положительным");
                }
            }

            key = shift(key);

            System.out.println(key);

            encrypt(uri,key);
        }
    }

//    Нужно сделать так, чтобы если символ введен неверно программа не продолжалась, а ожидалла корректного ввода.
    static public int shift(int key) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Сдвиг в право\\лево (L\\R):");
            String sdvig = in.nextLine();
            switch (sdvig) {
                case "L" -> key = key * (-1);
                case "R" -> {}
                default -> System.out.println("Не верный ввод");
            }
            return key;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    Можно попробовать упростить, реализовав метод с одним buffer'ом.
//    Надо найти вариант, как реализовать этот метод так, чтобы не повторялся код, иначе создавать отдельный метод с таким же
//    кодом было бы иррационально.
    static public void encrypt(String uri, int key) {
        try (FileChannel text = FileChannel.open(Paths.get(uri))) {
            ByteBuffer readBuffer = ByteBuffer.allocate((int) text.size());

            StringBuilder builder = new StringBuilder();

            text.read(readBuffer);
            readBuffer.flip();

            while (readBuffer.hasRemaining()) {
//               Тут нужно доделать проверку, чтобы если index > fileText.length начинался с начала
//                builder.append(fileText.get(fileText.indexOf((char) readBuffer.get())+ key));
                builder.append(readBuffer.getChar());
//                System.out.println();
//                System.out.println((char) readBuffer.get());
            }
            String encryptedText = builder.toString();
            System.out.println();

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
        String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ";
        ArrayList<Character> chars = new ArrayList<>();
        for (char ch: alphabet.toCharArray()) {
            chars.add(ch);
        }
        return chars;
    }
}
