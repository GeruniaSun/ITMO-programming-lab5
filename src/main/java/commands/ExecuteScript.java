package commands;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.List;

public class ExecuteScript implements Command{
    private final Receiver receiver;

    public ExecuteScript(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute(List<String> args) {
        if (args == null || args.isEmpty())
            throw new NullPointerException("Команда execute_script не работает без аргумента filename");
        if (args.size() > 1)
            throw new IllegalArgumentException("Команда execute_script принимает только 1 аргумент");
        var filename = args.get(0);
        try {
            receiver.executeScript(filename);
        } catch (AccessDeniedException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String description() {
        return "execute_script filename - выполняет команды из файла filename";
    }
}
