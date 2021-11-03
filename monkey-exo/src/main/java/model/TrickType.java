package model;

import java.util.HashMap;
import java.util.Map;

public enum TrickType {

    ACROBATIC, MUSICAL;

    private static Map<TrickType, String> labels;

    static {
        labels = new HashMap<>();
        labels.put(ACROBATIC, "tour d'acrobatie");
        labels.put(MUSICAL, "tour musicale");
    }

    public String label() {

        return labels.get(this);
    }
}
