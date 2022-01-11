package hu.progmatic;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Carnivora carnivora = new Carnivora();
        carnivora.initDatabaseTables();
        carnivora.adatbazisToltKlad();
        carnivora.adatbazisToltFaj();
        Carnivora.deSzerializalKlad().forEach(System.out::println);
        Carnivora.deSzerializalFaj().forEach(System.out::println);
        szerializalKladRecord();
        deSzerializalKladRecord();
    }

    private static void szerializalKladRecord() {
        Gson gson = new Gson();

        KladRecord mintaRecord = new KladRecord(
                2,
                1,
                "Kutyaalkatúak",
                "",
                "(üres)"
        );

        String json = gson.toJson(mintaRecord);
    }

    private static void deSzerializalKladRecord() {
        Gson gson = new Gson();
        String kladJson = "{'nev':'Kutyaalkatúak','leiras':'(üres)','Id':2,'szuloKategoriaId':2,'latinNev':''}";
        CharSequence kladChar = "{'nev':'Kutyaalkatúak','leiras':'(üres)','Id':2,'szuloKategoriaId':2,'latinNev':''}";

        KladRecord teszt = gson.fromJson(kladJson, KladRecord.class);
    }
}
