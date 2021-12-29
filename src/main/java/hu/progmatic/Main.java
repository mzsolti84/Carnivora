package hu.progmatic;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File fileKlad = new File("src/main/java/hu/progmatic/SheetKlad.csv");
        File fileFaj = new File("src/main/java/hu/progmatic/SheetFaj.csv");

        List<KladRecord> kladLeirasokInput = deSzerializalKlad(fileKlad.getAbsolutePath());
        List<FajRecord> fajLeirasokInput = deSzerializalFaj(fileFaj.getAbsolutePath());
        Carnivora carnivora = new Carnivora();
        carnivora.init();
        carnivora.adatbazisToltKlad(deSzerializalKlad(fileKlad.getAbsolutePath()));
        carnivora.adatbazisToltFaj(deSzerializalFaj(fileFaj.getAbsolutePath()));

        kladLeirasokInput.forEach(System.out::println);
        fajLeirasokInput.forEach(System.out::println);
    }

    public static List<KladRecord> deSzerializalKlad(String fileName) {
        List<KladRecord> kladLeirasok = new ArrayList<>();
        File file = new File(fileName);
        try (Scanner fileScanner = new Scanner(file, StandardCharsets.UTF_8)) {
            fileScanner.nextLine(); //mert az első sor a fejléc
            while (fileScanner.hasNextLine()) {
                kladLeirasok.add(KladRecord.factory(fileScanner.nextLine()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Nem létező fájl név");
        }
        return kladLeirasok;
    }

    public static List<FajRecord> deSzerializalFaj(String fileName) {
        List<FajRecord> fajLeirasok = new ArrayList<>();
        File file = new File(fileName);
        try (Scanner fileScanner = new Scanner(file, StandardCharsets.UTF_8)) {
            fileScanner.nextLine(); //mert az első sor a fejléc
            while (fileScanner.hasNextLine()) {
                fajLeirasok.add(FajRecord.factory(fileScanner.nextLine()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Nem létező fájl név");
        }
        return fajLeirasok;
    }
}
