package features.parser.data.mappers.json;

import core.mappers.JsonMapper;
import features.parser.data.models.FlagsModel;
import org.json.JSONObject;

public final class FlagsJsonMapper implements JsonMapper<FlagsModel> {
    @Override
    public JSONObject toJson(FlagsModel model) {
        var json = new JSONObject();
        json.put("nsfw", model.nsfw());
        json.put("religious", model.religious());
        json.put("political", model.political());
        json.put("racist", model.racist());
        json.put("sexist", model.sexist());
        json.put("explicit", model.explicit());
        return json;
    }

    @Override
    public FlagsModel fromJson(JSONObject json) {
        return new FlagsModel(
                json.getBoolean("nsfw"),
                json.getBoolean("religious"),
                json.getBoolean("political"),
                json.getBoolean("racist"),
                json.getBoolean("sexist"),
                json.getBoolean("explicit")
        );
    }
}
