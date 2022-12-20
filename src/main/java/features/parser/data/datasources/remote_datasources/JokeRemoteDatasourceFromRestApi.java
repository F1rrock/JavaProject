package features.parser.data.datasources.remote_datasources;

import core.errors.exceptions.ServerException;
import core.mappers.JsonMapper;
import features.parser.data.datasources.JokeRemoteDatasource;
import features.parser.data.models.RequestModel;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class JokeRemoteDatasourceFromRestApi implements JokeRemoteDatasource {
    private final HttpClient client;
    private final JsonMapper<RequestModel> requestMapper;

    public JokeRemoteDatasourceFromRestApi(HttpClient client, JsonMapper<RequestModel> requestMapper) {
        this.client = client;
        this.requestMapper = requestMapper;
    }

    private RequestModel fetchRequest(String url) throws Exception {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        final HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        var jsonResponse = new JSONObject(response.body());
        if ((response.statusCode() == 200) ||
                (jsonResponse.getBoolean("error"))) {
            return requestMapper.fromJson(jsonResponse);
        } else {
            throw new ServerException();
        }
    }

    @Override
    public RequestModel getSomeJokes() throws Exception {
        return fetchRequest("https://v2.jokeapi.dev/joke/Any?amount=10");
    }
}
