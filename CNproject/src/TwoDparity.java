public class TwoDparity {
    public static String[][] generate2DParity(String[][] data) {
        int rows = data.length;
        int cols = data[0].length;

        String[][] parityArray = new String[rows + 1][cols + 1];

        // Copy data into parityArray
        for (int i = 0; i < rows; i++) {
            System.arraycopy(data[i], 0, parityArray[i], 0, cols);
        }

        // Calculate row parity
        for (int i = 0; i < rows; i++) {
            int rowParity = 0;
            for (int j = 0; j < cols; j++) {
                rowParity ^= Integer.parseInt(data[i][j]);
            }
            parityArray[i][cols] = String.valueOf(rowParity);
        }

        // Calculate column parity
        for (int j = 0; j < cols; j++) {
            int colParity = 0;
            for (int i = 0; i < rows; i++) {
                colParity ^= Integer.parseInt(data[i][j]);
            }
            parityArray[rows][j] = String.valueOf(colParity);
        }

        // Calculate overall parity
        int overallParity = 0;
        for (int i = 0; i < cols; i++) {
            overallParity ^= Integer.parseInt(parityArray[rows][i]);
        }
        parityArray[rows][cols] = String.valueOf(overallParity);

        return parityArray;
    }

    public static boolean check2DParity(String[][] receivedData) {
        String[][] generatedParity = generate2DParity(receivedData);
        int rows = receivedData.length;
        int cols = receivedData[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!receivedData[i][j].equals(generatedParity[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
}

