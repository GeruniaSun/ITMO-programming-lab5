package Commands;

import java.util.List;

public interface Command {
    public void execute(List<String> args);
    public String description();
}
