package features.parser.domain.mappers.json;

import core.mappers.JsonMapper;
import org.json.JSONObject;

public interface JokeJsonMapperInterface extends JsonMapper<Object> {
    @Override
    JSONObject toJson(Object model);
    @Override
    Object fromJson(JSONObject json);
}
