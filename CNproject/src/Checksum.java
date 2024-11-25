public class Checksum {
    public static String generateChecksum(String data, int blockSize) {
        int n = data.length();
        if (n % blockSize != 0) {
            int padLength = blockSize - (n % blockSize);
            data = "0".repeat(padLength) + data; // Padding with leading zeros
        }

        int sum = 0;
        for (int i = 0; i < data.length(); i += blockSize) {
            String block = data.substring(i, i + blockSize);
            sum += Integer.parseInt(block, 2);
        }

        // Handling overflow by adding the carry to the sum
        while ((sum >> blockSize) > 0) {
            sum = (sum & ((1 << blockSize) - 1)) + (sum >> blockSize);
        }

        String checksum = Integer.toBinaryString(~sum & ((1 << blockSize) - 1)); // Taking one's complement
        return checksum;
    }

    public static boolean checkChecksum(String data, String receivedChecksum, int blockSize) {
        String calculatedChecksum = generateChecksum(data, blockSize);
        return calculatedChecksum.equals(receivedChecksum);
    }
}
