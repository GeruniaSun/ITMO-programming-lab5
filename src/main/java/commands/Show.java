package commands;

import app.Receiver;

import java.util.List;

/**
 * <h1>Команда show</h1>
 * класс инкапсулирующий объект команды show
 */
public class Show implements Command{
    /**
     * Поле для связи с объектом приемника класса {@link Receiver} выполняющим команду
     */
    private final Receiver receiver;

    /**
     * Стандартный конструктор
     * @param receiver объект приемника
     */
    public Show(Receiver receiver){
        this.receiver = receiver;
    }

    /**
     * Переопределенные методы из интерфейса {@link commands.Command}
     * логика описана в самом интерфейсе
     */
    @Override
    public void execute(List<String> args) {
        if (args != null) throw new IllegalArgumentException("Команда show не принимает никаких аргументов");
        receiver.show();
    }

    @Override
    public String description() {
        return "show - выводит все элементы коллекции";
    }
}
