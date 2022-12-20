package features.parser.data.datasources.local_datasources;

import core.errors.exceptions.CacheException;
import core.mappers.JsonMapper;
import features.parser.data.datasources.JokeLocalDatasource;
import features.parser.data.models.RequestModel;
import org.json.JSONObject;

import java.util.prefs.Preferences;

public final class JokeLocalDatasourceFromPrefs implements JokeLocalDatasource {
    private final Preferences preferences;
    private final JsonMapper<RequestModel> requestMapper;
    private static final String PATH = "JOKES_JSON";

    public JokeLocalDatasourceFromPrefs(Preferences preferences, JsonMapper<RequestModel> requestMapper) {
        this.preferences = preferences;
        this.requestMapper = requestMapper;
    }

    @Override
    public void safeJokes(RequestModel requestModel) {
        final JSONObject json = requestMapper.toJson(requestModel);
        preferences.put(PATH, json.toString());
    }

    @Override
    public RequestModel getCachedJokes() throws CacheException {
        final String cachedData = preferences.get(PATH, null);
        if (cachedData == null) {
            throw new CacheException();
        }
        final JSONObject jsonRequest = new JSONObject(cachedData);
        return requestMapper.fromJson(jsonRequest);
    }
}
