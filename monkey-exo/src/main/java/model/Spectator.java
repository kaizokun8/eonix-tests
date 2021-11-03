package model;

import lombok.Getter;
import lombok.Setter;
import pattern.Observator;

@Getter
@Setter
public class Spectator implements Observator {

    private String name;

    public Spectator(String name) {
        this.name = name;
    }

    @Override
    public void notify(Trick trick) {

        switch (trick.getType()) {
            case MUSICAL:
                this.whistles(trick.getLabel());
                break;
            case ACROBATIC:
                this.applauds(trick.getLabel());
                break;

        }
    }

    private void applauds(String label) {

        System.out.println(String.format(" %s applaudit pendant le %s", this.name, label));
    }

    private void whistles(String label) {

        System.out.println(String.format(" %s siffle pendant le %s", this.name, label));
    }

}
