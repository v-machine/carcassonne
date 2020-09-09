package edu.cmu.cs.cs214.hw4.core;

import java.util.Objects;

/**
 * Pointer provides pointer-like functionality. A pointer can either reference
 * an object, or reference another pointer. Upon reference retrieval, teh pointer
 * will return the object reference from its next pointer until its next pointer
 * is null, in which case the last pointer returns its stored object reference.
 */
public class Pointer {
    private Pointer next = null;
    private Object reference;

    /**
     * Constructor of the pointer object. Upon initiation the pointer stores
     * a reference to an non-pointer object.
     * @param reference a non-pointer object reference
     * @throws InstantiationException  if a pointer is passed into param
     */
    public Pointer(Object reference) throws InstantiationException {
        if (reference instanceof Pointer){
            throw new InstantiationException("Pointer cannot be instantiated " +
                                             "with a reference to a pointer");
        }
        this.reference = reference;
    }

    /**
     * Returns the reference from the next linked pointer or returns its
     * own object reference if it does not point to the next pointer
     * @return  reference from the next pointer or its own reference
     */
    public Object getReference(){
        try {
            return next.getReference();
        } catch (NullPointerException e){
            return reference;
        }
    }

    /**
     * Make pointer point to a new object and nullify its
     * current object reference.
     */
    public void pointTo(Pointer other) {
        next = other;
        reference = null;
    }
}
