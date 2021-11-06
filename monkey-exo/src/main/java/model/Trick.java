package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Trick {

    public static final String MARCHER_SUR_MAINS = "marcher sur les mains",
            TENIR_EQUILIBRE_BALLE = "tenir en équilibre sur une balle",
            SAUTER_CERCLE_FEU = "sauter à travers un cercle en feu",
            JOUER_TAMBOUR = "jouer du tambour",
            JOUER_FLUTE = "jouer de la flute",
            JOUER_HARMONICA = "jouer de l'harmonica";

    private String name;

    private TrickType type;

    public Trick(String name, TrickType type) {
        this.name = name;
        this.type = type;
    }

    public String getLabel() {

        return String.format("%s '%s'", this.type.label(), this.name);
    }

}
