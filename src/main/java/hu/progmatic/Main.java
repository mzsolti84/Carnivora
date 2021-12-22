package hu.progmatic;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File file = new File("src/main/java/hu/progmatic/Sheet5.csv");

        List<KladLeirasRecord> kladLeirasokInput = deSzerializalKladLeiras(file.getAbsolutePath());
        Carnivora carnivora = new Carnivora();
        carnivora.init();
        carnivora.adatbazisToltKlad(kladTolt());

        kladLeirasokInput.forEach(s -> System.out.println(s));
    }

    private static List<KladRecord> kladTolt() {
        return List.of(
                new KladRecord("állatok világa", null),
                new KladRecord("ragadozók rendje", 1),
                new KladRecord("macskaalkatúak alrendje", 2),
                new KladRecord("madagaszkári cibetmacskafélék családja", 3),
                new KladRecord("macskafélék családja", 3),
                new KladRecord("macskaformák alcsaládja", 5),
                new KladRecord("párducformák alcsaládja", 5),
                new KladRecord("kardfogú macskák alcsaládja – kihalt", 5),
                new KladRecord("mongúzfélék családja", 3),
                new KladRecord("hiénafélék családja", 3),
                new KladRecord("cibetmacskafélék családja", 3),
                new KladRecord("pálmacibetfélék családja", 3),
                new KladRecord("kutyaalkatúak alrendje", 2),
                new KladRecord("macskamedvefélék családja", 13),
                new KladRecord("kutyafélék családja", 13),
                new KladRecord("bűzösborzfélék családja", 13),
                new KladRecord("menyétfélék családja", 13),
                new KladRecord("vidraformák alcsaládja", 17),
                new KladRecord("borzformák alcsaládja", 17),
                new KladRecord("méhészborzformák alcsaládja", 17),
                new KladRecord("amerikai borzformák alcsaládja", 17),
                new KladRecord("menyétformák alcsaládja", 17),
                new KladRecord("mosómedvefélék családja", 13),
                new KladRecord("medvefélék családja", 13),
                new KladRecord("úszólábúak öregcsaládja", 13),
                new KladRecord("rozmárfélék családja", 25),
                new KladRecord("fülesfókafélék családja", 25),
                new KladRecord("valódi fókafélék családja", 25)
        );
    }

    public static List<KladLeirasRecord> deSzerializalKladLeiras(String fileName) {
        List<KladLeirasRecord> kladLeirasok = new ArrayList<>();
        File file = new File(fileName);
        try (Scanner fileScanner = new Scanner(file, StandardCharsets.UTF_8)) {
            fileScanner.nextLine(); //mert az első sor a fejléc
            while (fileScanner.hasNextLine()) {
                kladLeirasok.add(KladLeirasRecord.factory(fileScanner.nextLine()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Nem létező fájl név");
        }
        return kladLeirasok;
    }
}
