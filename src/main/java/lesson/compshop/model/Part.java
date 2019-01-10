package lesson.compshop.model;

import javax.persistence.*;

@Entity
@Table( name="parts")
public class Part {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name = "number")
    private int number;

    @Column(name = "isNeed")
    private Boolean isNeed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Boolean getIsNeed() {
        return isNeed;
    }

    public void setIsNeed(Boolean need) {
        isNeed = need;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", isNeed=" + isNeed +
                '}';
    }
}
