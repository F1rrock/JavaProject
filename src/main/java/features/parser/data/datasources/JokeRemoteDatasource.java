package features.parser.data.datasources;

import features.parser.data.models.RequestModel;

public interface JokeRemoteDatasource {
    RequestModel getSomeJokes() throws Exception;
}
