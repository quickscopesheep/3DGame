package renderEngine.Entity.components;

import org.json.simple.JSONObject;

public class SpinComponent extends Component{

    float speed;

    public SpinComponent(JSONObject initArgs) {
        super(initArgs);
        speed = Float.parseFloat((String) initArgs.get("speed"));
    }

    public SpinComponent(float speed){
        super(null);
        this.speed = speed;
    }

    @Override
    public void update(float elapsed) {
        parent.Rotate(0, speed*elapsed, 0);
    }
}
