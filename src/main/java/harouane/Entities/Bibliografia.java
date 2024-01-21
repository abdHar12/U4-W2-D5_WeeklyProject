package harouane.Entities;

import harouane.Exceptions.DateNotFound;
import harouane.Exceptions.InexistentIsbn;

import java.util.*;

public abstract class Bibliografia {
    static List<Integer> allIsbn=new ArrayList<>();
    Integer isbn;
    String titolo;
    Integer yearOfPubblication;
    Integer numOfPage;

    static Set<Bibliografia> allElements=new HashSet<>();

    public Bibliografia(String titolo, Integer yearOfPubblication, Integer numOfPage) {
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

    public static Bibliografia findElementByIsbn(Integer isbn) throws InexistentIsbn{
        if(!(allIsbn.contains(isbn))) throw new InexistentIsbn(isbn);
        List<Bibliografia> foundElement= allElements.stream().filter(element-> Objects.equals(element.isbn, isbn)).toList();
        return foundElement.get(0);
    };
    public static List<Bibliografia> findElementsByDate(Integer year) throws DateNotFound {
        List<Bibliografia> foundElements= allElements.stream().filter(element-> Objects.equals(element.yearOfPubblication, year)).toList();
        if (!foundElements.isEmpty()) return foundElements;
        else throw new DateNotFound(year);
    };

}
