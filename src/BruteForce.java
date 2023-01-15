import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BruteForce {

    public static void breaking(String uri) throws IOException {
        List<Character> list = FileWork.getCharList(uri);

        for (int k = 0; k < Main.alphabet.size(); k++) {
            List<Character> decryptedText = new ArrayList<>();
            for (Character character : list) {
                int index = Main.alphabet.indexOf(character);

                if ((index - k) >= 0) {
                    decryptedText.add(Main.alphabet.get(index - k));
                } else {
                    decryptedText.add(Main.alphabet.get((index - k) + Main.alphabet.size()));
                }
            }
            String option;
            StringBuilder builderResult = new StringBuilder();
            for (char ch: decryptedText) {
                builderResult.append(ch);
            }
            option = builderResult.toString();

            boolean isFirstUpper = Character.isUpperCase(option.charAt(0));
            boolean isLastLower = Character.isLowerCase(option.charAt(option.length()-1));
            boolean isLastADot = Character.toString(option.charAt(option.length()-1)).equals(".");
            if (option.contains(", ") && isFirstUpper && whatIsLastChar(isLastLower,isLastADot)) {
                System.out.println("Decrypted Text Using key" + k + ":" + option);
                FileWork.setFile(option,uri,'b');
            }
        }
    }

  static boolean whatIsLastChar(boolean lower, boolean dot){
        return lower || dot;
    }
}

//}
//    void bruteForce(String cipherText)
//    {
//        cipherText=cipherText.toUpperCase();
//
//        for(int k=0;k< 26;k++)
//        {
//            String decryptedText="";
//            int key=k;
//            for(int i=0;i< cipherText.length();i++)
//            {
//                int index=b.indexOfChar(cipherText.charAt(i));
//
//                if(index==-1)
//                {
//                    decryptedText+=cipherText.charAt(i);
//                    continue;
//                }
//                if((index-key)>=0)
//                {
//                    decryptedText+=b.charAtIndex(index-key);
//                }
//                else
//                {
//                    decryptedText+=b.charAtIndex((index-key)+26);
//                }
//            }
//
//            System.out.println("Decrypted Text Using key"+key+":"+decryptedText);
//        }
//    }
//}