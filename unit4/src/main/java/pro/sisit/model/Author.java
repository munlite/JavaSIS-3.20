package pro.sisit.model;

import pro.sisit.adapter.AdaptedForCSVobject;

import java.util.List;
import java.util.Objects;

public class Author implements AdaptedForCSVobject {

    private String name;
    private String birthPlace;

    public Author() {
    }

    public Author(String name, String birthPlace) {
        this.name = name;
        this.birthPlace = birthPlace;
    }

    public String getName() {
        return name;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return getName().equals(author.getName()) &&
            getBirthPlace().equals(author.getBirthPlace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBirthPlace());
    }

    @Override
    public void fillingInAnObjectField(List<String> list) {
        name = list.get(0);
        birthPlace = list.get(1);
    }
}
