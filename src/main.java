public class main {

    public static void showChessBoard(int[][] chessBoard) {
        for (int line = 0; line < 8; line++) {
            for (int column = 0; column < 8; column++) {
                if (chessBoard[line][column] == 1) {
                    System.out.print("R\t");
                    continue;
                }
                System.out.print("-\t");
            }
            System.out.print("\n\n");
        }
        System.out.print("\n");
    }

    /*
    * Essa função visa verificar se é seguro colocar a rainha em determinada posição,
    * de modo que, seja validado as possibilidades de ataque.
    * Deverá ser verificado as seguintes possibilidades de ataque:
    * -> Ataque na linha (verificar todas as casas daquela linha)
    * -> Ataque na coluna (verificar todas as casas daquela coluna)
    * -> Ataque na diagonal principal (verificar todas as casas acima e abaixo da diagonal)
    * -> Ataque na diagonal secundária (verificar todas as casas acima e abaixo da diagonal)
    * Caso a avaliação de algum desses cenários contenha uma rainha (1), deverá ser retornar "false".
    * Caso passe por todos esses cenários, deverá ser retornando "true".
    * */
    public static boolean isPossibleAddQueen(int[][] chessBoard, int line, int column) {
        return true;
    }

    /*
    * Essa função visa executar a solução para o problema,
    * de modo que, irá adicionar a rainha, representa pelo número 1, caso seja possível.
    * A chamada dessa função ocorre de modo recursivo.
    * */
    public static void resolve(int[][] chessBoard, int column) {
        if (column == 8) {
            System.out.printf("Solução %d:\n\n", 1);
            showChessBoard(chessBoard);
            return;
        }

        for (int line = 0; line < 8; line++) {
            if (isPossibleAddQueen(chessBoard, line, column)) {
                chessBoard[line][column] = 1;

                resolve(chessBoard, column + 1);

                chessBoard[line][column] = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[][] chessBoard = new int[8][8];

        int actualColumn = 0;
        resolve(chessBoard, actualColumn);
    }

}
