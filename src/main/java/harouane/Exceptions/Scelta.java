package harouane.Exceptions;

public class Scelta extends RuntimeException{
    public Scelta(int number){super("Il numero "+ number+ " è un numero invalido");};
}
