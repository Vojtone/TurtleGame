package game.model.command;

import game.model.Board;
import game.model.command.ITurtleCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandSequence {
    private List<ITurtleCommand> commands = new ArrayList<>();
    private Board board;

    public CommandSequence(Board b) {
        this.board = b;
    }

    public void addCommand(ITurtleCommand command) {
        commands.add(command);
    }

    public void removeLastCommand() {
        if (commands.size() > 0)
            commands.remove(commands.size()-1);
        else
            System.out.println("Size is zero");
    }

    public void removeCommand(int index){
        if (commands.size() > 0)
            commands.remove(index);
        else
            System.out.println("Size is zero");
    }

    public List<ITurtleCommand> getCommands(){
        return commands;
    }

    public int getSize() {
        return commands.size();
    }

    public void clear() {
        commands.clear();
    }

    public String execute() {
        boolean allCommandsOk = true;
        for (ITurtleCommand com : commands) {
            if (!com.execute()) allCommandsOk = false;
            // update view here!
        }
        if (!allCommandsOk)
            return "Fail :( \n You tried to make impossible move. (There is no way)";
        else {
            //check if all visible are also visited
            System.out.println("Nie ma bledow");

            if (board.allVisited())
                return "Great! :)";
            else return "You didn't visited all fields :(";
        }
    }
}