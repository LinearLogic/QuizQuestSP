package ss.linearlogic.quizquest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import ss.linearlogic.quizquest.entity.Player;

public class Map {
	private static int map[][];
	
	//Dimensions of the map
	private static int width;
	private static int height;
	
	private static final int quadrant_count = 9;
	private static int current_quadrant = 0;
	
	private static HashMap<Integer, Texture> textures = new HashMap(); //Hashmap of all the textures
	
	private static int tileSize; // Size of the tile
	
	public Map(String filename) {}
	
	//Initialize the map class
	public static void Initialize(String filename) {
		LoadMapFile(filename);
	}
	
	//Render the current map
	public static void Render() {
		//Get the players position
		
		int start_coordinate_x = (int)((current_quadrant % Math.sqrt(quadrant_count)) * 20);
		int start_coordinate_y = ((int)(current_quadrant / Math.sqrt(quadrant_count)) * 20);
		
		System.out.println(start_coordinate_x + ", " + start_coordinate_y);
		
		for (int x = start_coordinate_x; x < (start_coordinate_x + (19)); ++x) {
			for (int y = start_coordinate_y; y < (start_coordinate_y + (19)); ++y) {
				//2 Layers of rendering, the base layer being the grass and the top layer being the objects like doors and walls, etc.
				
				Renderer.RenderTexturedRectangle(x * tileSize, y * tileSize, tileSize, tileSize, textures.get(0));
				if (map[x][y] == 0)
					continue;
				
				Renderer.RenderTexturedRectangle(x * tileSize, y * tileSize, tileSize, tileSize, textures.get(map[x][y]));
			}
		}
	}
	
	public static void setCurrentQuadrant(int quad) {
		System.out.println(quad);
		
		current_quadrant = quad;
	}
	
	public static int getCurrentQuadrant() {
		return current_quadrant;
	}
	
	public static int getQuadrantWidth() {
		return (int)Math.sqrt(quadrant_count);
	}
	
	//Add a texture to the textures hashmap
	public static void AddTexture(String filename, int GID) {
		Texture texture = null;
		
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (texture != null) textures.put(GID, texture);
		else System.out.println("Texture is null, unable to add to array");
	}
	
	//Load map file
	private static void LoadMapFile(String filename) { 
		Scanner textReader = null;
		try {
			textReader = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		width = textReader.nextInt();
		height = textReader.nextInt();
		
		tileSize = textReader.nextInt();
		
		map = new int[width][height];
		
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				map[x][y] = textReader.nextInt();
			}
		}
		
		textReader.close();
	}
	
	//Use method to parse the file and get the objects out, so you know where the objects are
	private static void CreateObjects() {
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
