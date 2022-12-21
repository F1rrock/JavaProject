package features.parser.data.mappers.json.post;

import core.mappers.JsonMapper;
import features.parser.data.models.JokePostModel;
import features.parser.domain.mappers.json.JokeJsonMapperInterface;
import org.json.JSONObject;

public class JokePostJsonMapper implements JsonMapper<JokePostModel> {
    private final JokeJsonMapperInterface jokeMapper;

    public JokePostJsonMapper(JokeJsonMapperInterface jokeMapper) {
        this.jokeMapper = jokeMapper;
    }

    @Override
    public JSONObject toJson(JokePostModel model) {
        final JSONObject json = jokeMapper.toJson(model.joke());
        json.put("formatVersion", model.formatVersion());
        return json;
    }

    @Override
    public JokePostModel fromJson(JSONObject json) {
        final int formatVersion = json.getInt("formatVersion");
        json = (JSONObject) json.remove("formatVersion");
        return new JokePostModel(
                formatVersion,
                jokeMapper.fromJson(json)
        );
    }
}
