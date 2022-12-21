package features.parser.data.datasources;

import features.parser.data.models.JokePostModel;
import features.parser.data.models.RequestModel;

public interface JokeRemoteDatasource {
    RequestModel getSomeJokes() throws Exception;
    void addJoke(JokePostModel model) throws Exception;
}
