package model;

import lombok.Getter;
import lombok.Setter;
import pattern.Observator;
import pattern.Subject;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class Monkey implements Subject {

    private String name;

    private Collection<Trick> tricks;

    private List<Observator> observators = new LinkedList<>();

    private Map<String, Trick> tricksMap;

    public Monkey(String name, Collection<Trick> tricks) {

        this.name = name;

        this.tricks = tricks;

        this.tricksMap = tricks.stream().collect(Collectors.toMap(Trick::getName, Function.identity()));
    }

    /**
     * Execute a single trick and notify the observators
     * @param name the name of the trick
     */
    public void doTrick(String name) {

        this.observators.forEach(obs -> obs.notify(this.tricksMap.get(name)));
    }

    /**
     * Execute every monkey tricks and notify the observators.
     */
    public void doTricks() {

        this.tricks.forEach(trick -> this.observators.forEach(obs -> obs.notify(trick)));
    }

    @Override
    public void addObservator(Observator observator) {
        this.observators.add(observator);
    }

    @Override
    public void removeObservator(Observator observator) {
        this.observators.remove(observator);
    }
}
