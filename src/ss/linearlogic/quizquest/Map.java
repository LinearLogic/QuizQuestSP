package ss.linearlogic.quizquest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import ss.linearlogic.quizquest.entity.Entity;

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
	public static void initialize(String filename) {
		loadMapFile(filename);
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
	
	public static void setCurrentQuadrantX(int quad) {
		int current_temp = current_quadrant_x;
		current_quadrant_x = quad;
		
		if (getCurrentQuadrantX() > getQuadrantWidth()) {
			current_quadrant_x = current_temp;
			return;
		} else if (getCurrentQuadrantX() < 0) {
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
		
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				map[x][y] = textReader.nextInt();
			}
		}
		
		textReader.close();
	}
	
	public static void removeEntity(int x, int y) {
		if (map[x][y] != 0)
			map[x][y] = 0;
		if (entityMap[x][y] != null)
			entityMap[x][y] = null;
	}
	
	//Use method to parse the file and get the objects out, so you know where the objects are
	private static void createObjects() {
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				switch (map[x][y]) {
				case 0:
					break;
				case 1: //This is a door object
					break;
				case 2: //This is a wall object
					break;
				case 3: //This is a roof object (not really needed)
					break;
				}
			}
		}
	}
}
