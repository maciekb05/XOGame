public class Game {
    Board board;
    Player playerX;
    Player playerO;

    public Game(int sizeOfBoard) {
        board = new Board(sizeOfBoard);
    }

    public void play() {
        while(!board.endOfGame()) {

        }
    }
}
