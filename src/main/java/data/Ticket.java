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
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
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

    private Ticket(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Long getPrice() {
        return price;
    }

    public TicketType getType() {
        return type;
    }

    public Venue getVenue() {
        return venue;
    }

    public Ticket(Long id, String name, Coordinates coordinates, LocalDate creationDate,
                  Long price, TicketType type, Venue venue) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.type = type;
        this.venue = venue;
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