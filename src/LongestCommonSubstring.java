public class LongestCommonSubstring {

    public int[] bruteForceLCS(String s1, String s2) {
        int length1 = s1.length();
        int length2 = s2.length();
        int i, j, k;

        int[] returnObject = new int[3];

        // check through every character of string 1 and string 2
        for (i = 0; i < length1; i++) {
            for (j = 0; j < length2; j++) {
                for (k = 0; k < Math.min(length1 - i, length2 - j); k++) {
                    // compare the characters at each position and continue
                    // to compare them until they are different
                    if (s1.charAt(i + k) != s2.charAt(j + k))
                        break;
                }
                // if the new lcs length is longer than the previous one
                // update the overall longest common substring and start positions
                // in each of the two strings
                if (k > returnObject[0]) {
                    returnObject[0] = k;
                    returnObject[1] = i;
                    returnObject[2] = j;
                }
            }
        }

        return returnObject;
    }

    public int[] fasterLCS(String s1, String s2) {
        int length1 = s1.length();
        int length2 = s2.length();
        int i, j;

        // table to store the longest lengths to be added on to if necessary
        int[][] memo = new int[length1][length2];

        int[] returnObject = new int[3];

        // check through every character of string 1 and string 2
        for (i = 0; i < length1; i++) {
            for (j = 0; j < length2; j++) {
                // compare the characters of each string
                if (s1.charAt(i) == s2.charAt(j)) {
                    // if there is nothing in the table, and characters are the same
                    // add a value to the table
                    if (i == 0 || j == 0) {
                        memo[i][j] = 1;
                    } else {
                        // otherwise update the table with the length and
                        // update the starting postions in each string
                        memo[i][j] = memo[i - 1][j - 1] + 1;
                        returnObject[1] = i;
                        returnObject[2] = j;
                    }
                    // if the new longest lcs length is longer
                    // update the actual longest lcs length
                    if (returnObject[0] < memo[i][j]) {
                        returnObject[0] = memo[i][j];

                    }
                }
            }
        }

        // if there are no common substrings, return
        if (returnObject[0] == 0) {
            return returnObject;
        }

        // fix up the positions to be the proper length, because of
        // the way we are storing the starting postions.
        returnObject[1] = returnObject[1] - returnObject[0] + 1;
        returnObject[2] = returnObject[2] - returnObject[0] + 1;
        return returnObject;

    }

}
