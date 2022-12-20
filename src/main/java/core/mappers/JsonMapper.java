package core.mappers;

import org.json.JSONObject;

public interface JsonMapper<Model> {
    JSONObject toJson(Model model);
    Model fromJson(JSONObject json);
}
