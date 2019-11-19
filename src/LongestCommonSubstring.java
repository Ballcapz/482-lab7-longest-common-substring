public class LongestCommonSubstring {

    public int[] bruteForceLCS(String s1, String s2) {
        int length1 = s1.length();
        int length2 = s2.length();
        int i, j, k;

        int[] returnObject = new int[3];

        for (i = 0; i < length1; i++) {
            for (j = 0; j < length2; j++) {
                for (k = 0; k < Math.min(length1 - i, length2 - j); k++) {
                    if (s1.charAt(i + k) != s2.charAt(j + k))
                        break;
                }
                if (k > returnObject[0]) {
                    returnObject[0] = k;
                    returnObject[1] = i;
                    returnObject[2] = j;
                }
            }
        }

        return returnObject;
    }

}
