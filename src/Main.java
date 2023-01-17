import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
// Зацыклить main код, чтобы можно было с одного запуска программы делать множество действий.

    public static final String MESSAGE = "Неверный ввод";
    static List<Character> alphabet = FileWork.getCharList("src/alphabet");
    static String uri;

    public static void main(String[] args) {
//        C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt
//        C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\textCoded.txt
        try (Scanner in = new Scanner(System.in)) {
                uri = getUri(in);
                System.out.println("Шифр цезаря\\Брутфорс (cc\\bf):");
                String toDo = "";
                while (!toDo.equals("cc") && !toDo.equals("bf")) {
                    toDo = in.nextLine();
                    if (!toDo.equals("cc") && !toDo.equals("bf")) {
                        System.out.println(MESSAGE);
                    } else if (toDo.equals("cc")) {
                        CaesarCipher.converting(uri);
                    } else {
                        BruteForce.breaking(uri, in);
                    }
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static String getUri(Scanner in) {
        System.out.println("Введите путь к файлу:");
        String uriOption;
        while (true) {
            uriOption = in.nextLine();
            File file = new File(uriOption);
            if (file.exists()) {
                break;
            } else {
                System.out.println("Такого файла не сущечвует");
            }
        }
        return uriOption;
    }

    public static int checkForInt(Scanner in) {
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

}