package core.mappers;

public interface EntityMapper<Entity, Model> {
    Entity toEntity(Model model);
    Model fromEntity(Entity entity);
}
