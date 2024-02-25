import java.util.Scanner;

public class Main {
    private final static int[] ARABIC = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    private final static String[] ROMAN = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] number = input.nextLine().split(" ");

        String num1;
        String num2;
        String operation;

        num1 = number[0];               //извлечение чисел из массива строк (сканера)
        num2 = number[2];
        operation = number[1];

        if (number.length > 3) {
            throw new IllegalStateException("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)\n");
        }

        if (Calculator.isArab(num1) ^ Calculator.isArab(num2)) {      //одно римское, одно арабское
            throw new IllegalStateException("Нельзя использовать римские и арабские числа одновременно");
        } else if (Calculator.isArab(num1) & Calculator.isArab(num2)) {     //оба арабских
            assert operation != null;
            int resArab = Calculator.showResultArab(num1, num2, operation);
            System.out.println(resArab);
        } else if (!Calculator.isArab(num1) & !Calculator.isArab(num2)) {   //оба римских
            assert operation != null;
            String resRome = Calculator.showResult(num1, num2, operation);
            System.out.println(resRome);
        }
    }

    static class Calculator {
        public static String showResult(String num1, String num2, String operation) throws NumberFormatException {    //вычисление для римских
            int result;
            int num1arab = 0;
            int num2arab = 0;

            if (!isArab(num1) & !isArab(num2)) {        //вычисление для римских
                num1arab = romeToArab(num1);          //конвертируем римские в арабские
                num2arab = romeToArab(num2);
            }

            if (operation.equals("-") & (num1arab <= num2arab)) {
                throw new IllegalStateException("throws Exception //т.к. в римской системе нет отрицательных чисел");

            }

            switch (operation) {
                case "+" -> result = num1arab + num2arab;
                case "-" -> result = num1arab - num2arab;
                case "*" -> result = num1arab * num2arab;
                case "/" -> result = num1arab / num2arab;
                default ->
                        throw new IllegalStateException("Неизвестный/Неправильный арефметический знак: " + operation);

            }
            return arabToRome(result);      //результат конвертируется в римские числа
        }

        public static int showResultArab(String num1, String num2, String operation) throws NumberFormatException {        //вычисление для арабских
            int result = 0;
            int number1 = Integer.parseInt(num1);
            int number2 = Integer.parseInt(num2);
            switch (operation) {
                case "+" -> result = number1 + number2;
                case "-" -> result = number1 - number2;
                case "*" -> result = number1 * number2;
                case "/" -> result = number1 / number2;
            }
            return result;
        }

        public static boolean isArab(String num1) throws NumberFormatException {                    //проверка на арабские числа
            try {
                Integer.parseInt(num1);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }

        public static int romeToArab(String num1) {                     //преобразование римских в арабские
            int result = 0;

            for (int i = ROMAN.length - 1; i >= 0; i--) {
                if (num1.startsWith(ROMAN[i])) {
                    result += ARABIC[i];
                    num1 = num1.substring(ROMAN[i].length() == 2 ? 2 : 1);  //отрезаем от начала строки один или два символа в зависимости от длины римского числа
                }
                if (num1.startsWith(ROMAN[i])) i++;
            }
            return result;
        }

        public static String arabToRome(int number) {                 //преобразование арабских в римские
            StringBuilder RomeNumber = new StringBuilder();

            for (int i = ARABIC.length - 1; i >= 0; i--) {
                while (number >= ARABIC[i]) {
                    RomeNumber.append(ROMAN[i]);
                    number -= ARABIC[i];
                }
            }
            return RomeNumber.toString();
        }
    }
}
