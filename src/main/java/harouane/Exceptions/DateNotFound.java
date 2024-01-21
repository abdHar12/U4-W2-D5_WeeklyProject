package harouane.Exceptions;

public class DateNotFound extends RuntimeException{
    public DateNotFound(Integer year){
        super("Nessun elemento trovato con data di pubblicazione "+year+ " !");
    }
}
