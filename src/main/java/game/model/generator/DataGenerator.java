package game.model.generator;

import game.controller.GameAppController;
import game.model.Board;
import game.model.Orientation;
import game.model.Turtle;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class DataGenerator {

    private static JSONObject getStats() {
        String strJSON = "";
        try {
            strJSON = new String(Files.readAllBytes(Paths.get("./src/main/resources/levels/statistics.json")));
        } catch (IOException e) {
            System.out.println(e);
        }

        return new JSONObject(strJSON);
    }

    public final static int getHighestUnlocked(){
        JSONObject statJSON = getStats();
        return statJSON.getInt("unlocked");
    }

    public final static boolean unlockLevel(Integer level){
        JSONObject statJSON = getStats();
        if (statJSON.getInt("unlocked")<level){
            statJSON.remove("unlocked");
            statJSON.put("unlocked",level.toString());
            try (FileWriter file = new FileWriter("./src/main/resources/levels/statistics.json")) {
                statJSON.write(file);
            } catch (IOException ex){
                ex.printStackTrace();
            }
            return true;
        } else return false;
    }

    public final static JSONObject getLevelInfo(int lvl){
        if (lvl<=0)
            return null;
        String strJSON = "";
        try {
            strJSON = new String(Files.readAllBytes(Paths.get("./src/main/resources/levels/lvl" + lvl + ".json")));
        } catch (IOException e) { System.out.println(e); }

        return new JSONObject(strJSON);
    }

    public final static List<Object> getAvailiableCommandsForLvl(int lvl){
        return getLevelInfo(lvl).getJSONArray("commands").toList();
    }



    public final static Board generateGameData(int lvl, Board board) {
        JSONObject lvlJSON = getLevelInfo(lvl);

        int boardSize = lvlJSON.getInt("boardSize");
        int turtleX = lvlJSON.getInt("turtleX");
        int turtleY = lvlJSON.getInt("turtleY");
        String turtleOrientation = lvlJSON.getString("turtleOrientation");

        JSONArray avaliableMovesJSON = lvlJSON.getJSONArray("commands");
        List avaliableMoves = avaliableMovesJSON.toList();

        JSONArray fieldsInfoJSON = lvlJSON.getJSONArray("fieldsInfo");
        List fieldsInfo = fieldsInfoJSON.toList();

        if (GameAppController.boardExists) {
            board.getTurtle().setXProperty(turtleX);
            board.getTurtle().setYProperty(turtleY);
            board.getTurtle().setOrientationProperty(Orientation.valueOf(turtleOrientation));

            board.setBoardSize(boardSize);
            board.setFields(fieldsInfo);
            return board;
        } else {
            GameAppController.boardExists = true;
            Turtle turtle = new Turtle(turtleX, turtleY, Orientation.valueOf(turtleOrientation));
            return new Board(boardSize, turtle, fieldsInfo);
        }
    }

}