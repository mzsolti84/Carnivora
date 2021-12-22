package hu.progmatic;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.*;

public class Carnivora {
    public final Sql2o adatbazis = new Sql2o(
            "jdbc:mysql://localhost:3306/progmatic",
            "progmatic",
            "progmatic"
    );

    public void init() {
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
                      leiras longtext not null,
                      veszelyeztetett_besorolas text not null,
                      specialista boolean not null,
                      fotoURL text,
                      wikiURL text,
                    foreign key (szuloKategoriaID) references klad(ID)
                    )
                    """).executeUpdate();
        }
    }

    public void adatbazisToltKlad(List<KladRecord> lista) {
        try (Connection connection = adatbazis.open()) {
            for (KladRecord kladRecord : lista) {
                connection.createQuery("""
                                INSERT INTO klad (szuloKategoriaID, nev, latinNev, leiras)
                                VALUES (:szuloKategoriaID, :nev, :latinNev, :leiras)
                                """)
                        .addParameter("szuloKategoriaID", kladRecord.szuloKategoriaID)
                        .addParameter("nev", kladRecord.nev)
                        .addParameter("latinNev", kladRecord.latinNev)
                        .addParameter("leiras", kladRecord.leiras)
                        .executeUpdate();
            }
        }
    }
}
