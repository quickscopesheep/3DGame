package renderEngine.Entity.components;

import renderEngine.Entity.Entity;
import game.Game;
import org.json.simple.JSONObject;

public class Component {
    public Entity parent;
    public Game game;

    public JSONObject initArgs;

    public Component(JSONObject initArgs){
        this.initArgs = initArgs;
    }

    public void awake(Entity parent, Game game){
        this.parent = parent;
        this.game = game;
    }

    public void update(float elapsed){

    }

    public void onDestroy(){

    }
}
