package data;

public class VenueBuilder {
    private final int id;
    public static int nextId = 0;
    private String name;
    private int capacity;
    private Venue.Address address;

    public VenueBuilder(){
        this.id = nextId + 1;
        nextId++;
    }

    public VenueBuilder withName(String name) {
        if (name.isBlank() | name == "") throw new NullPointerException("это поле не может быть пустым");
        this.name = name;
        return this;
    }

    public VenueBuilder withCapacity(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("вместимость должна быть положительной");
        this.capacity = capacity;
        return this;
    }

    public VenueBuilder withAddress(Venue.Address address) {
        this.address = address;
        return this;
    }

    public Venue build(){
        return new Venue(id, name, capacity, address);
    }

}
