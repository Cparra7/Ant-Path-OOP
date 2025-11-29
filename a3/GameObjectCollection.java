package com.mycompany.a3;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection {

    // holds all world objects
    private ArrayList<GameObject> gameObjects;

    // init empty list
    public GameObjectCollection() {
        gameObjects = new ArrayList<>();
    }

    // add an object to the collection
    @Override
    public void add(GameObject object) {
        gameObjects.add(object);
    }

    @Override
    public void remove(GameObject obj) {
        gameObjects.remove(obj);
    }

    // return an iterator over the current snapshot
    @Override
    public IIterator getIterator() {
        return new GameObjectCollectionIterator();
    }

    // iterator implementation (index-based)
    private class GameObjectCollectionIterator implements IIterator {
        // start before the first element
        private int index = -1;

        // more elements left?
        @Override
        public boolean hasNext() {
            if (gameObjects.size() <= 0 || index == gameObjects.size() - 1) {
                return false;
            }
            return true;
        }

        // move to next and return it
        @Override
        public GameObject getNext() {
            index += 1;
            return gameObjects.get(index);
        }
    }
}
