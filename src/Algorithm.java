import java.util.Random;

public class Algorithm {

    private static int[][] population;
    private static int[] notes;

    public static void initialParams(int sizePopulation) {
       population = new int[sizePopulation][64];
    }

    public static void generatePopulation(int sizePopulation) {
        Random random = new Random();

        for (int line = 0; line < sizePopulation; line++) {
            int aux = 100;
            int queens = 0;

            while (queens != 8) {
                int column = random.nextInt(64);

                if (column != aux) {
                    population[line][column] = 1;
                    aux = column;
                    queens++;
                }
            }
        }
    }

    public static void evaluation() {
        for (int individual = 0; individual < 1; individual++) {
            int position = 0;
            int[][] chessboard = new int[8][8];

            for (int line = 0; line < 8; line++) {
                for (int column = 0; column < 8; column++) {
                    chessboard[line][column] = population[individual][position];
                    position++;
                }
            }

            for (int line = 0; line < 8; line++) {
                for (int column = 0; column < 8; column++) {
                    System.out.print(chessboard[line][column] + " ");
                }
                System.out.print("\n");
            }
        }
    }

    public static void viewPopulation(int sizePopulation) {
        for (int line = 0; line < sizePopulation; line++) {
            for (int column = 0; column < 64; column++) {
                System.out.print(population[line][column] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void algorithm() {
        int generation = 0;

        int maxGenerations = 10;
        int sizePopulation = 20;
        int sizePopulationResult = 0;

        int[][] populationResult = new int[sizePopulation][64];

        initialParams(sizePopulation);

        generatePopulation(sizePopulation);
        evaluation();

//        do {
//            do {
//            } while(sizePopulation > sizePopulationResult);
//
//
//        } while (generation < maxGenerations);

    }

    public static void main(String[] args) {
        algorithm();
    }

}
