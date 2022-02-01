package hu.progmatic;


public class FajRecordRegi {
  Integer szuloKategoriaID;
  String nev;
  String latinNev;
  String leiras;
  VeszelyeztetettKategoriak veszelyeztetettBesorolas;
  boolean specialista;
  String fotoURL;
  String wikiURL;

  public FajRecordRegi(Integer szuloKategoriaID, String nev, String latinNev, String leiras,
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

  public static FajRecordRegi factory(String line) {
    String[] lineTomb = line.split(",");
    Integer szuloKategoriaID = Integer.parseInt(lineTomb[0]);
    String nev = lineTomb[1];
    String latinNev = lineTomb[2];
    String leiras = lineTomb[3];
    VeszelyeztetettKategoriak veszelyeztetettBesorolas = kategoriaAtfordit(lineTomb[4]);
    boolean specialista = specialistaAtfordit(lineTomb[5]);
    String fotoURL = lineTomb[6];
    String wikiURL = lineTomb[7];
    return new FajRecordRegi(szuloKategoriaID, nev, latinNev, leiras, veszelyeztetettBesorolas,
        specialista, fotoURL, wikiURL);
  }

  private static boolean specialistaAtfordit(String line) {
    return line.equals("IGAZ") || line.equals("TRUE");
  }

  private static VeszelyeztetettKategoriak kategoriaAtfordit(String line) {
    String value = String.valueOf(line).toUpperCase();
    return switch (value) {
      case "KIHALT" -> VeszelyeztetettKategoriak.KIHALT;
      case "VADON KIHALT" -> VeszelyeztetettKategoriak.VADON_KIHALT;
      case "FENYEGETETT" -> VeszelyeztetettKategoriak.FENYEGETETT;
      case "SÚLYOSAN VESZÉLYEZTETETT" -> VeszelyeztetettKategoriak.SULYOSAN_VESZELYEZTETETT;
      case "VESZÉLYEZTETETT" -> VeszelyeztetettKategoriak.VESZELYEZTETETT;
      case "SEBEZHETO" -> VeszelyeztetettKategoriak.SEBEZHETO;
      case "MÉRSÉKELTEN FENYEGETETT" -> VeszelyeztetettKategoriak.MERSEKELTEN_FENYEGETETT;
      case "NEM FENYEGETETT" -> VeszelyeztetettKategoriak.NEM_FENYEGETETT;
      default -> VeszelyeztetettKategoriak.HAZIASITOTT;
    };
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
