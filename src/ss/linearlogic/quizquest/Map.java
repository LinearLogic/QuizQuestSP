package ss.linearlogic.quizquest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Map {
	private static int map[][];
	
	private static int width;
	private static int height;
	
	private static HashMap<Integer, Texture> textures = new HashMap();
	
	private static int tileSize;
	
	public Map(String filename) {}
	
	public static void Initialize(String filename) {
		LoadMapFile(filename);
	}
	
	public static void Render() {
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				Renderer.RenderTexturedRectangle(x * tileSize, y * tileSize, tileSize, tileSize, textures.get(map[x][y]));
			}
		}
	}
	
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
}
