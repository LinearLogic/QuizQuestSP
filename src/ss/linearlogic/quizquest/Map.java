package ss.linearlogic.quizquest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import ss.linearlogic.quizquest.entity.Door;
import ss.linearlogic.quizquest.entity.Enemy;
import ss.linearlogic.quizquest.entity.Entity;
import ss.linearlogic.quizquest.entity.Floor;
import ss.linearlogic.quizquest.entity.Grass;
import ss.linearlogic.quizquest.entity.Wall;
import ss.linearlogic.quizquest.item.Item;

public class Map {
	private static int map[][];
	private static Entity entityMap[][];
	
	//Dimensions of the map
	private static int width;
	private static int height;
	
	private static int quadrant_count_width = 3;
	private static int current_quadrant_x = 0;
	private static int current_quadrant_y = 0;
	
	private static HashMap<Integer, Texture> textures = new HashMap<Integer, Texture>(); //Hashmap of all the textures
	
	private static int tileSize; // Size of the tile
	
	public Map(String filename) {}
	
	//Initialize the map class
	public static void initialize(String filename/*, String entityFilename*/) {
 		loadMapFile(filename);
// 		loadEnemyEntityFile(entityFilename);
	}

	
	//Render the current map
	public static void render() {
		//Get the players position
		int start_coordinate_x = getCurrentQuadrantX() * 15;
		int start_coordinate_y = getCurrentQuadrantY() * 15;
		
		//Get the coordinate shift to keep rendering inside the camera
		int coordinate_shift_x = getCoordinateShiftX();
		int coordinate_shift_y = getCoordinateShiftY();
				
		for (int x = start_coordinate_x; x < (start_coordinate_x + 15); ++x) {
			for (int y = start_coordinate_y; y < (start_coordinate_y + 15); ++y) {
				//2 Layers of rendering, the base layer being the grass and the top layer being the objects like doors and walls, etc.
				Renderer.renderTexturedRectangle((x * tileSize) - coordinate_shift_x, (y * tileSize) - coordinate_shift_y, tileSize, tileSize, textures.get(0));
				
				if (map[x][y] == 0)
					continue;
				
				//Render the top layer such as walls and doors
				Renderer.renderTexturedRectangle((x * tileSize) - coordinate_shift_x, (y * tileSize) - coordinate_shift_y, tileSize, tileSize, textures.get(map[x][y]));
			}
		}
	}
	
	public static int getWidth() { return width; }
	
	public static int getHeight() { return height; }
	public static void setCurrentQuadrantX(int quad) {
		int current_temp = current_quadrant_x;
		current_quadrant_x = quad;
		
		if ((getCurrentQuadrantX() < 0) || (getCurrentQuadrantX() > getQuadrantWidth())) {
			current_quadrant_x = current_temp;
			return;
		}
	}
	
	public static void setCurrentQuadrantY(int quad) {
		int current_temp = current_quadrant_y;
		current_quadrant_y = quad;
		
		if ((getCurrentQuadrantY() < 0) || getCurrentQuadrantY() > getQuadrantWidth()) {
			current_quadrant_y = current_temp;
			return;
		}
	}
	
	//Get the current quadrant in the x index
	public static int getCurrentQuadrantX() {
		return (int) current_quadrant_x;
	}
	
	//Get the current quadrant in the y index
	public static int getCurrentQuadrantY() {
		return (int) current_quadrant_y;
	}
	
	//Get the quadrant width
	public static int getQuadrantWidth() {
		return quadrant_count_width;
	}
	
	//Get the coordinate shift to the X
	public static int getCoordinateShiftX() {
		return (int)(getCurrentQuadrantX() * 480);
	}
	
	//Get the coordinate shift to the Y
	public static int getCoordinateShiftY() {
		return (int)(getCurrentQuadrantY() * 480);
	}
	
	//Returns the tilesize (in pixels)
	public static int getTileSize() { return tileSize; }
	
	//Add a texture to the textures hashmap
	public static void addTexture(String filename, int GID) {
		Texture texture = null;
		
		//Read the texture object from the string
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Put the tile into the textures HashMap
		if (texture != null) textures.put(GID, texture);
		else System.out.println("Texture is null, unable to add to array");
	}
	
	//Load map file
	private static void loadMapFile(String filename) { 
		Scanner textReader = null;
		
		//Read the file from the filesystem
		try {
			textReader = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		width = textReader.nextInt();
		height = textReader.nextInt();
		
		//The next two ints are only required by the map editor
		quadrant_count_width = textReader.nextInt();
		textReader.nextInt();
				
		tileSize = textReader.nextInt();
		
		map = new int[width][height];
		entityMap = new Entity[width][height];
		
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				int typeID = textReader.nextInt();
				switch(typeID) {
					default:
					case 0: //Grass
						addEntity(new Grass(x, y));
						break;
					case 1: //Door
						addEntity(new Floor(x, y));
						break;
					case 2: //Wall
						addEntity(new Wall(x, y));
						break;
					case 3: //Door
						addEntity(new Door(1, x, y));
						break;
					case 4: //Enemy
						addEntity(new Enemy(4, 20, x, y));
						break;
				}
			}
		}
		textReader.close();
	}
	
	public static void addEntity(Entity entity) {
		int x = entity.getX();
		int y = entity.getY();
		map[x][y] = entity.getTypeID();
		entityMap[x][y] = entity;
	}
	public static void removeEntity(int x, int y) {
		if (map[x][y] != 0)
			map[x][y] = 0;
		if (entityMap[x][y] != null)
			entityMap[x][y] = null;
	}
	
	public static Entity getEntity(int x, int y) {
		return entityMap[x][y];
	}
	
	//Do not touch this for the time being
	/*
	private static void loadEnemyEntityFile(String filename) {
		Scanner textReader = null;
		
		try {
			textReader = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int enemyEntityCount = textReader.nextInt();
		System.out.println(enemyEntityCount);
		for (int i = 0; i < enemyEntityCount; ++i) {
			int maxHealth = textReader.nextInt();
			int damage = textReader.nextInt();
			int itemToDrop = textReader.nextInt();
			Item item = null;
			if (itemToDrop != 0)
				item = new Item(itemToDrop, 1);
			int x = textReader.nextInt();
			int y = textReader.nextInt();
			addEntity(new Enemy(maxHealth, damage, item, x, y));
		}
		
		textReader.close();
	}*/

}
