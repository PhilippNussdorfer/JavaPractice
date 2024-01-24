package bbrz.at.SpringBootTestDatabase;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CipherTxt implements Cipher {

    private final StringBuilder strBuilder = new StringBuilder();
    private final List<Character> alphabetList = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

    @Override
    public String encode(String value) {
        strBuilder.setLength(0);
        int shift = 1;
        for (char strChar : value.toCharArray()) {
            for (int i = 0; i < alphabetList.size(); i++) {
                if (strChar == alphabetList.get(i)) {
                    if (i + shift < alphabetList.size()) {
                        strBuilder.append(alphabetList.get(i + shift));
                        break;
                    } else {
                        i = shift - 1;
                        strBuilder.append(alphabetList.get(i));
                        break;
                    }
                }
                if (strChar == ' ') {
                    strBuilder.append(' ');
                    break;
                }
            }
        }
        return strBuilder.toString();
    }

    @Override
    public String decode(String value) {
        strBuilder.setLength(0);
        int shift = 1;
        for (char strChar : value.toCharArray()) {
            for (int i = 0; i < alphabetList.size(); i++) {
                if (strChar == alphabetList.get(i)) {
                    if (i - shift >= 0) {
                        strBuilder.append(alphabetList.get(i - shift));
                        break;
                    } else  {
                        i = alphabetList.size() - (shift);
                        strBuilder.append(alphabetList.get(i));
                        break;
                    }
                }
                if (strChar == ' ') {
                    strBuilder.append(' ');
                    break;
                }
            }
        }
        return strBuilder.toString();
    }
}
