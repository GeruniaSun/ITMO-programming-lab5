package data;

import java.time.LocalDate;

public class TicketBuilder {
    private Long id;
    private static Long nextId = 0L;
    private String name;
    private Coordinates coordinates;
    private final LocalDate creationDate;
    private Long price;
    private Ticket.TicketType type;
    private Venue venue;


    {
        creationDate = LocalDate.now();
    }

    public TicketBuilder(Long id) {
        this.id = id;
    }

    public TicketBuilder(){
        this.id = nextId + 1;
        nextId++;
    }

    public TicketBuilder withName(String name) {
        if (name.isBlank()) throw new NullPointerException("это поле не может быть пустым");
        this.name = name;
        return this;
    }

    public TicketBuilder withCoordinates(Coordinates coordinates) {
        if (coordinates == null) throw new NullPointerException("это поле не может быть пустым");
        this.coordinates = coordinates;
        return this;
    }

    public TicketBuilder withPrice(Long price) {
        if (price == null || price <= 0) throw new IllegalArgumentException("цена должна быть положительной");
        this.price = price;
        return this;
    }

    public TicketBuilder withType(Ticket.TicketType type) {
        if (type == null ) throw new NullPointerException("это поле не может быть пустым");
        this.type = type;
        return this;
    }

    public TicketBuilder withVenue(Venue venue) {
        this.venue = venue;
        return this;
    }

    public boolean isReadyToBuild(){
        return !(this.name == null
                || this.coordinates == null
                || this.price == null
                || this.type == null);
    }

    public Ticket build(){
        return new Ticket(id, name, coordinates, creationDate, price, type, venue);
    }

}
