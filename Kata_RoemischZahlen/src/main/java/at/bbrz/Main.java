package at.bbrz;

public class Main {
    public static void main(String[] args) {
        var myRomanNumber = "MMMCMXCIX";
        var myArabianNumber = 1571;
        var calculator = new SwitchBetweenArabianAndRomanNumberSystem();

        System.out.println(calculator.getRomanNumber(myArabianNumber));
        System.out.println(calculator.getArabianNumber(myRomanNumber));
    }
}