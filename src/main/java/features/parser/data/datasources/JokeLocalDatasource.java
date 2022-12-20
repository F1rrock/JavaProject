package features.parser.data.datasources;

import features.parser.data.models.RequestModel;

public interface JokeLocalDatasource {
    void safeJokes(RequestModel requestModel);
    RequestModel getCachedJokes() throws Exception;
}
