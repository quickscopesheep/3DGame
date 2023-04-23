package game;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.SharedDrawable;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Entity.Camera;
import renderEngine.Entity.Entity;
import renderEngine.Entity.Light;
import renderEngine.Entity.Scene;
import renderEngine.core.DisplayManager;
import renderEngine.core.Renderer;

public class Game implements Runnable{
    boolean running;
    public Renderer renderer;

    Camera camera;

    Scene scene;

    public Game(){
        DisplayManager.CreateDisplay(1280, 720, 120);

        renderer = new Renderer();

        renderer.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        renderer.setSkyColour(new Vector3f(0f, 0f, 0f));
        renderer.setWorldLight(new Light(new Vector3f(2, 5, 2), new Vector3f(1, .9f, .9f)));

        camera = new Camera(new Vector3f(0, 2, 5), 25, 0, 0);
        renderer.setCamera(camera);

        scene = new Scene(this);
        Entity.SpawnBlueprint("res/blueprints/testRect.json", new Vector3f(0, 0, 0), scene);

        running = true;
    }

    void update(float elapsed){
        scene.update(elapsed);
    }

    @Override
    public void run() {
        float now;
        float last = System.nanoTime();

        float nanoTime = 1/1000000000f;

        while (running) {
            running = !Display.isCloseRequested();

            now = System.nanoTime();
            float elapsed = (now - last)*nanoTime;
            last = now;

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            update(elapsed);
            DisplayManager.UpdateDisplay();
        }

        DisplayManager.DestroyDisplay();
        renderer.cleanUp();
    }

    public static void main(String args[]){
        Game game = new Game();
        game.run();
    }
}