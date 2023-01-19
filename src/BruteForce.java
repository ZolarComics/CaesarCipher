import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class BruteForce {

    public static void breaking(String uri, Scanner in) throws IOException {
//        Получаем список символов из файла.
        List<Character> chars = FileWork.getCharList(uri);
//        Список для записи всех результатов сдвига.
        List<String> listOfResults = new ArrayList<>();
        boolean notFind = true;
//        Перебираем все возможные сдвиги с помошью размера alphabet, за исключением 0,
//        так как сдвиг на ноль даст тот же текст, что и вводился.
        for (int k = 1; k < Main.alphabet.size(); k++) {
            String bruteForcedString;
//            Данный финт ушами необходим, так как, если в поток передать просто k, то будет ошибка.
            int key = k;

            Stream<Character> codedStream = chars.stream();
            bruteForcedString = codedStream.filter(x -> Main.alphabet.contains(x))
                    .map(x -> getChar(Main.alphabet.indexOf(x), key))
                    .collect(Collector.of(
                            StringBuilder::new,
                            StringBuilder::append,
                            StringBuilder::append,
                            StringBuilder::toString));

            System.out.println("Вариант взлома номер " + k + ": " + bruteForcedString);

//            Проверка строки на правильную расшифровку
            boolean isFirstUpper = Character.isUpperCase(bruteForcedString.charAt(0));
            boolean isLastLower = Character.isLowerCase(bruteForcedString.charAt(bruteForcedString.length() - 1));
            String lastChar = Character.toString(bruteForcedString.charAt(bruteForcedString.length() - 1));

//            Если текущий результат удовлетворяет условиям, то проверки прекращаются и расшифрованный текст записывается в новый файл.
//            В задании было сказано, что при выборе программой правильной расшифровки, стоит обратить внимание на пробелы или на знаки пунктуации.
//            В данном случае, я решил обращать внимание на то, что как правило предложение начинается с заглавной буквы,
//            заканчивается либо прописной буквой, либо знаком окончания предложения (. ! ?).
//            Так же, всегда после запятой идет пробел.
//            Такой метод может не выявить нужного варианта, потому что, как показал мой тестеровщик),
//            при вводе теста "давай кушать лапшу", он не удовлетворяет ниодному из условий.
//            Но при создании ещё большего количества проверок, шанс подбора неверного сдвига для дешифровки, увеличивается.
//            Чтобы выявить верный формат расшифровки, можно добавить какой-либо словарь, для проверки тех слов, которые есть в результате.
//            Поэтому, если программа не смогла выявить нужный ключ для дешифровки, предлагается выбрать правильный вариант самостоятельно.
            if (isFirstUpper && whatIsLastChar(isLastLower, endOfSentence(lastChar)) && bruteForcedString.contains(", "))  {
                FileWork.setFile(bruteForcedString, uri, 'b');
                notFind = false;
                break;
            }
            listOfResults.add(bruteForcedString);
        }
//        В том случае, если программе не удалось выявить правильную расшифровку с помощью условий выше,
//        то по выведеным результатам, пользователю предлогается выбрать правильно расшифрованный результат.
        if (notFind) {
            System.out.println("Программе не удолоась самостоятельо подобрать вариант разшифровки");
            System.out.println("Если среди вариантов выше есть верный вариант разшифровки введите его номер:");

//            После ввода индекса нужной строки, она записывается в новый файл.
            while (true){
                int index = Main.checkForInt(in)-1;
                if (index <= listOfResults.size()){
                    FileWork.setFile(listOfResults.get(index), uri, 'b');
                    break;
                } else
                    System.out.println(Main.MESSAGE);
            }
        }
    }

//    Метод, который возвращает символ по индексу с учетом сдвига на key (key - значение сдвига по alphabet с помощью цикла for)
    static char getChar(int index, int key) {
//        Если index, с учетом сдвига больше или равно нуля, то возвращает значение по index - key
        if ((index - key) >= 0) {
            return Main.alphabet.get(index - key);
        } else {
//            Иначе к результату index - key прибовляяется размер alphabet
            return Main.alphabet.get((index - key) + Main.alphabet.size());
        }
    }

//    Если строка заканчивается на точку или восклицательный знак, или вопросительный знак, то метод возвращает true
    static boolean endOfSentence(String str) {
        return str.equals(".") || str.equals("!") || str.equals("?");
    }

//    Если строка заканчиваеся на символ в нижнем регистре или одним из символов из метода endOfSentence, то метод возвращает true
    static boolean whatIsLastChar(boolean lower, boolean end) {
        return lower || end;
    }
}
