package hu.progmatic.carnivora;

import java.util.Arrays;

public enum TermeszetvedelmiStatusz {
    KIHALT("Kihalt"),
    VADON_KIHALT("Vadon kihalt"),
    FENYEGETETT("Fenyegetett"),
    SULYOSAN_VESZELYEZTETETT("Súlyosan veszélyeztetett"),
    VESZELYEZTETETT("Veszélyeztetett"),
    SEBEZHETO("Sebezhető"),
    MERSEKELTEN_FENYEGETETT("Mérsékelten fenyegetett"),
    NEM_FENYEGETETT("Nem fenyegetett"),
    HAZIASITOTT("Háziasított");

    private String displayName;

    TermeszetvedelmiStatusz(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
