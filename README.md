# Minta repó saját projektekhez
## Használat
* https://github.com/progmatic-java/oop-template
* `Use this template` gombra kattintás
* Repozitory nevének megadása
* `Create repository from template` gombra kattintás
* `Code` / clone..
* Az idea elvileg felismeri, ha le tudod futtatni a `HelloWorldTest`-et, akkor működik is
* Amennyiben nem, akkor a `pom.xml`-en jobb klikk, majd `Add as maven project`

## Javaslatok, konvenciók
* Ami nem kell, töröljétek nyugodtan
* Tartsátok karban a README-t, legalább annyira hogy a projekt célja le legyen írva benne
* Saját projekteknél nem kötelező a TDD módszertan használata, de ha úgy érzitek hogy segít, akkor bármelyik projektnél vagy házinál alkalmazhatjátok
* Ha nem TDD-ztek, akkor is nyugodtan írjatok egy-egy tesztet, amennyiben az segíti a fejlesztést vagy a hibakeresést - vagy csak megnyugtat hogy tudjátok hogy jó az adott függvény / osztály
## Tartalom
### `.gitignore`
Itt lehet beállítani, hogy mely fájlokat nem akarjuk feltölteni.
### `pom.xml`
A maven leíró fájl, ez a projekt összeállítását segíti.
Erről fogunk részletesebben is tanulni a spring résznél,
ott már szükségetek lesz a megértésére is, addig csak
örüljetek hogy nem kell vele foglalkozni :)
### `src/main/java/hu/progmatic/Main.java`
Futtatható minta osztály, `main` metódussal
### `src/main/java/hu/progmatic/HelloWorld.java`
Minta osztály, teszttel.
### `src/test/java/hu/progmatic/HelloWorldTest.java`
Minta teszt.