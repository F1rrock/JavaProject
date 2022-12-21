package features.parser.data.datasources.remote_datasources;

import core.errors.exceptions.ServerException;
import core.mappers.JsonMapper;
import features.parser.data.datasources.JokeRemoteDatasource;
import features.parser.data.models.JokePostModel;
import features.parser.data.models.RequestModel;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class JokeRemoteDatasourceFromRestApi implements JokeRemoteDatasource {
    private final HttpClient client;
    private final JsonMapper<RequestModel> requestMapper;
    private final JsonMapper<JokePostModel> jokePostMapper;

    public JokeRemoteDatasourceFromRestApi(HttpClient client, JsonMapper<RequestModel> requestMapper, JsonMapper<JokePostModel> jokePostMapper) {
        this.client = client;
        this.requestMapper = requestMapper;
        this.jokePostMapper = jokePostMapper;
    }

    private RequestModel get(String url) throws Exception {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        final HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        final var jsonResponse = new JSONObject(response.body());
        if ((response.statusCode() == 200) &&
                !(jsonResponse.getBoolean("error"))) {
            return requestMapper.fromJson(jsonResponse);
        } else {
            throw new ServerException();
        }
    }

    private void put(String url, JSONObject json) throws Exception {
        final String jsonString = json.toString();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        final var jsonResponse = new JSONObject(response);
        if ((response.statusCode() != 200) ||
                (jsonResponse.getBoolean("error"))) {
            System.out.println(response.statusCode());
            System.out.println(response.body());
            throw new ServerException();
        }
    }

    @Override
    public RequestModel getSomeJokes() throws Exception {
        return get("https://v2.jokeapi.dev/joke/Any?amount=10");
    }

    @Override
    public void addJoke(JokePostModel model) throws Exception {
        final JSONObject json = jokePostMapper.toJson(model);
        put("https://v2.jokeapi.dev/submit?dry-run", json);
    }
}
