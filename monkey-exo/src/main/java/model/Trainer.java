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

    public void start(Spectator spectator) {

        this.monkey.addObservator(spectator);

        this.monkey.doTricks();

        this.monkey.removeObservator(spectator);
    }
}
