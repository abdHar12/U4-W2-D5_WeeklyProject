package harouane.Entities;

import harouane.Enum.Periodicita;

import java.util.ArrayList;
import java.util.List;

public class Rivista extends Bibliografia{
    Periodicita periodicita;
    static List<Rivista> allMagazines=new ArrayList<>();

    public Rivista(String titolo, Integer yearOfPubblication, Integer numOfPage, Periodicita periodicita) {
        super(titolo, yearOfPubblication, numOfPage);
        this.periodicita = periodicita;
    }
    public static void addAllMagazines(Rivista magazine){
        allMagazines.add(magazine);
    }
    @Override
    public String toString() {
        return "Rivista{" +
                "Isbn= "+isbn +
                ", periodicita=" + periodicita +
                ", titolo='" + titolo + '\'' +
                ", yearOfPubblication=" + yearOfPubblication +
                ", numOfPage=" + numOfPage +
                '}';
    }

}
