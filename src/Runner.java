class Runner {

    public static void main(String[] args) {
        LongestCommonSubstring lcs = new LongestCommonSubstring();

        String test1 = "Hello";
        String test2 = "bell";

        int[] result = new int[3];

        result = lcs.bruteForceLCS(test1, test2);

        System.out.printf("Length: %d\n I: %d\n J: %d\n", result[0], result[1], result[2]);
    }

}