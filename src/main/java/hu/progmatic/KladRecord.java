package hu.progmatic;

public class KladRecord {
    Integer szuloKategoriaID;
    String nev;
    String latinNev;
    String leiras;

    public KladRecord(Integer szuloKategoriaID, String nev, String latinNev, String leiras) {
        this.szuloKategoriaID = szuloKategoriaID;
        this.nev = nev;
        this.latinNev = latinNev;
        this.leiras = leiras;
    }

    public static KladRecord factory(String line) {
        String[] lineTomb = line.split(",");
        String nev = lineTomb[0];
        String latinNev = lineTomb[1];
        String leiras = null;
        Integer szuloKategoriaID = null;
        if (lineTomb.length == 3) {
            leiras = lineTomb[2];
        }
        return new KladRecord(szuloKategoriaID, nev, latinNev, leiras);
    }

    @Override
    public String toString() {
        return "KladRecord{" +
                "szuloKategoriaID=" + szuloKategoriaID +
                ", nev='" + nev + '\'' +
                ", latinNev='" + latinNev + '\'' +
                ", leiras='" + leiras + '\'' +
                '}';
    }
}
