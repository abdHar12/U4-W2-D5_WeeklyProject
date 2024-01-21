package harouane;

import com.github.javafaker.Faker;
import harouane.Entities.Bibliografia;
import harouane.Entities.Libro;
import harouane.Entities.Rivista;
import harouane.Enum.Periodicita;
import harouane.Exceptions.InexistentIsbn;
import harouane.Exceptions.Scelta;
import harouane.Interfaces.MyFunction;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Supplier;

public class Archivio {
    static boolean continueCycle;
    static int choice=0;


    static Faker faker= new Faker();
    public static void main(String[] args) {
        initialChoice();

    }

    public static void verifyChoice(int choice, int min, int max) throws Scelta{
        if (choice>max || choice<min) throw new Scelta(choice);
    }

    static MyFunction systemOutInitialChoice1=()->{
        System.out.println("\nCosa vuoi fare? ");
        System.out.println("0: Mostrare tutti gli elemnti");
        System.out.println("1: Aggiungere un elemento");
        System.out.println("2: Rimuovere un elemento");
        System.out.println("3: Trovare un elemento con ISBN");
        System.out.println("4: Trovare un elemento o più elementi con l'anno di pubblicazione digitato");
        System.out.println("5: Trovare un elemento o più elementi con lo stesso autore");
        System.out.println("6: Salvare su disco l'archivio");
        System.out.println("7: Caricare l'archivio del disco");
        System.out.println("8: Esci");
    };
    static MyFunction systemOutChoice1=()->{
        System.out.println("Che elemento vuoi aggiungere? ");
        System.out.println("1: Libro");
        System.out.println("2: Rivista");
        System.out.println("0: Torna alla pagina principale");
    };

    static MyFunction systemOutRemoveElementOrExit=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Rimuovere un elemento");
        System.out.println("0: Uscire");

    };

    public static void tryCatchForChoices(MyFunction f, int min, int max){
        continueCycle = true;
        while (continueCycle){
            Scanner sc= new Scanner(System.in);
            try {
                continueCycle= false;
                f.apply();
                System.out.printf("\nLa tua scelta: ");
                choice = sc.nextInt();
                verifyChoice(choice, min, max);
            } catch (Scelta e) {
                continueCycle = true;
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                continueCycle = true;
                System.out.println("Devi inserire un numero");
            } catch (Exception e) {
                System.err.println("C'e un problema!");
            }
        }
    }

    public static void initialChoice(){
        while (choice!=8){
        tryCatchForChoices(systemOutInitialChoice1,0, 8);
        switch (choice){
            case 0:
                System.out.println("\nEcco i tuoi elementi:");
                if(!(Bibliografia.getAllElements().isEmpty())) showAllElements();
                else System.out.println("Non ci sono elementi salvati nell'archivio");
                break;
            case 1:
                choice1();
                break;
            case 2:
                choice2();
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                showAllElements();
                System.exit(0);
                break;
        }
}
    }
    static Supplier<Integer> randomNumPage=()->{
        Random random= new Random();
        return random.nextInt(80, 600);
    };
    static Supplier<Integer> randomPeriodicity=()->{
        Random random= new Random();
        return random.nextInt(1, 3);
    };

    static void showAllElements(){
        Bibliografia.getAllElements().forEach(element->System.out.println(element.toString()));
    }
    public static void choice1(){
        Libro book;
        Rivista magazine;
        String title;
        String author;
        String genre;
        Date date;
        Periodicita periodicita = null;
        do{
            tryCatchForChoices(systemOutChoice1, 0, 2);
            switch (choice) {
                case 0:
                    initialChoice();
                    break;
                case 1:
                    title = faker.book().title();
                    author = faker.book().author();
                    genre = faker.book().genre();
                    date = faker.date().between(Date.from(LocalDate.of(2020, 01,01).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(Instant.from(LocalDate.of(2023, 12,31).atStartOfDay(ZoneId.systemDefault()).toInstant())));
                    book = new Libro(title, date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), randomNumPage.get(), author, genre);
                    Bibliografia.addToAllElements(book);
                    break;
                case 2:
                    title = faker.book().title();
                    date = faker.date().between(Date.from(LocalDate.of(2020, 01,01).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(Instant.from(LocalDate.of(2023, 12,31).atStartOfDay(ZoneId.systemDefault()).toInstant())));
                    switch (randomPeriodicity.get()) {
                        case 1:
                            periodicita = Periodicita.MENSILE;
                            break;
                        case 2:
                            periodicita = Periodicita.SEMESTRALE;
                            break;
                        case 3:
                            periodicita = Periodicita.SETTIMANALE;
                            break;
                    }
                    magazine = new Rivista(title, date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), randomNumPage.get(), periodicita);
                    Bibliografia.addToAllElements(magazine);
                    break;
            }
        }while (!(choice==0));
        Bibliografia.getAllElements().forEach(element->System.out.println(element.toString()));

    }

    public static void choice2(){
        Bibliografia.getAllElements().forEach(element->System.out.println(element.toString()));
        continueCycle=true;
        while (continueCycle){
            Scanner sc= new Scanner(System.in);
            continueCycle=false;
            while (choice!=0){
                tryCatchForChoices(systemOutRemoveElementOrExit, 0, 1);
                switch (choice) {
                    case 1:
                        showAllElements();
                        try {
                            System.out.print("Che elemento vuoi rimuovere: ");
                            System.out.print("Inserisci l'ISBN: ");
                            Integer isbn = sc.nextInt();
                            Bibliografia.removeElement(isbn);
                        } catch (InexistentIsbn e) {
                            System.out.println(e.getMessage());
                            continueCycle = true;
                            continue;
                        } catch (InputMismatchException e) {
                            System.out.println("Attento ad inserire solo numeri!");
                            continueCycle = true;
                            continue;
                        }
                        break;
                    case 0:
                        initialChoice();
                        break;
                }
            }
        }
    }
}
