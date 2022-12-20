package features.parser.data.models;

public record TwoPartJokeModel(
        String category,
        String type,
        String setup,
        String delivery,
        FlagsModel flags,
        int id,
        boolean safe,
        String lang
) {
}
