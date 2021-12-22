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
}
