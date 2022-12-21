package features.parser.data.mappers.json.jokes;

import core.mappers.JsonMapper;
import core.observers.Observable;
import features.parser.data.models.FlagsModel;
import features.parser.data.models.SingleJokeModel;
import features.parser.domain.mappers.json.JokeJsonMapperInterface;
import org.json.JSONObject;

public final class SingleJokeJsonMapper implements JokeJsonMapperInterface, Observable<JokeJsonMapperInterface, JSONObject> {
    private final JsonMapper<FlagsModel> flagsMapper;

    public SingleJokeJsonMapper(JsonMapper<FlagsModel> flagsMapper) {
        this.flagsMapper = flagsMapper;
    }

    @Override
    public JSONObject toJson(Object model) {
        var jokeModel = (SingleJokeModel) model;
        var json = new JSONObject();
        json.put("category", jokeModel.category());
        json.put("type", jokeModel.type());
        json.put("joke", jokeModel.joke());
        json.put("flags", flagsMapper.toJson(jokeModel.flags()));
        json.put("id", jokeModel.id());
        json.put("safe", jokeModel.safe());
        json.put("lang", jokeModel.lang());
        return json;
    }

    @Override
    public SingleJokeModel fromJson(JSONObject json) {
        return new SingleJokeModel(
                json.getString("category"),
                json.getString("type"),
                json.getString("joke"),
                flagsMapper.fromJson(json.getJSONObject("flags")),
                json.getInt("id"),
                json.getBoolean("safe"),
                json.getString("lang")
        );
    }

    @Override
    public JokeJsonMapperInterface accessOnFrom(JSONObject json) {
        if (json.getString("type").equals("single")) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    public JokeJsonMapperInterface accessOnTo(Object param) {
        if (param instanceof SingleJokeModel) {
            return this;
        } else {
            return null;
        }
    }
}
