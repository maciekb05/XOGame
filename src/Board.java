import java.util.ArrayList;

public class Board {
    final int NUMBER_TO_WIN = 5;
    private final int size;
    private ArrayList<ArrayList<Character>> gameBoard;

    Board(int size) {
        this.size = size;
        gameBoard = new ArrayList<>();

        for(int i = 0; i < this.size; i++) {
            gameBoard.add(new ArrayList<>());
            for(int j = 0; j < this.size; j++) {
                gameBoard.get(i).add(' ');
            }
        }
    }

    public Board(Board board) {
        this.size = board.size;
        gameBoard = new ArrayList<>();

        for(int i = 0; i < this.size; i++) {
            gameBoard.add(new ArrayList<>());
            for(int j = 0; j < this.size; j++) {
                gameBoard.get(i).add(board.getGameBoard().get(i).get(j));
            }
        }
    }

    ArrayList<ArrayList<Character>> getGameBoard(){
        return gameBoard;
    }

    public void setGameBoard(ArrayList<ArrayList<Character>> gameBoard) {
        this.gameBoard = gameBoard;
    }

    int getSize() {
        return size;
    }

    char charAt(int posX, int posY) {
        return gameBoard.get(posX).get(posY);
    }

    char charAtNormalized(int posX, int posY) {
        return gameBoard.get(posX-1).get(posY-1);
    }

    private String makeVisualizationOfBoard() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("    ");
        for(int j = 0; j < size; j++) {
            if(j < 10) {
                stringBuilder.append("  ");
                stringBuilder.append(j + 1);
                stringBuilder.append(' ');
            }
            else {
                stringBuilder.append(" ");
                stringBuilder.append(j + 1);
                stringBuilder.append(' ');
            }
        }
        stringBuilder.append('\n');

        stringBuilder.append("     ");
        for(int j = 0; j < size; j++) {
            stringBuilder.append("____");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append('\n');

        for(int i = 0; i < size; i++) {

            if(i < 9){
                stringBuilder.append("  ");
                stringBuilder.append(i + 1);
                stringBuilder.append(' ');
            }
            else {
                stringBuilder.append(' ');
                stringBuilder.append(i + 1);
                stringBuilder.append(' ');
            }

            for(int j = 0; j < size; j++) {


                stringBuilder.append("| ");
                stringBuilder.append(gameBoard.get(i).get(j));
                stringBuilder.append(' ');
            }

            stringBuilder.append("|\n");
            stringBuilder.append("    ");

            for(int j = 0; j < size; j++) {
                stringBuilder.append("|___");
            }

            stringBuilder.append("|\n");
        }

        return stringBuilder.toString();
    }

    void printGameBoard() {
        System.out.println(makeVisualizationOfBoard());
    }

    void move(int posX, int posY, char XorO) throws Exception {
        checkMoveAndMove(makeNaturalIndex(posX), makeNaturalIndex(posY), XorO);
    }

    private int makeNaturalIndex(int index) {
        return index - 1;
    }

    private void checkMoveAndMove(int posX, int posY, char XorO) throws Exception {
        if(XorO == 'X' || XorO =='O') {
            if(positionIsInRangeOfBoard(posX, posY) && positionIsEmpty(posX, posY)) {
                moveInBoard(posX, posY, XorO);
            }
            else{
                System.out.println(posX + " " + posY);
                throw new Exception("This position is invalid");
            }
        }
        else {
            throw new Exception("Bad sign in moveInBoard. It must be 'X' or 'O'");
        }
    }

    private boolean positionIsEmpty(int posX, int posY) {
        return gameBoard.get(posX).get(posY)==' ';
    }

    boolean positionIsInRangeOfBoard(int posX, int posY) {
        return posX < size && posY < size && posX >= 0 && posY >= 0;
    }

    private void moveInBoard(int posX, int posY, char XorO) {
        gameBoard.get(posX).set(posY, XorO);
    }

    boolean endOfGame() {
        boolean remis = true;
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(gameBoard.get(i).get(j) == ' '){
                    remis = false;

                }
            }
        }
        if(remis){
            System.out.println("REMIS");
            return true;
        }
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(gameBoard.get(i).get(j) == 'X' || gameBoard.get(i).get(j) == 'O') {
                    if(checkWinForPositon(i, j)) {
                        System.out.println("Wygral gracz: "+ gameBoard.get(i).get(j));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkWinForPositon(int posX, int posY) {
        char XorO = gameBoard.get(posX).get(posY);
        if(checkRightFor(posX, posY, XorO))
            return true;
        if(checkLeftFor(posX, posY, XorO))
            return true;
        if(checkTopFor(posX, posY, XorO))
            return true;
        if(checkBottomFor(posX, posY, XorO))
            return true;
        if(checkRightBottom(posX, posY, XorO))
            return true;
        if(checkRightTop(posX, posY, XorO))
            return true;
        if(checkLeftTop(posX, posY, XorO))
            return true;
        return checkLeftBottom(posX, posY, XorO);
    }

    private boolean checkRightFor(int posX, int posY, char XorO) {
        for(int i = posY; i < posY + NUMBER_TO_WIN; i++) {
            if(positionNotOfThanSign(posX, i, XorO)){
                return false;
            }
        }

        return true;
    }

    private boolean checkLeftFor(int posX, int posY, char XorO) {
        for(int i = posY; i >= posY - NUMBER_TO_WIN; i--) {
            if(positionNotOfThanSign(posX, i, XorO)){
                return false;
            }
        }

        return true;
    }

    private boolean checkBottomFor(int posX, int posY, char XorO) {
        for(int i = posX; i < posX + NUMBER_TO_WIN; i++) {
            if(positionNotOfThanSign(i, posY, XorO)){
                return false;
            }
        }

        return true;
    }

    private boolean checkTopFor(int posX, int posY, char XorO) {
        for(int i = posX; i >= posX - NUMBER_TO_WIN; i--) {
            if(positionNotOfThanSign(i, posY, XorO)){
                return false;
            }
        }

        return true;
    }

    private boolean checkRightBottom(int posX, int posY, char XorO) {
        for(int i = 0; i < NUMBER_TO_WIN; i++) {
            if(positionNotOfThanSign(posX, posY, XorO)){
                return false;
            }
            posX += 1;
            posY += 1;
        }

        return true;
    }

    private boolean checkRightTop(int posX, int posY, char XorO) {
        for(int i = 0; i < NUMBER_TO_WIN; i++) {
            if(positionNotOfThanSign(posX, posY, XorO)){
                return false;
            }
            posX -= 1;
            posY += 1;
        }

        return true;
    }

    private boolean checkLeftBottom(int posX, int posY, char XorO) {
        for(int i = 0; i < NUMBER_TO_WIN; i++) {
            if(positionNotOfThanSign(posX, posY, XorO)){
                return false;
            }
            posX += 1;
            posY -= 1;
        }

        return true;
    }

    private boolean checkLeftTop(int posX, int posY, char XorO) {
        for(int i = 0; i < NUMBER_TO_WIN; i++) {
            if(positionNotOfThanSign(posX, posY, XorO)){
                return false;
            }
            posX -= 1;
            posY -= 1;
        }

        return true;
    }

    private boolean notInRangeOfSize(int index) {
        return index < 0 || index >= size;
    }

    private boolean positionNotOfThanSign(int posX, int posY, char XorO) {
        if(notInRangeOfSize(posX) || notInRangeOfSize(posY)){
            return true;
        }
        return gameBoard.get(posX).get(posY) != XorO;
    }

}
