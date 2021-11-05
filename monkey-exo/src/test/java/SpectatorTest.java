
import model.Spectator;
import model.Trick;
import model.TrickType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SpectatorTest {

    //https://www.baeldung.com/java-testing-system-out-println
    private final PrintStream standardOut = System.out;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void givenAMusicalTrick_whenASpectatorIsNotified_shouldPrintWhistle() {

        //given

        Trick trick = new Trick("jouer de la flute", TrickType.MUSICAL);

        Spectator spectator = new Spectator("John");

        String spectatorReaction = Spectator.whistles(spectator.getName(), trick.getLabel()).trim();

        //when
        spectator.notify(trick);

        //then
        Assertions.assertThat(spectatorReaction).isEqualTo(outputStreamCaptor.toString().trim());

    }
}
