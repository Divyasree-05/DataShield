public class HammingCode {
    public static String encode(String data) {
        int r = 0;
        while (Math.pow(2, r) < (data.length() + r + 1)) {
            r++;
        }

        int totalBits = data.length() + r;
        char[] encoded = new char[totalBits + 1];

        int j = 0;
        for (int i = 1; i <= totalBits; i++) {
            if (Math.ceil(Math.log(i) / Math.log(2)) == Math.floor(Math.log(i) / Math.log(2))) {
                encoded[i] = '0';
            } else {
                encoded[i] = data.charAt(j);
                j++;
            }
        }

        for (int i = 0; i < r; i++) {
            int parityPos = (int) Math.pow(2, i);
            int xorResult = 0;

            for (int k = parityPos; k <= totalBits; k++) {
                if (((k >> i) & 1) == 1) {
                    xorResult ^= (encoded[k] - '0');
                }
            }
            encoded[parityPos] = (char) (xorResult + '0');
        }

        return new String(encoded, 1, encoded.length - 1);
    }

    public static String decode(String encodedData) {
        int r = 0;
        while (Math.pow(2, r) < encodedData.length() + 1) {
            r++;
        }

        char[] received = encodedData.toCharArray();
        int errorPosition = 0;

        for (int i = 0; i < r; i++) {
            int parityPos = (int) Math.pow(2, i);
            int xorResult = 0;

            for (int k = parityPos; k <= received.length; k++) {
                if (((k >> i) & 1) == 1) {
                    xorResult ^= (received[k - 1] - '0');
                }
            }

            if (xorResult != 0) {
                errorPosition += parityPos;
            }
        }

        if (errorPosition != 0) {
            received[errorPosition - 1] = (received[errorPosition - 1] == '0') ? '1' : '0';
        }

        StringBuilder originalData = new StringBuilder();
        for (int i = 1; i <= received.length; i++) {
            if (Math.ceil(Math.log(i) / Math.log(2)) != Math.floor(Math.log(i) / Math.log(2))) {
                originalData.append(received[i - 1]);
            }
        }

        return originalData.toString();
    }
}

