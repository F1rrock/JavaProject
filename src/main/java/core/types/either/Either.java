package core.types.either;

sealed public interface Either<L, R> permits Left, Right {
    Object get();
}
