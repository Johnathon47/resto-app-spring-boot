package components.model.dto;

public class DishDTO {
    private Long id;
    private String name;

    public DishDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DishDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DishDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
