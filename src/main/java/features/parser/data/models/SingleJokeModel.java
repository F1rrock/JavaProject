package features.parser.data.models;

public record SingleJokeModel(
        String category,
        String type,
        String joke,
        FlagsModel flags,
        int id,
        boolean safe,
        String lang
) {
}
