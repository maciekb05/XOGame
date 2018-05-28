public class Human implements Player {
    private final char sign;

    public Human(char sign) {
        this.sign = sign;
    }

    @Override
    public void move(Board board) {
        System.out.println("Ruch gracza " + sign + ", wybierz pozycje:");

    }
}
