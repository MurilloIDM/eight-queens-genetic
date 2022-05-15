public class Individual {

    private int note;
    private int[][] chessboard;

    public Individual(int note, int[][] chessboard) {
        this.note = note;
        this.chessboard = chessboard;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int[][] getChessboard() {
        return chessboard;
    }

    public void setChessboard(int[][] chessboard) {
        this.chessboard = chessboard;
    }
}
