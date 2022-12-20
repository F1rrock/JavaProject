package features.parser.domain.entities;

public record JokeEntity(
        String category,
        String type,
        String text,
        FlagsEntity flags,
        int id,
        boolean safe,
        String lang
) {
}
