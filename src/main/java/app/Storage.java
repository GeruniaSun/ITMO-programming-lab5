package app;

import data.Ticket;

import java.util.Collection;

public record Storage(Collection<Ticket> collection) {
}
