package harouane.Exceptions;

public class AuthorNotFound extends RuntimeException{
    public AuthorNotFound(String author){
            super("Nessun elemento trovato dell'autore "+author+ " !");
        }

}
