import java.nio.charset.Charset;
import java.util.Random;

class Runner {

    public static void main(String[] args) {
        // LongestCommonSubstring lcs = new LongestCommonSubstring();

        // String test1 = "bbbbbbbb";
        // String test2 = "zzzzzz";

        // int[] result = new int[3];
        // int[] result2 = new int[3];

        // result = lcs.bruteForceLCS(test1, test2);

        // result2 = lcs.fasterLCS(test1, test2);

        // System.out.printf("Length: %d\n I: %d\n J: %d\n", result[0], result[1],
        // result[2]);
        // System.out.printf("Length: %d\n I: %d\n J: %d\n", result2[0], result2[1],
        // result2[2]);

        // System.out.println(generateRandomStringOfLength(10));

        testLCSFunctions();

    }

    // correctness testing
    public static String generateRandomStringOfLength(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

    public static void testLCSFunctions() {

        LongestCommonSubstring lcs = new LongestCommonSubstring();
        int[] slowResult = new int[3];
        int[] fastResult = new int[3];

        String[] s = new String[10];

        s[0] = "";
        s[1] = "aaaaaaaaaaaaaaa";
        s[2] = "bbb";
        s[3] = "bbbbbbaaaaaaabbbbbababab";
        s[4] = "HelloThereBud";
        s[5] = "OhHiThereChief";
        s[6] = "aaacosdiaaaiuhnvaaa";
        s[7] = "aaavoindaaagebfdaaa";
        s[8] = "aaaaaaaaaaaaaaaaaaaaahcuvhdf";
        s[9] = "aaaaaaaaaaaaaaaaaaaaabudledj";

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                slowResult = lcs.bruteForceLCS(s[i], s[j]);
                fastResult = lcs.fasterLCS(s[i], s[j]);

                if (slowResult[0] == fastResult[0]) {
                    System.out.printf("CORRECT\n");
                } else {
                    System.out.printf("FAIL!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }
        }

        Random random = new Random();
        String rand1 = generateRandomStringOfLength(500);
        String rand2 = generateRandomStringOfLength(500);

        int randomSubStrLen = random.nextInt(200 - 100 + 100) + 100;

        int subStrPos = random.nextInt((500 - randomSubStrLen) - 1 + 1) + 1;

        int secondRandPos = random.nextInt(500) + 1;

        String fromRand1 = rand1.substring(subStrPos, subStrPos + randomSubStrLen);

        String fromRand2 = rand2.substring(0, secondRandPos) + fromRand1 + rand2.substring(secondRandPos + 1);

        slowResult = lcs.bruteForceLCS(fromRand1, fromRand2);
        fastResult = lcs.fasterLCS(fromRand1, fromRand2);

        if (slowResult[0] == fastResult[0]) {
            System.out.printf("BIG CORRECT\n");
        } else {
            System.out.printf("HUGE FAIL!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }

    }
}