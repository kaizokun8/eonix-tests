import model.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Spectator spectator = new Spectator("John");

        Trick trickA = new Trick("marcher sur les mains", TrickType.ACROBATIC);

        Trick trickB = new Trick("tenir en équilibre sur une balle", TrickType.ACROBATIC);

        Trick trickC = new Trick("sauter à travers un cercle en feu", TrickType.ACROBATIC);

        Trick trickD = new Trick("jouer du tambour", TrickType.MUSICAL);

        Trick trickE = new Trick("jouer de la flute", TrickType.MUSICAL);

        Trick trickF = new Trick("jouer de l'harmonica", TrickType.MUSICAL);

        Monkey monkeyA = new Monkey("Abu", List.of(trickA, trickB, trickD));

        Monkey monkeyB = new Monkey("Aldo", List.of(trickC, trickE, trickF));

        Trainer trainerA = new Trainer(monkeyA);

        Trainer trainerB = new Trainer(monkeyB);

        List.of(trainerA, trainerB).forEach(trainer -> trainer.start(spectator));
    }
}
