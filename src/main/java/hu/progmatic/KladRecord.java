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

  public KladRecord(String nev, Integer szuloKategoriaID) {
    this.szuloKategoriaID = szuloKategoriaID;
    this.nev = nev;
  }

  public static KladRecord factory (String line) {
    String[] lineTomb = line.split(",");
    String nev = lineTomb[0];
    String latinNev = lineTomb[1];
    String leiras = null;
    if (lineTomb.length == 3) { leiras = lineTomb[2]; }
    return new KladRecord(null, nev, latinNev, leiras);
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
