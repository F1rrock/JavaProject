package features.parser.domain.entities;

public record FlagsEntity(
        boolean nsfw,
        boolean religious,
        boolean political,
        boolean racist,
        boolean sexist,
        boolean explicit
) {
}
