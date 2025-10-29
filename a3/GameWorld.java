package com.mycompany.a3;

import java.util.Observable;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

public class GameWorld extends Observable {
	
	// Game state
	private int timer = 0;
	private int lives = 3;
	private boolean sound = false;
	private boolean paused = false;
	private boolean position = false;

	// World objects
	private GameObjectCollection gameObjects;
	private int worldWidth;
	private int worldHeight;

	// Sound
	private BGSound backgroundSound;
	private Sound foodSound;
	private Sound flagSound;
	private Sound spiderSound;

	public GameWorld() {}

	// Initialize the world
	public void init() {
		gameObjects = new GameObjectCollection();

		// Flags
		gameObjects.add(new Flag(1, new Point(300, 300), this));
		gameObjects.add(new Flag(2, new Point(300, 800), this));
		gameObjects.add(new Flag(3, new Point(600, 500), this));
		gameObjects.add(new Flag(4, new Point(900, 400), this));

	// Ant (singleton)
		gameObjects.add(Ant.createAnt(new Point(300, 300), this));

		// Spiders
		gameObjects.add(new Spider(worldWidth, worldHeight, this));
		gameObjects.add(new Spider(worldWidth, worldHeight, this));

		// Food Stations
		gameObjects.add(new FoodStation(worldWidth, worldHeight, this));
		gameObjects.add(new FoodStation(worldWidth, worldHeight, this));

		setChanged();
		notifyObservers(this);
	}

	// Accelerate the ant
	public void accelerate() {
		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Ant) {
				((Ant) obj).accelerate();
			}
		}
		setChanged();
		notifyObservers(this);
	}

	// Brake the ant
	public void brake() {
		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Ant) {
				((Ant) obj).brake();
			}
		}
		setChanged();
		notifyObservers(this);
	}

	// Turn left
	public void Left() {
		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Ant) {
				System.out.println("Ant turned left.");
				((Ant) obj).turn(-5);
			}
		}
		setChanged();
		notifyObservers(this);
	}

	// Turn right
	public void Right() {
		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Ant) {
				System.out.println("Ant turned right.");
				((Ant) obj).turn(5);
			}
		}
		setChanged();
		notifyObservers(this);
	}

	// Ant + Flag collision
	public void flagCollision(int sequenceNumber) {
		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Ant) {
				boolean advanced = ((Ant) obj).checkFlagCollision(sequenceNumber);
				if (getSound() && advanced) {
					flagSound.play();
				}
			}
		}
		setChanged();
		notifyObservers(this);
	}

	// Ant + FoodStation collision
	public void foodStationCollision(FoodStation food) {
		System.out.println("Ant hit a food station.");

		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Ant) {
				Ant ant = (Ant) obj;

				ant.setFoodLevel(ant.getFoodLevel() + food.getCapacity());
				food.setCapacity(0);
				food.setColor(ColorUtil.rgb(0, 150, 0));

				// spawn a fresh station
				gameObjects.add(new FoodStation(worldWidth, worldHeight, this));

				if (getSound()) {
					foodSound.play();
				}
			}
		}

		setChanged();
		notifyObservers(this);
	}

	// Ant + Spider collision
	public void spiderCollision() {
		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Ant) {
				((Ant) obj).collisionSpider();
				if (getSound()) {
					spiderSound.play();
				}
			}
		}
		setChanged();
		notifyObservers(this);
	}

	// Clock tick
	public void clockTick() {
		System.out.println("Clock has ticked.");
		timer++;

		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();

			// Ant movement & food
			if (obj instanceof Ant) {
				Ant ant = (Ant) obj;

				ant.modifyFoodLevel(getWidth(), getHeight(), 20);

				// out of food or health -> reset
				if (ant.getFoodLevel() <= 0 || ant.getHealthLevel() <= 0) {
					resetGameObjectCollection();
					return;
				}
			}

			// Spiders move
			if (obj instanceof Spider) {
				((Spider) obj).moveSpider(getWidth(), getHeight(), 20);
			}

			// deselect selectables each tick
			if (obj instanceof ISelectable) {
				((ISelectable) obj).setSelected(false);
			}
		}

		// collisions pass
		checkCollisions();

		// bg music
		if (getSound()) {
			backgroundSound.play();
		}

		setChanged();
		notifyObservers(this);
	}

	// Pairwise collision detection
	public void checkCollisions() {
		IIterator iter1 = gameObjects.getIterator();
		while (iter1.hasNext()) {
			GameObject a = iter1.getNext();

			if (a instanceof Ant) {
				IIterator iter2 = gameObjects.getIterator();
				while (iter2.hasNext()) {
					GameObject b = iter2.getNext();

					if (a != b && a.collidesWith(b)) {
						a.handleCollision(b);
					} else if (a != b && !a.collidesWith(b)) {
						a.removeObject(b);
					}
				}
			}
		}
	}

	// Display current values
	public void displayValues() {
		System.out.println("Current values:");

		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Ant) {
				Ant ant = (Ant) obj;
				System.out.println("Lives left=" + getLives());
				System.out.println("Clock time=" + getTimer());
				System.out.println("Last flag reached=" + ant.getLastFlagReached());
				System.out.println("Ant food level=" + ant.getFoodLevel());
				System.out.println("Ant health=" + ant.getHealthLevel());
			}
		}
	}

	// Display current map
	public void displayMap() {
		System.out.println("Current map:");

		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			System.out.println(it.getNext().toString());
		}
	}

	// Reinitialize world after death; exit if no lives left
	public void resetGameObjectCollection() {
		System.out.println("Ant lost 1 life.");
		setLives(getLives() - 1);

		if (getLives() == 0) {
			System.out.println("Game over, you failed!");
			exit();
		}

		Ant.destroyAnt();
		init();
	}

	// Sounds
	public void createSounds() {
		spiderSound = new Sound("spider.wav");
		foodSound = new Sound("food.wav");
		flagSound = new Sound("flag.wav");
		backgroundSound = new BGSound("background.wav");
	}

	// Ant getters for ScoreView
	public int getLastFlagReached() {
		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Ant) {
				return ((Ant) obj).getLastFlagReached();
			}
		}
		return 1;
	}

	public int getFoodLevel() {
		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Ant) {
				return ((Ant) obj).getFoodLevel();
			}
		}
		return 50;
	}

	public int getHealthLevel() {
		IIterator it = gameObjects.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Ant) {
				return ((Ant) obj).getHealthLevel();
			}
		}
		return 10;
	}

	// Getters / Setters
	public int getLives() { return lives; }
	public void setLives(int lives) { this.lives = lives; }

	public int getTimer() { return timer; }
	public void setTimer(int timer) { this.timer = timer; }

	public boolean getSound() { return sound; }
	public void setSound(boolean sound) {
		this.sound = sound;
		setChanged();
		notifyObservers(this);
	}

	public int getWidth() { return worldWidth; }
	public void setWidth(int width) { this.worldWidth = width; }

	public int getHeight() { return worldHeight; }
	public void setHeight(int height) { this.worldHeight = height; }

	public boolean getPaused() { return paused; }
	public void setPaused(boolean paused) { this.paused = paused; }

	public void pauseSound() {
		if (sound) backgroundSound.pause();
	}

	public void playSound() {
		if (sound) backgroundSound.play();
	}

	public void switchPosition() {
		position = !position;
	}

	public boolean getPosition() { return position; }
	public void setPosition(boolean position) { this.position = position; }

	public IIterator getIterator() {
		return gameObjects.getIterator();
	}

	public void exit() { System.exit(0); }
}
