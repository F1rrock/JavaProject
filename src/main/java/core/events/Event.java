package core.events;

public interface Event<Param> {
    void execute(Param param);
}
