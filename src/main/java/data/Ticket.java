package data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс описывающий сущность билета
 */

public class Ticket implements Comparable<Ticket> {
    /**
     * уникальный номер билета в системе, значение этого поля генерируется автоматически
     */
    @JacksonXmlProperty
    private Long id;
    private static Long nextId = 0L;
    @JacksonXmlProperty
    private String name; //Поле не может быть null, Строка не может быть пустой
    @JacksonXmlProperty
    private Coordinates coordinates; //Поле не может быть null
    private final LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @JacksonXmlProperty
    private Long price; //Поле не может быть null, Значение поля должно быть больше 0
    @JacksonXmlProperty
    private TicketType type; //Поле не может быть null
    @JacksonXmlProperty
    private Venue venue; //Поле может быть null

    public enum TicketType {
        CHEAP,
        BUDGETARY,
        USUAL,
        VIP;
    }

    {
        this.id = nextId + 1;
        nextId++;

        this.creationDate = LocalDate.now();
    }

    public static class Builder{
        private Ticket newTicket;

        public Builder(){
            newTicket = new Ticket();
        }

        public Builder(Ticket ticket) {
            newTicket = new Ticket();
            newTicket.id = ticket.id;
            nextId--;
        }

        public Builder withName(String name) {
            if (name.isBlank()) throw new NullPointerException("это поле не может быть пустым");
            newTicket.name = name;
            return this;
        }

        public Builder withCoordinates(Coordinates coordinates) {
            if (coordinates == null) throw new NullPointerException("это поле не может быть пустым");
            newTicket.coordinates = coordinates;
            return this;
        }

        public Builder withPrice(Long price) {
            if (price == null | price <= 0) throw new IllegalArgumentException("цена должна быть положительной");
            newTicket.price = price;
            return this;
        }

        public Builder withType(TicketType type) {
            if (type == null ) throw new NullPointerException("это поле не может быть пустым");
            newTicket.type = type;
            return this;
        }

        public Builder withVenue(Venue venue) {
            newTicket.venue = venue;
            return this;
        }

        public boolean isReadyToBuild(){
            return !(newTicket.name == null
                    || newTicket.coordinates == null
                    || newTicket.price == null
                    || newTicket.type == null);
        }

        public Ticket build(){
            return newTicket;
        }

    }

    public TicketType getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", type=" + type +
                ", venue=" + venue +
                '}';
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || this.getClass() != otherObject.getClass()) return false;
        Ticket other = (Ticket) otherObject;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && Objects.equals(coordinates, other.coordinates) && Objects.equals(creationDate, other.creationDate)
                && Objects.equals(price, other.price) && type == other.type && Objects.equals(venue, other.venue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, type, venue);
    }

    @Override
    public int compareTo(Ticket other) {
        return this.price.compareTo(other.price);
    }
}