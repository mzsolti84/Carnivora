package hu.progmatic;

public class KladRecord {
  Integer szuloKategoriaID;
  Integer kladLeirasID;
  String nev;

  public KladRecord(String nev, Integer szuloKategoriaID, Integer kladLeirasID) {
    this.szuloKategoriaID = szuloKategoriaID;
    this.kladLeirasID = kladLeirasID;
    this.nev = nev;
  }
}
