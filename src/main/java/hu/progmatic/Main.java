package hu.progmatic;

import com.google.gson.Gson;
import hu.progmatic.klad.KladService;

public class Main {
    public static void main(String[] args) {
        Carnivora carnivora = new Carnivora();
        carnivora.initDatabaseTables();
        carnivora.adatbazisToltKlad();
        carnivora.adatbazisToltFaj();
        Carnivora.deSzerializalKlad().forEach(System.out::println);
        Carnivora.deSzerializalFaj().forEach(System.out::println);
    }
}
