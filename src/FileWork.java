import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileWork {

    public static String getNewFileName(String oldFileName, char key) {
        int dotIndex = oldFileName.lastIndexOf(".");
        String keyName;
        if (key == 'd') {
            keyName = "Decoded";
        } else if (key == 'c') {
            keyName = "Coded";
        } else {
            keyName = "BruteForced";
        }
        String newFileName = oldFileName.substring(0, dotIndex) + keyName + oldFileName.substring(dotIndex);
        int count = 0;
        while (Files.exists(Path.of(newFileName))) {
            count++;
            newFileName = oldFileName.substring(0, dotIndex) + keyName + count + oldFileName.substring(dotIndex);
        }
        return newFileName;
    }

    public static List<Character> getCharList(String uri) {
        try (Reader file = new FileReader(uri)) {
            BufferedReader buffReader = new BufferedReader(file);

            List<Character> charList = new ArrayList<>();
            while (buffReader.ready()) {
                charList.add((char) buffReader.read());
            }
            return charList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setFile(String fileText, String uri, char fileKeyName) throws IOException {
        System.out.println(fileText);

        ByteBuffer writeBuffer = ByteBuffer.allocate(fileText.getBytes().length);
        writeBuffer.put(fileText.getBytes());

        String path = getNewFileName(uri, fileKeyName);
        Path encryptedFile = Files.createFile(Path.of(path));
        if(Files.notExists(encryptedFile)){
            Files.createFile(encryptedFile);
        }
        try (FileChannel partInputChannel = FileChannel.open(encryptedFile, StandardOpenOption.WRITE)) {
            writeBuffer.flip();
            partInputChannel.write(writeBuffer);
            writeBuffer.clear();
        }
        System.out.println("Результат: " + path);
    }
}