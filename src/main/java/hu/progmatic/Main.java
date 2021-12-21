package hu.progmatic;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    List<KladLeirasRecord> kladLeirasokInput=
        deSzerializalKladLeiras("C:\\Users\\zsolt\\IdeaProjects\\Carnivora\\src\\main\\java\\hu\\progmatic\\Sheet5.csv");
    Carnivora carnivora = new Carnivora();
    List<KladRecord> kategoriakInput = kladTolt();
    carnivora.init();
    carnivora.adatbazisToltKlad(kategoriakInput);
    carnivora.adatbazisToltKladLeiras(kladLeirasokInput);

    kladLeirasokInput.forEach(s -> System.out.println(s));
  }

  private static List<KladRecord> kladTolt() {
    return List.of(
        new KladRecord("állatok világa", null, 1),
        new KladRecord("ragadozók rendje", 1, 2),
        new KladRecord("macskaalkatúak alrendje", 2, 3),
        new KladRecord("madagaszkári cibetmacskafélék családja", 3, 4),
        new KladRecord("macskafélék családja", 3, 5),
        new KladRecord("macskaformák alcsaládja", 5, 6),
        new KladRecord("párducformák alcsaládja", 5, 7),
        new KladRecord("kardfogú macskák alcsaládja – kihalt", 5, 8),
        new KladRecord("mongúzfélék családja", 3, 9),
        new KladRecord("hiénafélék családja", 3, 10),
        new KladRecord("cibetmacskafélék családja", 3, 11),
        new KladRecord("pálmacibetfélék családja", 3, 12),
        new KladRecord("kutyaalkatúak alrendje", 2, 13),
        new KladRecord("macskamedvefélék családja", 13, 14),
        new KladRecord("kutyafélék családja", 13, 15),
        new KladRecord("bűzösborzfélék családja", 13, 16),
        new KladRecord("menyétfélék családja", 13, 17),
        new KladRecord("vidraformák alcsaládja", 17, 18),
        new KladRecord("borzformák alcsaládja", 17, 19),
        new KladRecord("méhészborzformák alcsaládja", 17, 20),
        new KladRecord("amerikai borzformák alcsaládja", 17, 21),
        new KladRecord("menyétformák alcsaládja", 17, 22),
        new KladRecord("mosómedvefélék családja", 13, 23),
        new KladRecord("medvefélék családja", 13, 24),
        new KladRecord("úszólábúak öregcsaládja", 13, 25),
        new KladRecord("rozmárfélék családja", 25, 26),
        new KladRecord("fülesfókafélék családja", 25, 27),
        new KladRecord("valódi fókafélék családja", 25, 28)
    );
  }

  public static List<KladLeirasRecord> deSzerializalKladLeiras(String fileName) {
    List<KladLeirasRecord> kladLeirasok= new ArrayList<>();
      File file = new File(fileName);
      try (Scanner fileScanner = new Scanner(file, StandardCharsets.UTF_8)) {
        fileScanner.nextLine(); //mert az első sor a fejléc
        while (fileScanner.hasNext()) {
          kladLeirasok.add(KladLeirasRecord.factory(fileScanner.nextLine()));
        }
      } catch (IOException e) {
        throw new RuntimeException("Nem létező fájl név");
      }
    return kladLeirasok;
  }
}
