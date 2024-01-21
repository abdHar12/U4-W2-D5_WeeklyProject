package harouane.Exceptions;

public class InexistentIsbn extends RuntimeException{
    public InexistentIsbn(Integer isbn){
        super(isbn+": ISBN non esistente");
    }
}
