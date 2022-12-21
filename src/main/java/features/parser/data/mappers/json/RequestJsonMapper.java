package features.parser.data.mappers.json;

import core.mappers.JsonMapper;
import features.parser.data.models.RequestModel;
import features.parser.domain.mappers.json.JokeJsonMapperInterface;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class RequestJsonMapper implements JsonMapper<RequestModel> {
    private final JokeJsonMapperInterface jokeMapper;

    public RequestJsonMapper(JokeJsonMapperInterface jokeMapper) {
        this.jokeMapper = jokeMapper;
    }

    private JSONArray convertToList(List<?> models) throws NullPointerException {
        final var jokes = new JSONArray();
        for (var model : models) {
            jokes.put(jokeMapper.toJson(model));
        }
        return jokes;
    }

    private List<?> convertFromList(JSONArray jokes) throws NullPointerException {
        final var models = new ArrayList<>();
        for (var i = 0; i < jokes.length(); i ++) {
            JSONObject json = jokes.getJSONObject(i);
            models.add(jokeMapper.fromJson(json));
        }
        return models;
    }

    @Override
    public JSONObject toJson(RequestModel model) {
        final var json = new JSONObject();
        json.put("error", model.error());
        json.put("amount", model.amount());
        json.put("jokes", convertToList(model.jokes()));
        return json;
    }

    @Override
    public RequestModel fromJson(JSONObject json) {
        return new RequestModel(
                json.getBoolean("error"),
                json.getInt("amount"),
                convertFromList(json.getJSONArray("jokes"))
        );
    }
}
