package renderEngine.Entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Entity.components.Component;
import renderEngine.Entity.components.ComponentType;
import renderEngine.Model.Renderable;
import renderEngine.core.Renderer;

import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.HashMap;

public class Entity {
    protected Vector3f location;
    protected float rotX, rotY, rotZ;
    protected float scale = 1f;

    HashMap<ComponentType, Component> components = new HashMap<>();

    public Scene scene;
    boolean hasStarted;

    public Entity(Vector3f location){
        this.location = location;
    }

    public void Start(Scene scene){
        this.scene = scene;
        hasStarted = true;
        for (Component component : components.values()) {
            component.awake(this, scene.game);
        }
    }

    public void update(float elapsed){
        for (Component component : components.values()) {
            component.update(elapsed);
        }
    }

    public void onDestroy(){
        for (Component component : components.values()) {
            component.onDestroy();
        }
    }

    public void Translate(float dx, float dy, float dz){
        location.translate(dx, dy, dz);
    }

    public void Rotate(float dx, float dy, float dz){
        rotX += dx;
        rotY += dy;
        rotZ += dz;
    }
    public Component GetComponent(ComponentType type){
        return components.get(type);
    }

    public void AddComponent(ComponentType type, Component component){
        if(components.containsKey(type))
            return;

        if(hasStarted)
            component.awake(this, scene.game);

        components.put(type, component);
    }

    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public static Entity SpawnBlueprint(String path, Vector3f position, Scene scene){
        JSONParser parser = new JSONParser();
        Object blueprint;

        try {
            blueprint = parser.parse(new FileReader(path));
            Entity entity = new Entity(position);

            for (Object object : ((JSONArray) blueprint)) {
                JSONObject comp = (JSONObject)object;
                ComponentType type = ComponentType.valueOf((String) comp.get("type"));
                Class<?> componentClass = Class.forName((String) comp.get("class"));

                Constructor<?>[] constructor = componentClass.getDeclaredConstructors();
                Component component = (Component) constructor[0].newInstance(((JSONObject) object).get("properties"));

                entity.AddComponent(type, component);
            }

            scene.AddEntity(entity);
            return entity;

        } catch (Exception e) {
            System.out.println("Could not instantiate entity blueprint: ");
            e.printStackTrace();
            return null;
        }
    }
}
