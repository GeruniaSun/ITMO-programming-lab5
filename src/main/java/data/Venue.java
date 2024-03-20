package data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Objects;

public class Venue {
    @JacksonXmlProperty
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    public static int nextId = 0;
    @JacksonXmlProperty
    private String name; //Поле не может быть null, Строка не может быть пустой
    @JacksonXmlProperty
    private int capacity; //Значение поля должно быть больше 0
    @JacksonXmlProperty
    private Address address; //Поле может быть null

    {
        this.id = nextId + 1;
        nextId++;
    }

    public static class Builder{
        private Venue newVenue;

        public Builder(){
            newVenue = new Venue();
        }

        public Builder(Venue venue) {
            newVenue = new Venue();
            newVenue.id = venue.id;
            nextId--;
        }

        public Builder withName(String name) {
            if (name.isBlank() | name == "") throw new NullPointerException("это поле не может быть пустым");
            newVenue.name = name;
            return this;
        }

        public Builder withCapacity(int capacity) {
            if (capacity <= 0) throw new IllegalArgumentException("вместимость должна быть положительной");
            newVenue.capacity = capacity;
            return this;
        }

        public Builder withAddress(Address address) {
            newVenue.address = address;
            return this;
        }

        public Venue build(){
            return newVenue;
        }

    }

    public static class Address {
        @JacksonXmlProperty
        private final String street; //Длина строки не должна быть больше 160, Поле не может быть null

        public Address(String street) {
            if (street.isBlank()) throw new NullPointerException("это поле не может быть пустым");
            if (street.length() > 160) throw new IllegalArgumentException("слишком длинное название. " +
                    "Пожалуйста ограничтесь 160-ью символами");
            this.street = street;
        }

        private Address(){this.street = "костыль";}

        @Override
        public boolean equals(Object otherObject) {
            if (this == otherObject) return true;
            if (otherObject == null || this.getClass() != otherObject.getClass()) return false;
            Address address = (Address) otherObject;
            return Objects.equals(street, address.street);
        }

        @Override
        public int hashCode() {
            return street.hashCode() * 3;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Venue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || this.getClass() != otherObject.getClass()) return false;
        Venue other = (Venue) otherObject;
        return id == other.id && capacity == other.capacity
                && Objects.equals(name, other.name) && Objects.equals(address, other.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, address);
    }
}