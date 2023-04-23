package renderEngine.Entity;

import game.Game;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    public List<Entity> entities = new ArrayList<>();
    Game game;

    public Scene(Game game){
        this.game = game;
    }

    public void update(float elapsed){
        for (Entity entity : entities) {
            entity.update(elapsed);
        }
    }

    public void AddEntity(Entity entity){
        entities.add(entity);
        entity.Start(this);
    }

    public void RemoveEntity(Entity entity){
        entities.remove(entity);
        entity.onDestroy();
    }
}
