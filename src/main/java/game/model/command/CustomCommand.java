package game.model.command;

import java.util.List;

public class CustomCommand implements ITurtleCommand {

    List<ITurtleCommand> commands;

    public CustomCommand(List<ITurtleCommand> commands){
        this.commands = commands;
    }


    @Override
    public boolean execute() {
        if(!commands.isEmpty()) {
            for (ITurtleCommand command : commands) {
                if (!command.execute()) return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getImageURL() {
        //todo zamienic
        return "./src/main/resources/images/loop.png";
    }
}
