package hu.progmatic;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Carnivora carnivora = new Carnivora();
        carnivora.init();
        carnivora.adatbazisToltKlad();
        carnivora.adatbazisToltFaj();
        Carnivora.deSzerializalKlad().forEach(System.out::println);
        Carnivora.deSzerializalFaj().forEach(System.out::println);
    }
}
