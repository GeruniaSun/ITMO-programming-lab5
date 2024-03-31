package app;

import data.Ticket;

import java.util.Collection;

/**
 * <h1>Класс хранилища</h1>
 * record для хранения коллекции, которой манипулирует приложение
 * @param collection коллекция экземпляров класса {@link data.Ticket}
 */
public record Storage(Collection<Ticket> collection) {
}
