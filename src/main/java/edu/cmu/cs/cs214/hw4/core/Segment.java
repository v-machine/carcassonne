package edu.cmu.cs.cs214.hw4.core;

public class Segment {
    private Feature featurePointer = null;
    private final Type type;
    public enum Type {
        FIELD,
        ROAD,
        CITY
    }

    /**
     * Constructor for the segment class
     * @param t Segment.Type
     */
    Segment(Type t) {
        type = t;
    }

    /**
     * Method for linking segment to features they are a part of
     * @param f  the feature consisting of segment
     */
    void linkFeature(Feature f) {
        featurePointer = f;
    }

    /**
     * Getter of pointer to linked features
     * @return featurePointer
     */
    Feature getLinkedFeature() {
        return featurePointer;
    }

    /**
     * Override Object equals method
     * @param o  Object to compare against
     * @return boolean as the result of comparison
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Segment)) { return false; }
        Segment s = (Segment) o;
        return (type.equals(s.type));
    }

    /**
     * Override Object hashCode method
     * @return hash code of the Segment
     */
    @Override
    public int hashCode() {
        int PRIME = 31;
        return PRIME + type.hashCode();
    }

    /**
     * Override toString method
     * @return  string representation of segment
     */
    @Override
    public String toString() {
        return type.toString();
    }
}
