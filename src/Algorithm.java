import java.util.*;

public class Algorithm {

    // Data de entrega: 23/06/2022
    // Grupo 01:
    // Cristina Kochmann
    // Gustavo Luiz Dutra Santos
    // Murillo Isidoro de Medeiros
    // Tulio Cesar Gontijo

    private static double pc;
    private static double pm;
    private static int generation;
    private static int lastSubject;
    private static int sizePopulation;
    private static int maxGenerations;
    private static int[][] population;
    private static int[][] newPopulation;
    private static Subject subjectFather;
    private static Subject subjectMother;
    private static List<Subject> populationWithNotes;

    public static void initialParams() {
        lastSubject = 0;
        population = new int[sizePopulation][64];
        newPopulation = new int[sizePopulation][64];
    }

    public static int[] randomQueens() {
        Random random = new Random();

        int queens = 1;
        int[] newSubject = new int[64];

        while (queens <= 8) {
            int column = random.nextInt(64);
            boolean isValidColumn = verifyColumn(column, newSubject);

            if (!isValidColumn) continue;

            newSubject[column] = 1;
            queens++;
        }

        return newSubject;
    }

    public static boolean verifyColumn(int actualColumn, int[] subject) {
        boolean isValid = true;

        int startColumn = 0;
        int endColumn = 0;

        if (actualColumn >= 0 && actualColumn <= 7) {
            startColumn = 0;
            endColumn = 8;
        } else if (actualColumn >= 8 && actualColumn <= 15) {
            startColumn = 8;
            endColumn = 16;
        } else if (actualColumn >= 16 && actualColumn <= 23) {
            startColumn = 16;
            endColumn = 24;
        } else if (actualColumn >= 24 && actualColumn <= 31) {
            startColumn = 24;
            endColumn = 32;
        } else if (actualColumn >= 32 && actualColumn <= 39) {
            startColumn = 32;
            endColumn = 40;
        } else if (actualColumn >= 40 && actualColumn <= 47) {
            startColumn = 40;
            endColumn = 48;
        } else if (actualColumn >= 48 && actualColumn <= 55) {
            startColumn = 48;
            endColumn = 56;
        } else if (actualColumn >= 56 && actualColumn <= 63) {
            startColumn = 56;
            endColumn = 64;
        }

        for (int column = startColumn; column < endColumn; column++) {
            if (subject[column] == 1) {
                isValid = false;
            }
        }

        return isValid;
    }

    public static void generatePopulation() {
        Random random = new Random();

        for (int subject = 0; subject < sizePopulation; subject++) {
            int queens = 1;

            while (queens <= 8) {
                int column = random.nextInt(64);
                boolean isValidColumn = verifyColumn(column, population[subject]);

                if (!isValidColumn) continue;

                population[subject][column] = 1;
                queens++;
            }
        }
    }

    public static void evaluation() {
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

    public static void selection() {
        Random random = new Random();

        int halfPopulation = sizePopulation / 2;

        int positionMother = random.nextInt(halfPopulation);
        int positionFather = random.nextInt(sizePopulation);

        while (positionFather == positionMother) {
            positionFather = random.nextInt(sizePopulation);
        }

        Subject mother = populationWithNotes.get(positionMother);
        Subject father = populationWithNotes.get(positionFather);

        subjectMother = mother;
        subjectFather = father;
    }

    public static void crossing() {
        Random random = new Random();

        double pcRandom = random.nextDouble();

        int[] vectorMother = new int[64];
        int[] vectorFather = new int[64];

        int length = 0;
        for (int line = 0; line < 8; line++) {
            for (int column = 0; column < 8; column++) {
                vectorMother[length] = subjectMother.getChessboard()[line][column];
                vectorFather[length] = subjectFather.getChessboard()[line][column];
                length++;
            }
        }

        if (pcRandom < pc) {
            for (int son = 0; son < 2; son++) {
                int[] children = new int[64];

                int firstPart = ((random.nextInt(8) + 1) * 8) - 1;
                int secondPart = firstPart + 1;

                for (int position = 0; position < 64; position++) {
                    if (position <= firstPart) {
                        children[position] = vectorMother[position];
                    }

                    if (position >= secondPart) {
                        children[position] = vectorFather[position];
                    }
                }

                newPopulation[lastSubject] = children;
                lastSubject++;
            }
        } else {
            newPopulation[lastSubject] = vectorFather;
            lastSubject++;
            newPopulation[lastSubject] = vectorMother;
            lastSubject++;
        }
    }

    public static void mutation() {
        Random random = new Random();

        double pmRandom = random.nextDouble();

        if (pmRandom < pm) {
            int indexLastSon = lastSubject - 1;
            int indexPenultimateSon = lastSubject - 2;

            int[] lastSon = randomQueens();
            int[] penultimateSon = randomQueens();

            newPopulation[indexLastSon] = lastSon;
            newPopulation[indexPenultimateSon] = penultimateSon;
        }
    }

    public static void viewPopulation() {
        System.out.printf("==========================================%n");
        System.out.printf("= Qtd. nova população: %d               =%n", lastSubject);
        System.out.printf("= Qtd. gerações: %d                      =%n", generation);
        System.out.printf("==========================================%n");

        System.out.printf("========= Melhor Indivíduo (%d) =========%n", populationWithNotes.get(0).getNote());

        for (int line = 0; line < 8; line++) {
            for (int column = 0; column < 8; column++) {
                System.out.print(populationWithNotes.get(0).getChessboard()[line][column] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void algorithm() {
        initialParams();
        generatePopulation();

        do {
            if (lastSubject == sizePopulation) {
                population = newPopulation;
            }

            lastSubject = 0;

            evaluation();
            ordination();

            do {
                selection();
                crossing();
                mutation();
            } while(sizePopulation > lastSubject);

            generation++;
        } while (generation < maxGenerations);

        viewPopulation();
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o tamanho da população desejada: ");
        sizePopulation = scanner.nextInt();

        if (sizePopulation == 1) {
            System.out.println("O tamanho da população deve ser maior que 1.");
            return;
        }

        System.out.println("Informe a quantidade máxima de gerações possíveis: ");
        maxGenerations = scanner.nextInt();

        System.out.println("Informe a probabilidade de cruzamento: ");
        pc = scanner.nextDouble();

        System.out.println("Informe a probabilidade de mutação: ");
        pm = scanner.nextDouble();

        algorithm();
    }

}
