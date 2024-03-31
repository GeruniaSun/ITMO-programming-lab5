package commands;

import app.Receiver;

import java.util.List;

public class Clear implements Command{
    private final Receiver receiver;

    public Clear(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (args != null) throw new IllegalArgumentException("Команда clear не принимает никаких аргументов");
        receiver.clear();
    }

    @Override
    public String description() {
        return "clear - удаляет все элементы из коллекции";
    }
}
