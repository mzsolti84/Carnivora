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
            tablaTorleseHaLetezik(connection);
            tablaLetrehozasa(connection);
            veszelyeztettKategoriakBetoltese(connection);
        }
    }

    private void tablaTorleseHaLetezik(Connection connection) {
        connection.createQuery("""
                DROP TABLE if exists klad, klad_leiras, faj, faj_leiras, veszelyeztetett;
                """).executeUpdate();
    }

    private void tablaLetrehozasa(Connection connection) {
        connection.createQuery("""
                create table klad_leiras (
                  ID int auto_increment primary key,
                  nev text not null,
                  latinNev text not null,
                  leiras longtext /*not null*/
                )
                """).executeUpdate();

        connection.createQuery("""
                create table klad (
                  ID int auto_increment primary key,
                  szuloKategoriaID int,
                  kladLeirasID int,
                  nev text /*not null*/,
                  foreign key (szuloKategoriaID) references klad(ID),
                  foreign key (kladLeirasID) references klad_leiras(ID)
                )
                """).executeUpdate();

        connection.createQuery("""
                create table veszelyeztetett (
                  ID int auto_increment primary key,
                  besorolas text not null
                )
                """).executeUpdate();

        connection.createQuery("""
                create table faj_leiras (
                  ID int auto_increment primary key,
                  veszelyeztetettID int,
                  nev text not null,
                  latin_nev text not null,
                  leiras longtext not null,
                  specialista boolean not null,
                  fotoURL text,
                  wikiURL text,
                foreign key (veszelyeztetettID) references veszelyeztetett(ID)
                )
                """).executeUpdate();

        connection.createQuery("""
                create table faj (
                  ID int auto_increment primary key,
                  szuloKategoriaID int not null,
                  fajLeirasID int not null,
                  nev text not null,
                foreign key (szuloKategoriaID) references klad(ID),
                foreign key (fajLeirasID) references faj_leiras(ID)
                )
                """).executeUpdate();
    }

  private void veszelyeztettKategoriakBetoltese(Connection connection) {
    connection.createQuery("""
        insert into veszelyeztetett (besorolas) values
         ('Kihalt'),
         ('Vadon kihalt'),
         ('Fenyegetett'),
         ('Súlyosan veszélyeztetett'),
         ('Veszélyeztetett'),
         ('Sebezhető'),
         ('Mérsékelten fenyegetett'),
         ('Nem fenyegetett'),
         ('Háziasított')
        """).executeUpdate();
  }

    public void adatbazisToltKlad(List<KladRecord> lista) {
        try (Connection connection = adatbazis.open()) {
            for (KladRecord kladRecord : lista) {
                connection.createQuery("""
                                insert into klad (szuloKategoriaID,  /*kladLeirasID,*/ nev) values (:szuloKategoriaID,  /*:kladLeirasID,*/ :nev)
                                """)
                        .addParameter("szuloKategoriaID", kladRecord.szuloKategoriaID)
                        //.addParameter("kladLeirasID", kladRecord.kladLeirasID)
                        .addParameter("nev", kladRecord.nev)
                        .executeUpdate();
            }
        }
    }

    public void adatbazisToltKladLeiras(List<KladLeirasRecord> lista) {
        try (Connection connection = adatbazis.open()) {
            for (KladLeirasRecord kladLeirasRecord : lista) {
                connection.createQuery("""
                                insert into klad_leiras (nev, latinNev, leiras) values (:nev, :latinNev, :leiras)
                                """)
                        .addParameter("nev", kladLeirasRecord.nev)
                        .addParameter("latinNev", kladLeirasRecord.latinNev)
                        .addParameter("leiras", kladLeirasRecord.leiras)
                        .executeUpdate();
            }
        }
    }
}
