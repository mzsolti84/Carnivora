package hu.progmatic;

import java.util.Locale;

public class FajRecord {
  Integer szuloKategoriaID;
  String nev;
  String latinNev;
  String leiras;
  VeszelyeztetettKategoriak veszelyeztetettBesorolas;
  boolean specialista;
  String fotoURL;
  String wikiURL;

  public FajRecord(Integer szuloKategoriaID, String nev, String latinNev, String leiras,
                   VeszelyeztetettKategoriak veszelyeztetettBesorolas, boolean specialista,
                   String fotoURL, String wikiURL) {
    this.szuloKategoriaID = szuloKategoriaID;
    this.nev = nev;
    this.latinNev = latinNev;
    this.leiras = leiras;
    this.veszelyeztetettBesorolas = veszelyeztetettBesorolas;
    this.specialista = specialista;
    this.fotoURL = fotoURL;
    this.wikiURL = wikiURL;
  }

  public static FajRecord factory(String line) {
    String[] lineTomb = line.split(",");
    Integer szuloKategoriaID = Integer.parseInt(lineTomb[0]);
    String nev = lineTomb[1];
    String latinNev = lineTomb[2];
    String leiras = lineTomb[3];
    VeszelyeztetettKategoriak veszelyeztetettBesorolas = kategoriaAtfordit(lineTomb[4]);
    boolean specialista = specialistaAtfordit(lineTomb[5]);
    String fotoURL = lineTomb[6];
    String wikiURL = lineTomb[7];
    return new FajRecord(szuloKategoriaID, nev, latinNev, leiras, veszelyeztetettBesorolas,
        specialista, fotoURL, wikiURL);
  }

  private static boolean specialistaAtfordit(String line) {
    return line.equals("IGAZ") || line.equals("TRUE");
  }

  private static VeszelyeztetettKategoriak kategoriaAtfordit(String line) {
    String value = String.valueOf(line).toUpperCase();
    switch (value) {
      case "KIHALT":
        return VeszelyeztetettKategoriak.KIHALT;
      case "VADON KIHALT":
        return VeszelyeztetettKategoriak.VADON_KIHALT;
      case "FENYEGETETT":
        return VeszelyeztetettKategoriak.FENYEGETETT;
      case "SÚLYOSAN VESZÉLYEZTETETT":
        return VeszelyeztetettKategoriak.SULYOSAN_VESZELYEZTETETT;
      case "VESZÉLYEZTETETT":
        return VeszelyeztetettKategoriak.VESZELYEZTETETT;
      case "SEBEZHETO":
        return VeszelyeztetettKategoriak.SEBEZHETO;
      case "MÉRSÉKELTEN FENYEGETETT":
        return VeszelyeztetettKategoriak.MERSEKELTEN_FENYEGETETT;
      case "NEM FENYEGETETT":
        return VeszelyeztetettKategoriak.NEM_FENYEGETETT;
      default:
        return VeszelyeztetettKategoriak.HAZIASITOTT;
    }
  }

  @Override
  public String toString() {
    return "FajRecord{" +
        "szuloKategoriaID=" + szuloKategoriaID +
        ", nev='" + nev + '\'' +
        ", latinNev='" + latinNev + '\'' +
        ", leiras='" + leiras + '\'' +
        ", veszelyeztetettBesorolas=" + veszelyeztetettBesorolas +
        ", specialista=" + specialista +
        ", fotoURL='" + fotoURL + '\'' +
        ", wikiURL='" + wikiURL + '\'' +
        '}';
  }
}