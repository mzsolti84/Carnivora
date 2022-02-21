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

}
