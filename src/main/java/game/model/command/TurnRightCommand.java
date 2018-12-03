package game.model.command;

import game.model.Board;
import game.model.BoardField;
import game.model.Orientation;
import game.model.Turtle;
import game.model.command.ITurtleCommand;

public class TurnRightCommand implements ITurtleCommand {

    private Board board;
    private Turtle turtle;

    private int xOffset;
    private int yOffset;

    public TurnRightCommand(Board b) {
        this.board = b;
        this.turtle = b.getTurtle();
    }

    @Override
    public boolean execute() {

        switch (turtle.getOrientation()) {
            case E: {
                turtle.setOrientation(Orientation.S);
                return true;
            }
            case N: {
                turtle.setOrientation(Orientation.E);
                return true;
            }
            case S: {
                turtle.setOrientation(Orientation.W);
                return true;
            }
            case W: {
                turtle.setOrientation(Orientation.N);
                return true;
            }
            default:
                return false;
        }
    }

    @Override
    public String getName() {
        return "Turn right";
    }
}