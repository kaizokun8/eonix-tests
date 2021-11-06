import model.*;

import java.util.List;

import static model.Trick.*;

public class Main {

    public static void main(String[] args) {

        Spectator spectator = new Spectator("John");

        Trick trickA = new Trick(MARCHER_SUR_MAINS, TrickType.ACROBATIC);

        Trick trickB = new Trick(TENIR_EQUILIBRE_BALLE, TrickType.ACROBATIC);

        Trick trickC = new Trick(SAUTER_CERCLE_FEU, TrickType.ACROBATIC);

        Trick trickD = new Trick(JOUER_TAMBOUR, TrickType.MUSICAL);

        Trick trickE = new Trick(JOUER_FLUTE, TrickType.MUSICAL);

        Trick trickF = new Trick(JOUER_HARMONICA, TrickType.MUSICAL);

        Monkey monkeyA = new Monkey("Abu", List.of(trickA, trickB, trickD));

        Monkey monkeyB = new Monkey("Aldo", List.of(trickC, trickE, trickF));

        Trainer trainerA = new Trainer(monkeyA);

        Trainer trainerB = new Trainer(monkeyB);

        List.of(trainerA, trainerB).forEach(trainer -> trainer.start(spectator));
    }
}
