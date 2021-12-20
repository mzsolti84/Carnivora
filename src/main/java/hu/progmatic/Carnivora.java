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
    }
  }

  private void tablaTorleseHaLetezik(Connection connection) {
    Query query = connection.createQuery("""
            DROP TABLE if exists klad, klad_leiras, faj, faj_leiras;
            """);
    query.executeUpdate();
  }

  private void tablaLetrehozasa(Connection connection) {
     connection.createQuery("""
        create table klad (
          ID int auto_increment primary key,
          szuloKategoriaID int,
          nev text,
        foreign key (szuloKategoriaID) references klad(ID)
        )
        """).executeUpdate();

    connection.createQuery("""
        create table klad_leiras (
          ID int auto_increment primary key,
          nev text not null,
          latin_nev text not null,
          leiras longtext not null,
        foreign key (ID) references klad(ID)
        )
        """).executeUpdate();

    connection.createQuery("""
        create table faj (
          ID int auto_increment primary key,
          szuloKategoriaID int,
          nev text,
        foreign key (szuloKategoriaID) references klad(ID)
        )
        """).executeUpdate();

    connection.createQuery("""
        create table faj_leiras (
          ID int auto_increment primary key,
          nev text not null,
          latin_nev text not null,
          leiras longtext not null,
          specialista int not null,
          fotoURL text not null,
          wikiURL text,
        foreign key (ID) references faj(ID)
        )
        """).executeUpdate();
  }
}
