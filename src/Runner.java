class Runner {

    public static void main(String[] args) {
        LongestCommonSubstring lcs = new LongestCommonSubstring();

        String test1 = "bbbbbbbb";
        String test2 = "zzzzzz";

        int[] result = new int[3];
        int[] result2 = new int[3];

        result = lcs.bruteForceLCS(test1, test2);

        result2 = lcs.fasterLCS(test1, test2);

        System.out.printf("Length: %d\n I: %d\n J: %d\n", result[0], result[1], result[2]);
        System.out.printf("Length: %d\n I: %d\n J: %d\n", result2[0], result2[1], result2[2]);
    }

    // correctness testing

}