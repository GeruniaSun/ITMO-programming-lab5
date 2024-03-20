package Commands;

import java.util.List;

public class PrintDescending implements Command {

    private final Receiver receiver;

    public PrintDescending(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (args != null) throw new IllegalArgumentException("Команда print_descending не принимает никаких аргументов");
        receiver.printDescending();
    }

    @Override
    public String description() {
        return "print_descending - выводит все элементы коллекции в порядке убывания";
    }
}
