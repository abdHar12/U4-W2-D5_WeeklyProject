package harouane;

import com.github.javafaker.Faker;
import harouane.Entities.Bibliografia;
import harouane.Entities.Libro;
import harouane.Entities.Rivista;
import harouane.Enum.Periodicita;
import harouane.Exceptions.AuthorNotFound;
import harouane.Exceptions.DateNotFound;
import harouane.Exceptions.InexistentIsbn;
import harouane.Exceptions.Scelta;
import harouane.Interfaces.MyFunction;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;

public class Archivio {
    static boolean continueCycle;
    static int choice=0;
    static File file = new File("/src/output.txt");
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
    static MyFunction systemOutFindByDate=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare uno o più elemento usando la data");
        System.out.println("0: Uscire");
    };
    static MyFunction systemOutFindElementOrExit=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare un elemento");
        System.out.println("0: Uscire");

    };
    static MyFunction systemOutPutTheIsbnToRemove=()->{
        Scanner sc= new Scanner(System.in);
        showAllElements();
        System.out.println("Che elemento vuoi rimuovere: ");
        System.out.printf("Inserisci l'ISBN: ");
        Integer isbn = sc.nextInt();
        Bibliografia.removeElement(isbn);
    };
    static MyFunction systemOutPutTheIsbnToFind=()->{
        Scanner sc= new Scanner(System.in);
        System.out.println("Che elemento vuoi trovare: ");
        System.out.printf("Inserisci l'ISBN: ");
        Integer isbn = sc.nextInt();
        System.out.print("Ecco il tuo elemento: ");
        System.out.println(Bibliografia.findElementByIsbn(isbn));
    };
    static MyFunction systemOutFindByAuthor=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare un elemento uno o più elementi cercando l'autore");
        System.out.println("0: Uscire");
    };

    static MyFunction systemOutChargeInFile=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Caricare l'archivio in un file");
        System.out.println("0: Uscire");
    };
    static MyFunction systemOutReadFile=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Leggere l'archivio dal file");
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
                choice3();
                break;
            case 4:
                choice4();
                break;
            case 5:
                choice5();
                break;
            case 6:
                choice6();
                break;
            case 7:
                choice7();
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
    static Supplier<Integer> yearSupplier=()->{
        Random random= new Random();
        return random.nextInt(1990, 2023);
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
        Integer date;
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
                    date = yearSupplier.get();
                    book = new Libro(title, date, randomNumPage.get(), author, genre);
                    Bibliografia.addToAllElements(book);
                    Libro.addAllBooks(book);
                    break;
                case 2:
                    title = faker.book().title();
                    date = yearSupplier.get();
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
                    magazine = new Rivista(title, date, randomNumPage.get(), periodicita);
                    Bibliografia.addToAllElements(magazine);
                    Rivista.addAllMagazines(magazine);
                    break;
            }
        }while (!(choice==0));
        Bibliografia.getAllElements().forEach(element->System.out.println(element.toString()));
    }
    public static void choice2(){
        Bibliografia.getAllElements().forEach(element->System.out.println(element.toString()));
        removeOrFindTheIsbn(systemOutRemoveElementOrExit, systemOutPutTheIsbnToRemove);
    }
    public static void choice3(){
        removeOrFindTheIsbn(systemOutFindElementOrExit, systemOutPutTheIsbnToFind);
    }
    public static void choice4() {
        continueCycle=true;
        while (continueCycle){
            continueCycle=false;
            while (choice!=0){
                Scanner sc= new Scanner(System.in);
                tryCatchForChoices(systemOutFindByDate, 0, 1);
                switch (choice) {
                    case 1:
                        try {
                            System.out.printf("Inserisci l'anno di pubblicazione: ");
                            Integer year= sc.nextInt();
                            Bibliografia.findElementsByDate(year).forEach(element->System.out.println(element));
                        } catch (DateNotFound e) {
                            System.out.println("Data non trovata!");
                            continueCycle = true;
                            continue;
                        }catch (InputMismatchException e){
                            System.out.println("Attenzione devi inserire un anno!");
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
    public static void choice5(){
        continueCycle=true;
        while (continueCycle){
            continueCycle=false;
            while (choice!=0){
                Scanner sc= new Scanner(System.in);
                tryCatchForChoices(systemOutFindByAuthor, 0, 1);
                switch (choice) {
                    case 1:
                        try {
                            System.out.printf("Inserisci l'autore: ");
                            String author= sc.nextLine();
                            Libro.findElementsByAuthor(author).forEach(element->System.out.println(element));
                        } catch (AuthorNotFound e) {
                            System.out.println("Autore non trovata!");
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

    public static void choice6(){
        File file = new File("/src/output.txt");
        continueCycle=true;
        while (continueCycle){
            continueCycle=false;
            while (choice!=0){
                tryCatchForChoices(systemOutChargeInFile, 0, 1);
                switch (choice) {
                    case 1:
                        file.delete();
                        Bibliografia.getAllElements().forEach(element-> {
                                try {
                                    FileUtils.writeStringToFile(file, element + System.lineSeparator(), StandardCharsets.UTF_8, true);
                                } catch (IOException e) {
                                    System.out.println("Problemi con l'aggiornamento del file");
                                    continueCycle=true;
                                }
                            });
                            System.out.println("File aggiornato con successo!");
                        break;
                    case 0:
                        initialChoice();
                        break;
                }
            }
        }
    }
    public static void choice7(){
        continueCycle=true;
        while (continueCycle){
            continueCycle=false;
            while (choice!=0){
                tryCatchForChoices(systemOutReadFile, 0, 1);
                switch (choice) {
                    case 1:
                            try {
                                String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                                System.out.println("Ho trovato all'interno del file: ");
                                System.out.println(content);
                            } catch (IOException e) {
                                System.out.println("Problemi con la lettura del file");
                                continueCycle=true;
                            }
                        System.out.println("File aggiornato con successo!");
                        break;
                    case 0:
                        initialChoice();
                        break;
                }
            }
        }
    }
    public static void removeOrFindTheIsbn(MyFunction f1, MyFunction f2){
        continueCycle=true;
        while (continueCycle){
            continueCycle=false;
            while (choice!=0){
                tryCatchForChoices(f1, 0, 1);
                switch (choice) {
                    case 1:
                        try {
                            f2.apply();
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
