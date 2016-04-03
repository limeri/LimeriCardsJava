package limeri.cards.exceptions;

public class NoPlayersFoundException extends Exception {

    static final long serialVersionUID = 1;

    public NoPlayersFoundException() {
        super("No players were found at this table.");
    }
}
