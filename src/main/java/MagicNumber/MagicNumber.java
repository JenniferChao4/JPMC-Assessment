package MagicNumber;

public class MagicNumber {

    public static String run(String input) {
        String result = "";

        Integer[] intArray = toIntegers(input);
        Integer A = intArray[0];
        Integer B = intArray[1];

        for (int i = A; i <= B; i++) {
            if (noRepeatDigits(i) && isMagic(i)) {
                result += i + "\n";
            }
        }

        return result.trim();
    }

    private static Integer[] toIntegers(String input) {
        String[] array = input.split(" ");
        Integer[] intArray = new Integer[]{Integer.valueOf(array[0]), Integer.valueOf(array[1])};
        return intArray;
    }

    private static Boolean noRepeatDigits(Integer input) {
        String stringA = input.toString();

        for (int j = 0; j < stringA.length() - 1; j++) {
            for (int k = j + 1; k < stringA.length(); k++) {
                if (stringA.charAt(j) == stringA.charAt(k)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static Integer[] splitToDigits(Integer input) {
        String stringA = input.toString();
        String[] stringDigits = stringA.split("");
        Integer[] digits = new Integer[stringDigits.length];

        for (int i = 0; i < stringDigits.length; i++) {
            digits[i] = Integer.valueOf(stringDigits[i]);
        }

        return digits;
    }

    private static Boolean isMagic(Integer input) {
        Integer[] digits = splitToDigits(input);
        boolean[] indexes = new boolean[digits.length];
        int currentIndex = 0;

        for (int j = 0; j < digits.length; j++) {
            currentIndex = currentIndex + digits[currentIndex];

            if (currentIndex >= digits.length) {
                currentIndex = currentIndex % digits.length;
            }

            if (!indexes[currentIndex]) {
                indexes[currentIndex] = true;
            } else {
                return false;
            }
        }

        return true;
    }

}
