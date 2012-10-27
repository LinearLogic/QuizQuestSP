package ss.linearlogic.quizquest;

import org.newdawn.slick.TrueTypeFont;

public class Textbox {
	private static String string;
	private static TrueTypeFont font;
	
	private static int x;
	private static int y;
	
	private static int width;
	private static int height;
	
	private static boolean active = false;
	
	public Textbox() {}
	
	public static void Initialize(String fontFile) {
		font = Renderer.LoadFont(fontFile, 24);
		
		x = 0;
		y = 380;
		
		width = 480;
		height = 100;
	}
	
	public static void InitializeWithSystemFont() {
		font = Renderer.LoadSystemFont("Times New Roman", 24);
		
		if (font == null) System.out.println("Font is null");
		
		x = 0;
		y = 380;
		
		width = 480;
		height = 100;
	}
	
	public static void toggleActive() {
		active = !active;
	}
	
	public static boolean isActive() {
		return active;
	}
	
	public static void setText(String text) {
		string = text;
	}
	
	public static void render() {
		if (!active) return;
		
		System.out.println(string);
		
		Renderer.RenderColoredRectangle(x, y, width, height, 1.0f, 0.0f, 1.0f);
		Renderer.RenderString(string, x + (width/2), y + (height/2), font);
	}
}
