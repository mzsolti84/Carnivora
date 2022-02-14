package hu.progmatic.carnivora;

import java.util.Arrays;

public enum Tureshatar {
    GENERALISTA("Generalista"),
    SPECIALISTA("Specialista");

    private String displayName;

    Tureshatar(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Tureshatar getFromString(String text) {
        return Arrays
                .stream(Tureshatar.values())
                .filter(tureshatar -> tureshatar.displayName.equalsIgnoreCase(text))
                .findFirst().orElse(null);
    }
}
