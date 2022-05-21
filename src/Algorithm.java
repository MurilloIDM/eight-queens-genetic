import java.util.*;

public class Algorithm {

    private static int[][] population;
    private static List<Subject> populationWithNotes;

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

    public static void evaluation(int sizePopulation) {
        List<Subject> subjects = new ArrayList<>();

        for (int individual = 0; individual < sizePopulation; individual++) {
            int position = 0;
            int[][] chessboard = new int[8][8];

            for (int line = 0; line < 8; line++) {
                for (int column = 0; column < 8; column++) {
                    chessboard[line][column] = population[individual][position];
                    position++;
                }
            }

            int attacks = 0;
            for (int line = 0; line < 8; line++) {
                for (int column = 0; column < 8; column++) {
                    int queen = chessboard[line][column];

                    if (queen != 1) continue;

                    // Verificação de ataque na mesma linha
                    for (int k = 0; k < 8; k++) {
                        if (column == k) continue;
                        if (chessboard[line][k] == 1) attacks += 1;
                    }

                    // Verificação de ataque na mesma coluna
                    for (int k = 0; k < 8; k++) {
                        if (line == k) continue;
                        if (chessboard[k][column] == 1) attacks += 1;
                    }

                    // Variáveis para validações de ataque nas diagonais
                    boolean flag;
                    int lineRef;
                    int columnRef;

                    // Verificação de ataque na diagonal principal superior
                    flag = true;
                    lineRef = line > 0 ? line - 1 : -1;
                    columnRef = column > 0 ? column - 1 : -1;
                    while (flag) {

                        if (lineRef < 0 || columnRef < 0) {
                            flag = false;
                            break;
                        }

                        if (chessboard[lineRef][columnRef] == 1) attacks += 1;

                        if (lineRef >= 0) lineRef--;
                        if (columnRef >= 0) columnRef--;
                    }

                    // Verificação de ataque na diagonal principal inferior
                    flag = true;
                    lineRef = line < 7 ? line + 1 : 8;
                    columnRef = column < 7 ? column + 1 : 8;
                    while (flag) {

                        if (lineRef > 7 || columnRef > 7) {
                            flag = false;
                            break;
                        }

                        if (chessboard[lineRef][columnRef] == 1) attacks += 1;

                        if (lineRef <= 7) lineRef++;
                        if (columnRef <= 7) columnRef++;
                    }

                    // Verificação de ataque na diagonal secundária superior
                    flag = true;
                    lineRef = line > 0 ? line - 1 : -1;
                    columnRef = column < 7 ? column + 1 : 8;
                    while (flag) {

                        if (lineRef < 0 || columnRef > 7) {
                            flag = false;
                            break;
                        }

                        if (chessboard[lineRef][columnRef] == 1) attacks += 1;

                        if (lineRef >= 0) lineRef--;
                        if (columnRef <= 7) columnRef++;
                    }

                    // Verificação de ataque na diagonal secundária inferior
                    flag = true;
                    lineRef = line < 7 ? line + 1 : 8;
                    columnRef = column > 0 ? column - 1 : -1;
                    while (flag) {

                        if (lineRef > 7 || columnRef < 0) {
                            flag = false;
                            break;
                        }

                        if (chessboard[lineRef][columnRef] == 1) attacks += 1;

                        if (lineRef <= 7) lineRef++;
                        if (columnRef >= 0) columnRef--;
                    }
                }
            }

            Subject subject = new Subject(attacks, chessboard);
            subjects.add(subject);
        }
        populationWithNotes = subjects;
    }

    public static void ordination() {
        Collections.sort(populationWithNotes, Comparator.comparing(Subject::getNote));
    }

    public static void viewPopulation(int sizePopulation) {
        for (int subject = 0; subject < sizePopulation; subject++) {
            System.out.println("Ataque = " + populationWithNotes.get(subject).getNote());
        }
    }

    public static void algorithm(int sizePopulation) {
        int generation = 0;

        int maxGenerations = 10;
        int sizePopulationResult = 0;

        int[][] populationResult = new int[sizePopulation][64];

        initialParams(sizePopulation);

        generatePopulation(sizePopulation);
        evaluation(sizePopulation);

        ordination();

        viewPopulation(sizePopulation);

//        do {
//            do {
//            } while(sizePopulation > sizePopulationResult);
//
//
//        } while (generation < maxGenerations);

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o tamanho da população desejada: ");
        int sizePopulation = scanner.nextInt();

        // Solicitar limite de gerações, pc, pm

        algorithm(sizePopulation);
    }

}
