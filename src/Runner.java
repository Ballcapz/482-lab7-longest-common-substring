import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodType;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Stream;

class Runner {

    static ThreadMXBean bean = ManagementFactory.getThreadMXBean();

    /* define constants */
    static long MAXVALUE = 2000000000;
    static long MINVALUE = -2000000000;
    private static int numberOfTrials = 50;
    private static int MAXINPUTSIZE = (int) Math.pow(2, 14);
    private static int MININPUTSIZE = 1;
    private static int SIZEINCREMENT = 2;

    static Random rand = new Random();

    // static int SIZEINCREMENT = 10000000; // not using this since we are doubling
    // the size each time
    private static String ResultsFolderPath = "C:\\Users\\Zach\\Documents\\School\\Fall 2019\\482 Algorithms\\lab7_logs\\"; // pathname
                                                                                                                            // to
                                                                                                                            // results
                                                                                                                            // folder
    private static FileWriter resultsFile;
    private static PrintWriter resultsWriter;

    public static void main(String[] args) {
        testLCSFunctions();

        String book1 = getFileTextAsString(
                "C:\\Users\\Zach\\Documents\\School\\Fall 2019\\482 Algorithms\\jekyllAndHydeBook.txt");

        String book2 = getFileTextAsString(
                "C:\\Users\\Zach\\Documents\\School\\Fall 2019\\482 Algorithms\\aModestProposalBook.txt");

        runFullExperiment("bruteForce-singleBook-1", book1, book2);
        runFullExperiment("bruteForce-singleBook-2", book1, book2);

    }

    // random string generator for testing and performance testing
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

    // convert a passed in text file to a string to test against
    public static String getFileTextAsString(String filePath) {
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    private static void runFullExperiment(String resultsFileName, String book1, String book2) {
        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);

        } catch (Exception e) {

            System.out.println(
                    "*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return; // not very foolproof... but we do expect to be able to create/open the file...

        }

        // set up the class to be used
        LongestCommonSubstring lcs = new LongestCommonSubstring();

        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial

        resultsWriter.println("#InputSize    AverageTime        DoublingRatio"); // # marks a comment in gnuplot data

        resultsWriter.flush();

        /*
         * for each size of input we want to test: in this case starting small and
         * doubling the size each time
         */
        // double[] timeRatios;
        double previousTime = 0;

        for (int inputSize = MININPUTSIZE; inputSize <= MAXINPUTSIZE; inputSize *= SIZEINCREMENT) {

            // progress message...

            System.out.println("Running test for input size " + inputSize + " ... ");

            /* repeat for desired number of trials (for a specific size of input)... */

            long batchElapsedTime = 0;

            /*
             * force garbage collection before each batch of trials run so it is not
             * included in the time
             */

            System.out.println("Collecting the trash...");
            System.gc();

            // instead of timing each individual trial, we will time the entire set of
            // trials (for a given input size)

            // and divide by the number of trials -- this reduces the impact of the amount
            // of time it takes to call the

            // stopwatch methods themselves

            // BatchStopwatch.start(); // comment this line if timing trials individually

            int[] results = new int[3];
            // set up the strings for the trials

            // StringBuilder generatedString1 = new StringBuilder();
            // // // StringBuilder generatedString2 = new StringBuilder();

            // for (int i = 0; i < inputSize; i++) {
            // generatedString1.append("x");
            // // // generatedString2.append("x");
            // }

            // String formattedString1 = generatedString1.toString();
            // String formattedString2 = generatedString2.toString();

            int max = book1.length() - inputSize;
            int randomIndex1 = rand.nextInt(max - 0 + 1) + 0;
            int randomIndex2 = rand.nextInt(max - 0 + 1) + 0;

            String formattedString1 = book1.substring(randomIndex1, randomIndex1 + inputSize);
            String formattedString2 = book1.substring(randomIndex2, randomIndex2 + inputSize);

            // String formattedString1 = generateRandomStringOfLength(inputSize);
            // String formattedString2 = generateRandomStringOfLength(inputSize);

            // run the trials
            System.out.println("Timing Each sort individually wo gc every time forced...");
            System.out.print("    Starting trials for input size " + inputSize + " ... ");
            for (long trial = 0; trial < numberOfTrials; trial++) {

                // long[] testList = createRandomIntegerList(inputSize);

                /*
                 * force garbage collection before each trial run so it is not included in the
                 * time
                 */
                // System.gc();

                TrialStopwatch.start(); // *** uncomment this line if timing trials individually

                /* run the function we're testing on the trial input */

                ///////////////////////////////////////////
                /* DO BIDNESS */
                /////////////////////////////////////////

                results = lcs.bruteForceLCS(formattedString1, formattedString2);

                ///////////////////////////////////////////
                /* END DO BIDNESS */
                /////////////////////////////////////////

                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime(); // *** uncomment this line if timing
                                                                                    // trials individually

            }

            // batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if
            // timing trials individually

            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the
                                                                                                     // average time per
                                                                                                     // trial in this
                                                                                                     // batch
            double doublingRatio = 0;
            if (previousTime > 0) {
                doublingRatio = averageTimePerTrialInBatch / previousTime;
            }

            previousTime = averageTimePerTrialInBatch;
            /* print data for this size of input */

            resultsWriter.printf("%12d  %18.2f %18.1f\n", inputSize, averageTimePerTrialInBatch, doublingRatio); // might
                                                                                                                 // as
                                                                                                                 // well
                                                                                                                 // make
                                                                                                                 // the
                                                                                                                 // columns
                                                                                                                 // look
                                                                                                                 // nice

            resultsWriter.flush();

            System.out.println(" ....done.");

        }
    }

    // correctness testing
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