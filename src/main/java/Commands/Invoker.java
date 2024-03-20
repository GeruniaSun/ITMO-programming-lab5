package Commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Invoker {
    private final Map<String, Command> commandMap = new HashMap<>();

    public Invoker(Receiver receiver){
        commandMap.put("info", new Info(receiver));
        commandMap.put("help", new Help(receiver, commandMap.values()));
        commandMap.put("show", new Show(receiver));
        commandMap.put("exit", new Exit(receiver));
        commandMap.put("add", new Add(receiver));
        commandMap.put("print_descending", new PrintDescending(receiver));
        commandMap.put("filter_by_type", new FilterByType(receiver));
        commandMap.put("count_greater_than_type", new CountGreaterThanType(receiver));
        commandMap.put("clear", new Clear(receiver));
        commandMap.put("remove_by_id", new RemoveById(receiver));
        commandMap.put("save", new Save(receiver));
    }

    public void runApp(){
        Scanner in = new Scanner(System.in);
        while (true){
            try {
                List<String> commandWithArgs = List.of(in.nextLine().split(" "));
                if (commandWithArgs.isEmpty()) {throw new IllegalArgumentException("Вы ничего не ввели");}
                String commandName = commandWithArgs.get(0);
                List<String> commandArguments = null;
                if (commandWithArgs.size() >= 2){
                    commandArguments = commandWithArgs.subList(1, commandWithArgs.size());
                }
                this.runCommand(commandMap.get(commandName), commandArguments);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void runCommand(Command command, List<String> args){
        if (command == null) throw new NullPointerException("Такой команды не существует. " + "\n" +
                "Используйте 'help' чтоб получить справку по доступным командам");
        command.execute(args);
    }
}
