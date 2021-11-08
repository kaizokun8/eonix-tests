package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Trainer {

    private Monkey monkey;

    public Trainer(Monkey monkey) {
        this.monkey = monkey;
    }

    /**
     * Add the spectator to the observers list, make the monkey execute his tricks,
     * then remove the spectator from the observers .
     *
     * @param spectator a spectator to observe the monkey tricks
     */
    public void start(Spectator spectator) {

        this.monkey.addObservator(spectator);

        this.monkey.doTricks();

        this.monkey.removeObservator(spectator);
    }
}
