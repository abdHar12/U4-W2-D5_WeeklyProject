package harouane.Entities;

import harouane.Exceptions.AuthorNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Libro extends Bibliografia{
    String autore;
    String genre;

    static List<Libro> allBooks=new ArrayList<>();
    public Libro(String titolo, Integer yearOfPubblication, Integer numOfPage, String autore, String genre) {
        super(titolo, yearOfPubblication, numOfPage);
        this.autore = autore;
        this.genre = genre;
    }
    public static void addAllBooks(Libro book){
        allBooks.add(book);
    }
    public static List<Libro> findElementsByAuthor(String author) throws AuthorNotFound {
        List<Libro> foundElements= allBooks.stream().filter(element-> Objects.equals(element.autore, author)).toList();
        if (!foundElements.isEmpty()) return foundElements;
        else throw new AuthorNotFound(author);
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
