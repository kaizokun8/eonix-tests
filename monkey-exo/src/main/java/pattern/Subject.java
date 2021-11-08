package pattern;

public interface Subject {

    /**
     * Add an observator to the subject list
     *
     * @param observator an observator implementing the Observator interface
     */
    void addObservator(Observator observator);

    /**
     * Remove an observator to the subject list
     *
     * @param observator an observator implementing the Observator interface
     */
    void removeObservator(Observator observator);
}
