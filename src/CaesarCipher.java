import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CaesarCipher {

    static List<Character> fileText = getArrayList();

    public static void main(String[] args) {
//        System.out.println(-3 * (-1));"C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt"
        encrypt("C:\\Users\\srgjz\\OneDrive\\Рабочий стол\\encryptingTest\\text.txt",1);
    }
//    Можно попробовать упростить, реализовав метод с одним buffer'ом.
//    Нужно уточнить, можно ли вызывать метод get у buffer без flip.
//    Надо найти вариант, как реализовать этот метод так, чтобы не повторялся код, иначе создавать отдельный метод с таким же
//    кодом было бы иррационально.
    static public void encrypt(String url, int key) {
        try (FileChannel text = FileChannel.open(Paths.get(url))) {
            ByteBuffer readBuffer = ByteBuffer.allocate((int) text.size());

            StringBuilder builder = new StringBuilder();

            text.read(readBuffer);
            readBuffer.flip();

            while (readBuffer.hasRemaining()) {
//               Тут нужно доделать проверку, чтобы если index > fileText.length начинался с начала
//                 + key
//                builder.append(fileText.get(fileText.indexOf((char) readBuffer.get())+ key));
                builder.append(readBuffer.getChar());
//                System.out.println();
//                System.out.println((char) readBuffer.get());
            }
            String encryptedText = builder.toString();
            System.out.println();

            ByteBuffer writeBuffer = ByteBuffer.allocate(encryptedText.getBytes().length);
            writeBuffer.put(encryptedText.getBytes());

            Path encryptedFile = Files.createFile(Path.of(getNewFileName(url)));
            if(Files.notExists(encryptedFile)){
                Files.createFile(encryptedFile);
            }
            try (FileChannel partInputChannel = FileChannel.open(encryptedFile, StandardOpenOption.WRITE)) {
                writeBuffer.flip();
                partInputChannel.write(writeBuffer);
                writeBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getNewFileName(String oldFileName) {
        int dotIndex = oldFileName.lastIndexOf(".");
        return oldFileName.substring(0, dotIndex) + "encrypted" + oldFileName.substring(dotIndex);
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
