package harouane.Entities;

import java.time.LocalDate;

public class Libro extends Bibliografia{
    String autore;
    String genre;

    public Libro(String titolo, LocalDate yearOfPubblication, Integer numOfPage, String autore, String genre) {
        super(titolo, yearOfPubblication, numOfPage);
        this.autore = autore;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "Isbn: "+isbn +
                ", autore='" + autore + '\'' +
                ", genre='" + genre + '\'' +
                ", titolo='" + titolo + '\'' +
                ", yearOfPubblication=" + yearOfPubblication +
                ", numOfPage=" + numOfPage +
                '}';
    }
}
