package com.mycompany.a2;

public interface IIterator {

    // Returns true if another object exists in the collection
    boolean hasNext();

    // Returns the next GameObject
    GameObject getNext();
}
