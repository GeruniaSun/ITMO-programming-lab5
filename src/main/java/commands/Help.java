package commands;

import java.util.Collection;
import java.util.List;

public class Help implements Command{

    private final Receiver receiver;
    private final Collection<Command> commands;

    public Help(Receiver receiver, Collection<Command> commands){
        this.receiver = receiver;
        this.commands = commands;
    }

    @Override
    public void execute(List<String> args) {
        if (args != null) throw new IllegalArgumentException("Команда help не принимает никаких аргументов");
        receiver.help(this.commands);
    }

    @Override
    public String description() {
        return "help - справка по доступным командам";
    }
}
