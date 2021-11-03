package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trick {

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
