package com.mycompany.a2;

import java.util.ArrayList;

/**
 * GameObjectCollection
 * - Stores all GameObjects in the world.
 * - Provides add() and an iterator to loop through them.
 */
public class GameObjectCollection implements ICollection {

    private ArrayList<GameObject> gameObjects;

    public GameObjectCollection() {
        gameObjects = new ArrayList<>();
    }

    // Adds a new GameObject to the collection
    @Override
    public void add(GameObject obj) {
        gameObjects.add(obj);
    }

    // Returns an iterator for looping through all objects
    @Override
    public IIterator getIterator() {
        return new GameObjectIterator();
    }

    // Inner iterator class
    private class GameObjectIterator implements IIterator {
        private int index = -1;

        @Override
        public boolean hasNext() {
            return (gameObjects.size() > 0 && index < gameObjects.size() - 1);
        }

        @Override
        public GameObject getNext() {
            index++;
            return gameObjects.get(index);
        }
    }
}
