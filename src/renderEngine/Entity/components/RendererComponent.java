package renderEngine.Entity.components;

import renderEngine.Entity.Entity;
import game.Game;
import org.json.simple.JSONObject;
import renderEngine.Model.Renderable;
import renderEngine.Util.MathUtil;

public class RendererComponent extends Component {
    Renderable renderable;

    public RendererComponent(JSONObject initArgs){
        super(initArgs);
    }

    public RendererComponent(Renderable renderable){
        super(null);
        this.renderable = renderable;
    }

    @Override
    public void awake(Entity parent, Game game) {
        super.awake(parent, game);
        renderable = game.renderer.getLoader().loadRenderableFromFile((String) initArgs.get("renderable"), game.renderer);
    }

    @Override
    public void update(float elapsed) {
        super.update(elapsed);
        game.renderer.render(renderable, MathUtil.createTransformationMatrix(parent.getLocation(), parent.getRotX(), parent.getRotY(),
                parent.getRotZ(), parent.getScale()));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
