package commands;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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
        commandMap.put("remove_greater", new RemoveGreater(receiver));
        commandMap.put("remove_lower", new RemoveLower(receiver));
        commandMap.put("add_if_max", new AddIfMax(receiver));
        commandMap.put("update", new Update(receiver));
        commandMap.put("execute_script", new ExecuteScript(receiver));
    }

    public void runApp(InputStreamReader inStream){
        Scanner in = new Scanner(inStream);
        while (in.hasNextLine()){
            try {
                var input = in.nextLine();
                if (input.isBlank()) {throw new NullPointerException("Вы ничего не ввели");}
                List<String> commandWithArgs = List.of(input.split(" "));
                String commandName = commandWithArgs.get(0);
                List<String> commandArguments = null;
                if (commandWithArgs.size() >= 2){
                    commandArguments = commandWithArgs.subList(1, commandWithArgs.size());
                }
                this.runCommand(commandMap.get(commandName), commandArguments);
            } catch (NoSuchElementException e) {
                System.out.println("вы какие-то гадости делаете. Закрываю приложение");
                System.exit(999);
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
