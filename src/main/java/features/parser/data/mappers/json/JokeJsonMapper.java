package features.parser.data.mappers.json;

import core.observers.Observer;
import features.parser.domain.mappers.json.JokeJsonMapperInterface;
import org.json.JSONObject;

public class JokeJsonMapper implements JokeJsonMapperInterface {
    private final Observer<JokeJsonMapperInterface, JSONObject> observer;

    public JokeJsonMapper(Observer<JokeJsonMapperInterface, JSONObject> observer) {
        this.observer = observer;
    }

    @Override
    public JSONObject toJson(Object model) {
        JokeJsonMapperInterface jokeMapper = observer.getMapperOnTo(model);
        return jokeMapper.toJson(model);
    }

    @Override
    public Object fromJson(JSONObject json) {
        JokeJsonMapperInterface jokeMapper = observer.getMapperOnFrom(json);
        return jokeMapper.fromJson(json);
    }
}
