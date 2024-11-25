public class error_ctrl {
    private static final double LOSS_THRESHOLD = 0.1; // 10% loss rate threshold
    private static final int CHECKSUM_BLOCK_SIZE = 8; // Block size for checksum

    public static void main(String[] args) {
        String data = "1011001";
        String polynomial = "1101"; 
        double packetLossRate = getSimulatedPacketLossRate();

        // Adaptive decision
        if (packetLossRate < LOSS_THRESHOLD) {
            String transmittedData = CRC.generateCRC(data, polynomial);
            System.out.println("Using CRC: Transmitted Data: " + transmittedData);

            // Simulating reception
            if (CRC.checkCRC(transmittedData, polynomial)) {
                System.out.println("CRC Check Passed. Data is error-free.");
            } else {
                System.out.println("CRC Check Failed. Errors detected.");
            }
        } else if (packetLossRate >= LOSS_THRESHOLD && packetLossRate < 0.15) {
            // Use Hamming Code (error correction)
            String transmittedData = HammingCode.encode(data);
            System.out.println("Using Hamming Code: Transmitted Data: " + transmittedData);

            // Simulating reception and error correction
            String receivedData = HammingCode.decode(transmittedData);
            System.out.println("Decoded Data (with correction): " + receivedData);
        } else if (packetLossRate >= 0.15 && packetLossRate < 0.2) {
            // Use Checksum (simple error detection for higher error rates)
            String checksum = Checksum.generateChecksum(data, CHECKSUM_BLOCK_SIZE);
            System.out.println("Using Checksum: Transmitted Data: " + data + ", Checksum: " + checksum);

            // Simulating reception
            if (Checksum.checkChecksum(data, checksum, CHECKSUM_BLOCK_SIZE)) {
                System.out.println("Checksum Check Passed. Data is error-free.");
            } else {
                System.out.println("Checksum Check Failed. Errors detected.");
            }
        } else {
            // Use 2D Parity Check (for very noisy channels)
            String[][] dataMatrix = {
                {"1", "0", "1"},
                {"1", "0", "0"},
                {"0", "1", "1"}
            };
            String[][] parityMatrix = TwoDparity.generate2DParity(dataMatrix);
            System.out.println("Using 2D Parity Check: Transmitted Matrix with Parity:");

            for (String[] row : parityMatrix) {
                for (String bit : row) {
                    System.out.print(bit + " ");
                }
                System.out.println();
            }

            // Simulating reception and checking for parity errors
            if (TwoDparity.check2DParity(parityMatrix)) {
                System.out.println("2D Parity Check Passed. Data is error-free.");
            } else {
                System.out.println("2D Parity Check Failed. Errors detected.");
            }
        }
    }

    // Simulate packet loss rate (for testing purposes)
    private static double getSimulatedPacketLossRate() {
        // Simulate packet loss rate between 0% to 20%
        return Math.random() * 0.2;
    }
}

    

