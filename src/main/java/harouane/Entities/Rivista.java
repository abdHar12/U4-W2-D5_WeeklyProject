package harouane.Entities;

import harouane.Enum.Periodicita;

import java.time.LocalDate;

public class Rivista extends Bibliografia{
    Periodicita periodicita;

    public Rivista(String titolo, LocalDate yearOfPubblication, Integer numOfPage, Periodicita periodicita) {
        super(titolo, yearOfPubblication, numOfPage);
        this.periodicita = periodicita;
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
