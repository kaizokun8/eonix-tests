
import model.Monkey;
import model.Spectator;
import model.Trick;
import model.TrickType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SpectatorTest {

    //https://www.baeldung.com/java-testing-system-out-println
    //sauvegarde la sortie standart
    private final PrintStream standardOut = System.out;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        //modifie la sortie pour le test
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        //reaffecte la sortie standart
        System.setOut(standardOut);
    }

    @Test
    public void givenAMusicalTrick_whenASpectatorIsNotified_shouldPrintWhistles() {

        //given
        Trick trick = new Trick(Trick.JOUER_FLUTE, TrickType.MUSICAL);

        Spectator spectator = new Spectator("John");

        String spectatorReaction = Spectator.whistles(spectator.getName(), trick.getLabel()).trim();

        //when
        spectator.notify(trick);

        //then
        Assertions.assertThat(spectatorReaction).isEqualTo(outputStreamCaptor.toString().trim());
    }

    @Test
    public void givenAnAcrobaticTrick_whenASpectatorIsNotified_shouldPrintApplauds() {

        //given
        Trick trick = new Trick(Trick.TENIR_EQUILIBRE_BALLE, TrickType.ACROBATIC);

        Spectator spectator = new Spectator("John");

        String spectatorReaction = Spectator.applauds(spectator.getName(), trick.getLabel()).trim();

        //when
        spectator.notify(trick);

        //then
        Assertions.assertThat(spectatorReaction).isEqualTo(outputStreamCaptor.toString().trim());
    }

    /*
     * Mock le spectator pour verifier si sa methode notify est bien appellée lorsque le monkey execute un tour.
     * */
    @Test
    public void givenAMonkeyWithTricksAndASpectator_whenMonkeyDoTricks_SpectatorShouldBeNotifiedAtEachTrick() {

        //given
        Spectator spectator = Mockito.mock(Spectator.class);
        //on ne verifie que l'appel de la méthode
        doNothing().when(spectator).notify(any(Trick.class));

        ArgumentCaptor<Trick> trickArgumentCaptor = ArgumentCaptor.forClass(Trick.class);

        Collection<Trick> tricks = List.of(
                new Trick(Trick.JOUER_FLUTE, TrickType.MUSICAL),
                new Trick(Trick.JOUER_HARMONICA, TrickType.MUSICAL),
                new Trick(Trick.TENIR_EQUILIBRE_BALLE, TrickType.ACROBATIC));

        Monkey monkey = new Monkey("", tricks);

        monkey.addObservator(spectator);
        //when
        monkey.doTricks();
        //then
        //verifie que la méthode notify est appellée autant de fois qu'il y a de tours
        Mockito.verify(spectator, times(tricks.size())).notify(trickArgumentCaptor.capture());
        //récupère la liste des arguments passé à la methode notify du mock spectator
        List<Trick> trickList = trickArgumentCaptor.getAllValues();
        //verifie que la liste d'argument passé à notify correspond à la liste de tour
        Assertions.assertThat(trickList).containsAll(tricks);
    }

}
