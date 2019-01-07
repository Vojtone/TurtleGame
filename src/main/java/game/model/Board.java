package game.model;

import java.util.List;

public class Board {
    private int boardSize;
    private Turtle turtle;
    private BoardField[][] fields;

    public Board(int boardSize, Turtle turtle, List fieldsInfo) {
        if (boardSize > 0){
            this.boardSize = boardSize;
            this.turtle = turtle;

            BoardField[][] fields = new BoardField[boardSize][boardSize];

            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++)
                    fields[i][j] = (((List)fieldsInfo.get(i)).get(j)).toString().charAt(0) == '#' ?
                            new BoardField(true) : new BoardField(false);
            }


            fields[turtle.getY()][turtle.getX()].setVisited(true);

            this.fields = fields;
        }
        else{
            fields = new BoardField[][]{};
            this.fields = fields;
        }

    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) { this.boardSize = boardSize; }

    public Turtle getTurtle() {
        return turtle;
    }

    public void setTurtle(Turtle turtle) {
        this.turtle = turtle;
    }

    public BoardField[][] getFields() {
        return fields;
    }

    public void setFields(List fieldsInfo) {
        BoardField[][] fields = new BoardField[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++)
                fields[i][j] = (((List)fieldsInfo.get(i)).get(j)).toString().charAt(0) == '#' ?
                        new BoardField(true) : new BoardField(false);
        }


        fields[turtle.getY()][turtle.getX()].setVisited(true);

        this.fields = fields;
    }

    public Boolean canMoveTo(int x, int y){
        if (x>=0 && y>=0)
            return (x >= 0 && x < boardSize && y >= 0 && y < boardSize && fields[y][x].isVisible());
        else
            return false;
    }

    public void visitField(int x, int y){
        if(x>=0 && y>=0)
            fields[y][x].setVisited(true);
        else
            System.out.println("Error");
    }

    public Boolean allVisited(){
        for (int i=0; i<boardSize; i++) {
            for (int j=0; j<boardSize; j++) {
                if (fields[i][j].isVisible() && !fields[i][j].isVisited())
                    return false;
            }
        }
        return true;
    }
}

