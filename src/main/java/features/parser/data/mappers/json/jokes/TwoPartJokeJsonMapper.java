package features.parser.data.mappers.json.jokes;

import core.mappers.JsonMapper;
import core.observers.Observable;
import features.parser.data.models.FlagsModel;
import features.parser.data.models.TwoPartJokeModel;
import features.parser.domain.mappers.json.JokeJsonMapperInterface;
import org.json.JSONObject;

public final class TwoPartJokeJsonMapper implements JokeJsonMapperInterface, Observable<JokeJsonMapperInterface, JSONObject> {
    private final JsonMapper<FlagsModel> flagsMapper;

    public TwoPartJokeJsonMapper(JsonMapper<FlagsModel> flagsMapper) {
        this.flagsMapper = flagsMapper;
    }

    @Override
    public JSONObject toJson(Object model) {
        var jokeModel = (TwoPartJokeModel) model;
        var json = new JSONObject();
        json.put("category", jokeModel.category());
        json.put("type", jokeModel.type());
        json.put("setup", jokeModel.setup());
        json.put("delivery", jokeModel.delivery());
        json.put("flags", flagsMapper.toJson(jokeModel.flags()));
        json.put("id", jokeModel.id());
        json.put("safe", jokeModel.safe());
        json.put("lang", jokeModel.lang());
        return json;
    }

    @Override
    public TwoPartJokeModel fromJson(JSONObject json) {
        return new TwoPartJokeModel(
                json.getString("category"),
                json.getString("type"),
                json.getString("setup"),
                json.getString("delivery"),
                flagsMapper.fromJson(json.getJSONObject("flags")),
                json.getInt("id"),
                json.getBoolean("safe"),
                json.getString("lang")
        );
    }

    @Override
    public JokeJsonMapperInterface accessOnFrom(JSONObject json) {
        if (json.getString("type").equals("twopart")) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    public JokeJsonMapperInterface accessOnTo(Object param) {
        if (param instanceof TwoPartJokeModel) {
            return this;
        } else {
            return null;
        }
    }
}
