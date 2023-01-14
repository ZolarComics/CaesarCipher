import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileWork {

    public static String getNewFileName(String oldFileName, boolean isDecoding) {
        int dotIndex = oldFileName.lastIndexOf(".");
        String keyName;
        if (isDecoding) {
            keyName = "Decoded";
        } else {
            keyName = "Coded";
        }
        String newFileNmae = oldFileName.substring(0, dotIndex) + keyName + oldFileName.substring(dotIndex);
        int count = 0;
        while (Files.exists(Path.of(newFileNmae))) {
            count++;
            newFileNmae = oldFileName.substring(0, dotIndex) + keyName + count + oldFileName.substring(dotIndex);
        }
        return newFileNmae;
    }

    public static List<Character> getCharList(String uri) {
        try (Reader file = new FileReader(uri)) {
            BufferedReader buffReader = new BufferedReader(file);

            List<Character> charList = new ArrayList<>();
            while (buffReader.ready()) {
                charList.add((char) buffReader.read());
            }
            System.out.println(charList);
            return charList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setEncryptFile(List<Character> fileText, String uri, boolean isDecoding) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (char aChar: fileText) {
            builder.append(aChar);
        }
        String encryptedText = builder.toString();
        System.out.println(encryptedText);

        ByteBuffer writeBuffer = ByteBuffer.allocate(encryptedText.getBytes().length);
        writeBuffer.put(encryptedText.getBytes());

        Path encryptedFile = Files.createFile(Path.of(getNewFileName(uri, isDecoding)));
        if(Files.notExists(encryptedFile)){
            Files.createFile(encryptedFile);
        }
        try (FileChannel partInputChannel = FileChannel.open(encryptedFile, StandardOpenOption.WRITE)) {
            writeBuffer.flip();
            partInputChannel.write(writeBuffer);
            writeBuffer.clear();
        }
        System.out.println("Файл зашифрован");
    }
}