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

        String reaction = null;

        switch (trick.getType()) {
            case MUSICAL:
                reaction = whistles(this.name, trick.getLabel());
                break;
            case ACROBATIC:
                reaction = applauds(this.name, trick.getLabel());
                break;
            default:
        }

        if (reaction != null) {

            System.out.println(reaction);
        }

    }

    public String getName() {
        return name;
    }

    /**
     * returns the applause of the spectator
     *
     * @param name  the name of the spectator
     * @param label the label of the trick
     */
    public static String applauds(String name, String label) {

        return String.format("%s applaudit pendant le %s", name, label);
    }

    /**
     * returns the whistles of the spectator
     *
     * @param name  the name of the spectator
     * @param label the label of the trick
     */
    public static String whistles(String name, String label) {

        return String.format("%s siffle pendant le %s", name, label);
    }
}
