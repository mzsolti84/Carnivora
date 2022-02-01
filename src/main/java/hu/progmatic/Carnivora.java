package hu.progmatic;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

public class Carnivora {
    public final Sql2o adatbazis = new Sql2o(
            "jdbc:mysql://localhost:3306/progmatic",
            "progmatic",
            "progmatic"
    );

    public void initDatabaseTables() {
        try (Connection connection = adatbazis.open()) {
            connection.createQuery("""
                    DROP TABLE if exists klad, faj;
                    """).executeUpdate();

            connection.createQuery("""
                    CREATE TABLE klad (
                      ID int auto_increment primary key,
                      szuloKategoriaID int,
                      nev text,
                      latinNev text,
                      leiras longtext,
                      foreign key (szuloKategoriaID) references klad(ID)
                    )
                    """).executeUpdate();

            connection.createQuery("""
                    CREATE TABLE faj (
                      ID int auto_increment primary key,
                      szuloKategoriaID int not null,
                      nev text not null,
                      latinNev text not null,
                      leiras longtext,
                      veszelyeztetett_besorolas text not null,
                      specialista boolean not null,
                      fotoURL text,
                      wikiURL text,
                    foreign key (szuloKategoriaID) references klad(ID)
                    )
                    """).executeUpdate();
        }
    }

    public void adatbazisToltKlad() {
        try (Connection connection = adatbazis.open()) {
            for (KladRecord kladRecord : deSzerializalKlad()) {
                connection.createQuery("""
                                INSERT INTO klad (szuloKategoriaID, nev, latinNev, leiras)
                                VALUES (:szuloKategoriaID, :nev, :latinNev, :leiras)
                                """)
                        .addParameter("szuloKategoriaID", kladRecord.szuloKategoriaId)
                        .addParameter("nev", kladRecord.nev)
                        .addParameter("latinNev", kladRecord.latinNev)
                        .addParameter("leiras", kladRecord.leiras)
                        .executeUpdate();
            }
        }
    }

    public void adatbazisToltFaj() {
        try (Connection connection = adatbazis.open()) {
            for (FajRecordRegi fajRecordRegi : deSzerializalFaj()) {
                connection.createQuery("""
                                INSERT INTO faj (szuloKategoriaID, nev, latinNev, leiras, veszelyeztetett_besorolas, specialista, fotoURL, wikiURL)
                                VALUES (:szuloKategoriaID, :nev, :latinNev, :leiras, :veszelyeztetett_besorolas, :specialista, :fotoURL, :wikiURL)
                                """)
                        .addParameter("szuloKategoriaID", fajRecordRegi.szuloKategoriaID)
                        .addParameter("nev", fajRecordRegi.nev)
                        .addParameter("latinNev", fajRecordRegi.latinNev)
                        .addParameter("leiras", fajRecordRegi.leiras)
                        .addParameter("veszelyeztetett_besorolas", fajRecordRegi.veszelyeztetettBesorolas)
                        .addParameter("specialista", fajRecordRegi.specialista)
                        .addParameter("fotoURL", fajRecordRegi.fotoURL)
                        .addParameter("wikiURL", fajRecordRegi.wikiURL)
                        .executeUpdate();
            }
        }
    }

    public static List<KladRecord> deSzerializalKlad() {
        File fileKlad = new File("src/main/java/hu/progmatic/SheetKlad.csv");
        List<KladRecord> kladLeirasok = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(Path.of(fileKlad.getAbsolutePath()), StandardCharsets.UTF_8)) {
            fileScanner.nextLine(); //mert az első sor a fejléc
            while (fileScanner.hasNextLine()) {
                kladLeirasok.add(KladRecord.factory(fileScanner.nextLine()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Nem létező fájl név: " + fileKlad.getName());
        }
        return kladLeirasok;
    }

    public static List<FajRecordRegi> deSzerializalFaj() {
        File fileFaj = new File("src/main/java/hu/progmatic/SheetFaj.csv");
        List<FajRecordRegi> fajLeirasok = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(Path.of(fileFaj.getAbsolutePath()), StandardCharsets.UTF_8)) {
            fileScanner.nextLine(); //mert az első sor a fejléc
            while (fileScanner.hasNextLine()) {
                fajLeirasok.add(FajRecordRegi.factory(fileScanner.nextLine()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Nem létező fájl név: " + fileFaj.getName());
        }
        return fajLeirasok;
    }

}