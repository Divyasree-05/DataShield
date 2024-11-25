public class CRC {

    public static String generateCRC(String data, String polynomial) {
        int polyLen = polynomial.length();

       
        String appendedData = data + "0".repeat(polyLen - 1);
        String remainder = divide(appendedData, polynomial);

        
        return data + remainder;
    }

    public static boolean checkCRC(String receivedData, String polynomial) {
        String remainder = divide(receivedData, polynomial);
        return remainder.equals("0".repeat(polynomial.length() - 1));
    }

    private static String divide(String data, String polynomial) {
        int polyLen = polynomial.length();
        String temp = data.substring(0, polyLen);

        for (int i = 0; i < data.length() - polyLen + 1; i++) {
            if (temp.charAt(0) == '1') {
                temp = xor(temp, polynomial);
            }
            if (i + polyLen < data.length()) {
                temp = temp.substring(1) + data.charAt(i + polyLen);
            } else {
                temp = temp.substring(1);
            }
        }
        return temp;
    }

    private static String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            result.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return result.toString();
    }
}
