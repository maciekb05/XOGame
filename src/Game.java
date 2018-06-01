public class Game {
    private Board board;
    private final Player playerX;
    private final Player playerO;

    public Game(int sizeOfBoard) {
        board = new Board(sizeOfBoard);
        playerO = new Computer('O');
        playerX = new Human('X');
    }

    public void play() {
        board.printGameBoard();
        while(true) {

            board = playerX.move(board);
            if(board.endOfGame()){
                break;
            }
            board.printGameBoard();

            board = playerO.move(board);
            if(board.endOfGame()){
                break;
            }
            board.printGameBoard();
        }
        board.printGameBoard();
        System.out.println("Koniec");
    }
}
