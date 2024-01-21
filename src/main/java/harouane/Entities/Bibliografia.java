package harouane.Entities;

import harouane.Exceptions.InexistentIsbn;

import java.time.LocalDate;
import java.util.*;

public abstract class Bibliografia {
    static List<Integer> allIsbn=new ArrayList<>();
    Integer isbn;
    String titolo;
    LocalDate yearOfPubblication;
    Integer numOfPage;

    static Set<Bibliografia> allElements=new HashSet<>();

    public Bibliografia(String titolo, LocalDate yearOfPubblication, Integer numOfPage) {
        Random random= new Random();
        Integer num= random.nextInt();
            while ((allIsbn.contains(num))) {
                num = random.nextInt();
            }
        isbn=Math.abs(num);
        allIsbn.add(isbn);
        this.titolo = titolo;
        this.yearOfPubblication =yearOfPubblication;
        this.numOfPage = numOfPage;
    }

    public static Set<Bibliografia> getAllElements() {
        return allElements;
    }

    public static void addToAllElements(Bibliografia bibliografia){
        getAllElements().add(bibliografia);
    }
    public static void removeElement(Integer isbn) throws InexistentIsbn {
        if(!(allIsbn.contains(isbn))) throw new InexistentIsbn(isbn);
        Iterator it = Bibliografia.allElements.iterator();
        while (it.hasNext()){
            Bibliografia elemento= (Bibliografia) it.next();
            if(Objects.equals(elemento.isbn, isbn))it.remove();
        }
    }
}
