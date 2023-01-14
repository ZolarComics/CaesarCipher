import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\text.txt
//        C:\Users\srgjz\OneDrive\Рабочий стол\encryptingTest\textCoded.txt
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Шифр цезаря\\Брутфорс (cc\\bf):");
            String toDo = "";
            while (!toDo.equals("cc") && !toDo.equals("bf")) {
                toDo = in.nextLine();
                if (!toDo.equals("cc") && !toDo.equals("df")) {
                    System.out.println("Неверный ввод");
                }else if (toDo.equals("cc")) {
                    CaesarCipher.encrypt();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}