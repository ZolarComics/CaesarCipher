import java.io.*;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class CaesarCipher {
    static int key;
    static boolean isLeft;
    static char methodSimbol;

//    Получение информации, какой метод хочет использовать пользователь (шифровка\дешифровка)
    static char getMethodSimbol(Scanner in) {
        System.out.println("Шифровка\\Разшифровка (c\\d):");
        char result = 'c';
        String methodToDo = "";
        while (!methodToDo.equals("c") && !methodToDo.equals("d")) {
            methodToDo = in.nextLine();
            if (!methodToDo.equals("c") && !methodToDo.equals("d")) {
                System.out.println(Main.MESSAGE);
            } else if (methodToDo.equals("d")) {
                result = 'd';
            }
        }
        return result;
    }

//    Получаем значение для сдвига
    static int getKey(Scanner in) {
        System.out.println("Сдвиг на (натуральное не отрицательное число):");
        return Main.checkForInt(in);
    }

//    Выбор стороны для сдвига
    static boolean isLeftShift(Scanner in) {
        System.out.println("Сдвиг в право\\лево (l\\r):");
        boolean result = false;
        String shiftSimbol = in.nextLine();
        while (!shiftSimbol.equals("l") && !shiftSimbol.equals("r")) {
            shiftSimbol = in.nextLine();
            if (!shiftSimbol.equals("l") && !shiftSimbol.equals("r")) {
                System.out.println(Main.MESSAGE);
            } else if (shiftSimbol.equals("l")) {
                result = true;
            }
        }
        return result;
    }

    static public void converting(String uri) {
        try (Scanner in = new Scanner(System.in)) {
//            Получаем все неоходимые значения
            methodSimbol =getMethodSimbol(in);
            key = getKey(in);
            isLeft = isLeftShift(in);

//           Получаем список символов из файла
            Stream<Character> textFile = FileWork.getCharList(uri).stream();
            String encryptedText;

//            Проверка на шифровку\дешифровку
            if (methodSimbol == 'd') {
                if (!isLeft) {
//                    В данной строке, если сдвиг происходит в левую сторону, то значение ключа меняется так,
//                    чтобы сдвиг происходил корректно.
//                    Если этого не сделать, то ключ будет выходить за пределы массива.
//                    Иначе ключ передается без изменений.
                    key = Main.alphabet.size() - (key % Main.alphabet.size());
                }
            } else {
                if (isLeft) {
                    key = Main.alphabet.size() - (key % Main.alphabet.size());
                }
            }
            encryptedText = coding(textFile, key);

//            Запись результата в файл
            FileWork.setFile(encryptedText, uri, methodSimbol);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    В данном методе происходит сдвиг, в зависисоти от переданного ключа
    static String coding(Stream<Character> textStream, int key){
        return textStream.filter(x -> Main.alphabet.contains(x)).
                map(x -> Main.alphabet.get((Main.alphabet.indexOf(x) + key) % Main.alphabet.size()))
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }

}