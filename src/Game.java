public class Game {
    private final Board board;
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

            playerX.move(board);
            if(board.endOfGame()){
                break;
            }
            board.printGameBoard();

            playerO.move(board);
            if(board.endOfGame()){
                break;
            }
            board.printGameBoard();
        }
        board.printGameBoard();
        System.out.println("Koniec");
    }
}
