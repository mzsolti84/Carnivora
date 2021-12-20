package hu.progmatic;

public class KladLeirasRecord {
  String nev;
  String latinNev;
  String leiras;

  public KladLeirasRecord(String nev, String latinNev, String leiras) {
    this.nev = nev;
    this.latinNev = latinNev;
    this.leiras = leiras;
  }

  public static KladLeirasRecord factory (String line) {
    String[] lineTomb = line.split(",");
    String nev = lineTomb[0];
    String latinNev = lineTomb[1];
    String leiras = null;
    if (lineTomb.length == 3) { leiras = lineTomb[2]; }
    return new KladLeirasRecord(nev, latinNev, leiras);
  }

  @Override
  public String toString() {
    return "KladLeirasRecord{" +
        "nev='" + nev + '\'' +
        ", latinNev='" + latinNev + '\'' +
        ", leiras='" + leiras + '\'' +
        '}';
  }
}
