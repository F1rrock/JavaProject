package features.parser.data.datasources;

import core.errors.exceptions.CacheException;
import features.parser.data.models.RequestModel;
import org.json.JSONObject;

public interface JokeLocalDatasource {
    void safeJokes(RequestModel requestModel);
    RequestModel getCachedJokes() throws CacheException;
}
