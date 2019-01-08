package game.controller;

import game.model.Board;
import game.model.command.*;
import game.model.MoveType;
import game.model.generator.DataGenerator;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Math.*;

public class GameOverviewController {

    private GameAppController appController;
    private Board boardData;
    private CommandSequence commandSequence;
    @FXML
    private Pane boardPane;
    @FXML
    private HBox commandBox;
    @FXML
    private VBox customCommandsBox;

    @FXML
    private Canvas boardCanvas;
    @FXML
    private Canvas turtleCanvas;
    private GraphicsContext gc_board, gc_turtle;
    private ImageView turtleImage;

    //    @FXML
//    private Text commandSeq;
    @FXML
    private Text info;
    @FXML
    private ImageView forwardImage;
    @FXML
    private ImageView leftImage;
    @FXML
    private ImageView rightImage;
    @FXML
    private ImageView loopImage;
    @FXML
    private ImageView nextLevelImage;
    @FXML
    private ImageView executeImage;
    @FXML
    private ImageView customCommand;
    @FXML
    private ChoiceBox levelBox;
    double fieldSize, border, padding;

    private Effect shadow = new DropShadow(20, 2, 2, Color.BLACK);
    private List<Effect> customEffects = new LinkedList<Effect>();
    private List<ImageView> commandImages = new LinkedList<ImageView>();

    @FXML
    private void initialize() {
        commandImages.add(forwardImage);
        commandImages.add(rightImage);
        commandImages.add(leftImage);
        commandImages.add(loopImage);

        for (ImageView iv : commandImages){
            setDragDrop(iv);
        }


        commandBox.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if( db.hasImage()){
                    event.setDropCompleted(true);
                }
            }
        });

        commandBox.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getGestureSource() != commandBox &&
                        event.getDragboard().hasImage()) {

                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });


        //get graphic contexts for board layers
        gc_board = boardCanvas.getGraphicsContext2D();
        gc_turtle = turtleCanvas.getGraphicsContext2D();
        turtleCanvas.toFront();

        //variables for drawing the board

        border = 20;
        padding = 5;
        fieldSize = boardCanvas.getWidth() - 2*border;

        //get turtle image
        try {
            turtleImage = new ImageView(new Image(new FileInputStream("./src/main/resources/images/turtle.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        info.setText(GameAppController.lvl + "/5");

        //disable next level image
        nextLevelImage.setVisible(false);

        //populate level selector
        for (int i = 0; i < DataGenerator.getHighestUnlocked(); i++)
            levelBox.getItems().add("level " + String.format("%d", i + 1));
        levelBox.getSelectionModel().select(GameAppController.lvl - 1);

        //add a listener function for changing the selected level
        levelBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            GameAppController.lvl = t1.intValue() + 1;
            setData(DataGenerator.generateGameData(GameAppController.lvl, boardData));
//            commandSeq.setText("");
            info.setText(GameAppController.lvl + "/5");
            nextLevelImage.setVisible(false);
        });
        // int selectedLvl = levelBox.getSelectionModel().getSelectedIndex();

    }

    private void setDragDrop(ImageView iv){
        iv.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = iv.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(iv.getImage());
                db.setContent(content);
                event.consume();
            }
        });

        iv.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getAcceptedTransferMode()!=null){
                    iv.getOnMouseClicked().handle(null);
                }
            }
        });
    }

    public void addCommand(ITurtleCommand command) {
        commandSequence.addCommand(command);
//        commandSeq.setText(commandSeq.getText() + command.getName());
    }

    public Board getBoardData() {
        return this.boardData;
    }



    @FXML
    private void handleAddCustomCommandAction(Event event){

        CustomCommand customCommandToAdd = new CustomCommand(commandSequence.getCommands());

        ImageView customCommandImage = new ImageView(customCommand.getImage());
        customCommandImage.setFitWidth(50);
        customCommandImage.setFitHeight(50);
        ColorAdjust hue = new ColorAdjust(random(),0,0,0);
        hue.setInput(shadow);
        customEffects.add(hue);
        customCommandImage.setEffect(hue);


        customCommandImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int effectIndex = customCommandsBox.getChildren().indexOf(customCommandImage);
                addCommandToBox(customCommandImage.getImage(),customEffects.get(effectIndex));
                addCommand(customCommandToAdd);
            }
        });

        customCommandsBox.getChildren().addAll(customCommandImage);
        setDragDrop(customCommandImage);
        handleResetAction(null);
    }

    @FXML
    private void handleAddStepForwardAction(Event event) { //
        commandSequence.addCommand(new StepForwardCommand(boardData));
//        commandSeq.setText(commandSeq.getText() + "F\n");
        System.out.println("Step forward event fired.");
        addCommandToBox(forwardImage.getImage(),null);
    }

    @FXML
    private void handleAddTurnLeftAction(Event event) {
        commandSequence.addCommand(new TurnLeftCommand(boardData));
//        commandSeq.setText(commandSeq.getText() + "L\n");
        System.out.println("Turn left event fired.");
        addCommandToBox(leftImage.getImage(),null);
    }

    @FXML
    private void handleAddTurnRightAction(Event event) {
        commandSequence.addCommand(new TurnRightCommand(boardData));
//        commandSeq.setText(commandSeq.getText() + "R\n");
        System.out.println("Turn right event fired.");
        addCommandToBox(rightImage.getImage(),null);
    }

    @FXML
    private void handleRemoveLastCommandAction(Event event) {
        if (commandSequence.getSize() > 0) {
            commandSequence.removeLastCommand();
//            commandSeq.setText(commandSeq.getText().substring(0, commandSeq.getText().length() - 2));
            commandBox.getChildren().remove(commandSequence.getSize());
        }
        System.out.println("Remove last command event fired.");

    }

    @FXML
    private void handleClearCommandSequenceAction(Event event) {
        commandBox.getChildren().clear();
        commandSequence = new CommandSequence(boardData);
//        commandSeq.setText("");
        System.out.println("Clear sequence event fired.");
    }

    @FXML
    private void handleExecuteCommandSequenceAction(Event event) {
        //disable execution and set opacity to 0 for its button
        executeImage.setOnMouseClicked(null);
        executeImage.setOpacity(0);

        //get starting position before changes in model
        int startX = boardData.getTurtle().getX();
        int startY = boardData.getTurtle().getY();
        info.setText(commandSequence.execute());
        System.out.println("Execute event fired.");

        //clear the turtle layer
        gc_turtle.setFill(Color.TRANSPARENT);
        gc_turtle.fillRect(0, 0, boardCanvas.getHeight(), boardCanvas.getWidth());

        //animate the movements and wipe turtle memory afterwards
        animate(boardData.getTurtle().getMemory(), startX, startY);
        boardData.getTurtle().wipeMemory();

        //TODO
        //winning condition check
        if (info.getText().equals("Great! :)")) {
            nextLevelImage.setVisible(true);
        }
    }

    @FXML
    private void handleResetAction(Event event) {

        //revet execute button to its original state
        executeImage.setOnMouseClicked(this::handleExecuteCommandSequenceAction);
        executeImage.setOpacity(100);
        System.out.println("Reset event fired.");

        //reset data and wipe turtle memory
        setData(DataGenerator.generateGameData(GameAppController.lvl, boardData));
        boardData.getTurtle().wipeMemory();
        commandBox.getChildren().clear();
//        commandSeq.setText("");
        info.setText(GameAppController.lvl + "/5");

    }

    @FXML
    private void handleNextLevelAction(Event event) {
        System.out.println("Next level event fired.");

        //revert execute button to its original state
        executeImage.setOnMouseClicked(this::handleExecuteCommandSequenceAction);
        executeImage.setOpacity(100);

        //increase level number and unlock the next one, add unlocked level to choicebox
        if (GameAppController.lvl < 5) GameAppController.lvl++;
        if (DataGenerator.unlockLevel(GameAppController.lvl)) {
            levelBox.getItems().add("level " + String.format("%d", GameAppController.lvl));
        }

        //load next level
        setData(DataGenerator.generateGameData(GameAppController.lvl, boardData));
//        commandSeq.setText("");
        info.setText(GameAppController.lvl + "/5");

        //disable next level button
        nextLevelImage.setVisible(false);
        levelBox.getSelectionModel().select(GameAppController.lvl - 1);
        commandBox.getChildren().clear();
    }

    @FXML
    private void handleLoopAction(Event event) {
        appController.showLoopDialog();
    }

    private void addCommandToBox(Image image, Effect effect) {
        //prepare ImageView
        ImageView imageToAdd = new ImageView();
        imageToAdd.setEffect(shadow);
        imageToAdd.setFitHeight(50);
        imageToAdd.setFitWidth(50);
        imageToAdd.setImage(image);
        imageToAdd.setVisible(true);

        if(effect!=null){
            imageToAdd.setEffect(effect);
        }

        //prepare container for ImageView, set padding and insert image
        HBox container = new HBox();
        container.setPadding(new Insets(0, 5, 5, 0));
        container.getChildren().add(imageToAdd);


        //add command image to commandBox
        commandBox.getChildren().addAll(container);

        //on click delete
        setDeleteOnClick(container);
    }

    public void addLoopToBox(LoopCommand loop) {
        try { //loopBox - box to add to commands

            //preparing loopBox
            HBox loopBox = new HBox();
            loopBox.setEffect(shadow);
            loopBox.setMaxHeight(50);
            loopBox.setPadding(new Insets(5, 5, 5, 5));
            loopBox.setStyle("-fx-background-color: #b40093;");

            //for every command in loop, get its image, create a container with padding
            // and add it to loopBox
            for (ITurtleCommand command : loop.getCommands()) {
                HBox container = new HBox();
                container.setPadding(new Insets(0, 5, 0, 0));
                Image i = new Image(new FileInputStream(command.getImageURL()));
                ImageView imageToAdd = new ImageView();
                imageToAdd.setEffect(new DropShadow(5,Color.BLACK));
                imageToAdd.setFitWidth(40);
                imageToAdd.setFitHeight(40);
                imageToAdd.setImage(i);
                imageToAdd.setVisible(true);
                container.getChildren().add(imageToAdd);
                loopBox.getChildren().add(container);
            }

            //add iterations number to loopBox
            Text iters = new Text(" " + loop.getIterations().toString());
            iters.setTextAlignment(TextAlignment.CENTER);
            iters.setFont(new Font(30));
            iters.setFill(Color.WHITE);
            iters.setEffect(new DropShadow(5,Color.BLACK));
            loopBox.getChildren().add(iters);

            setDeleteOnClick(loopBox);

            //add loopBox to commandBox
            commandBox.getChildren().add(loopBox);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setDeleteOnClick(Node container){
        container.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HBox c = (HBox) event.getSource();
                int index = c.getParent().getChildrenUnmodifiable().indexOf(c);

                commandSequence.removeCommand(index);
                commandBox.getChildren().remove(c);
            }
        });
    }

    public void visitField(int x, int y) {
        gc_board.setFill(Color.BLUE);
        gc_board.fillRect(border + fieldSize * x + padding,
                border + fieldSize * y + padding,
                fieldSize - 2 * padding,
                fieldSize - 2 * padding);
    }

    public void drawBoard() {
        gc_turtle.clearRect(0, 0, boardCanvas.getHeight(), boardCanvas.getWidth());


        for (int i = 0; i < boardData.getFields().length; i++) {
            for (int j = 0; j < boardData.getFields().length; j++) {
                if (boardData.getFields()[j][i].isVisible()) {
                    gc_board.setEffect(shadow);
                    gc_board.setFill(Color.BROWN);
                    gc_board.fillRect(
                            border + fieldSize * i + padding,
                            border + fieldSize * j + padding,
                            fieldSize - 2 * padding,
                            fieldSize - 2 * padding);
                    gc_board.setEffect(null);
                }
            }
        }

    }

    public void animate(LinkedList<MoveType> steps, int startX, int startY) {

        //prepare starting coordinates on canvas
        DoubleProperty x = new SimpleDoubleProperty(border + startX * fieldSize + 0.15*fieldSize);
        DoubleProperty y = new SimpleDoubleProperty(border + startY * fieldSize + 0.15*fieldSize);
        DoubleProperty r = new SimpleDoubleProperty(0);
        SequentialTransition s = new SequentialTransition();

        //copy to ending coordinates
        int xEnd = x.intValue(), yEnd = y.intValue(), rEnd = r.intValue();

        for (MoveType step : steps) {

            //determine the offset
            switch (step) {
                case Up:
                    yEnd -= fieldSize;
                    break;
                case Down:
                    yEnd += fieldSize;
                    break;
                case Left:
                    xEnd -= fieldSize;
                    break;
                case Right:
                    xEnd += fieldSize;
                    break;
                case RLeft:
                    rEnd -= 90;
                    break;
                case RRight:
                    rEnd += 90;
                    break;
            }

            //create keyvalues for animation
            KeyValue px = new KeyValue(x, xEnd);
            KeyValue py = new KeyValue(y, yEnd);
            KeyValue pr = new KeyValue(r, rEnd);

            //create an animation for the step
            KeyFrame kf = new KeyFrame(Duration.seconds(0.5), px, py, pr);

            //create a timeline from the animation and add it to sequence with a pause
            Timeline t = new Timeline(kf);
            Timeline pause = new Timeline(new KeyFrame(Duration.seconds(0.2)));
            s.getChildren().addAll(t, pause);

        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);

                //clear the turtle layer
                gc_turtle.clearRect(0, 0, turtleCanvas.getWidth(), turtleCanvas.getHeight());

                //visit a field that the turtle is on roght now
                visitField((int) (x.intValue() / fieldSize), (int) (y.intValue() / fieldSize));

                //rotate the turtle image if needed
                turtleImage.setRotate(r.doubleValue());
                double width = sin(toRadians(abs(r.doubleValue()) % 90)) + cos(toRadians(abs(r.doubleValue()) % 90));
                double margin = (0.70*fieldSize * width - 0.70*fieldSize) / 2;

                //draw the turtle
                gc_turtle.setEffect(shadow);
                gc_turtle.drawImage(turtleImage.snapshot(params, null), x.intValue() - margin, y.intValue() - margin, 0.70*fieldSize * width, 0.70*fieldSize * width);
                gc_turtle.setEffect(null);
            }
        };

        //on sequence finish, stop the timer
        s.setOnFinished(event -> {
            timer.stop();
            //what to do after animation ends
        });

        //start the timer and animation
        timer.start();
        s.play();
    }

    public void setData(Board board) {
        this.boardData = board;
        this.commandSequence = new CommandSequence(board);
        fieldSize = (boardCanvas.getWidth() - 2*border) / board.getBoardSize();
        turtleImage.setRotate(0);
        gc_turtle.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
        gc_board.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
        drawBoard();
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        gc_turtle.setEffect(shadow);
        gc_turtle.drawImage(turtleImage.snapshot(params, null),
                border + fieldSize * boardData.getTurtle().getX() + 0.15 * fieldSize,
                border + fieldSize * boardData.getTurtle().getY() + 0.15 * fieldSize,
                0.70 * fieldSize,
                0.70 * fieldSize);
        gc_turtle.setEffect(null);

        for (ImageView iv : commandImages){
            iv.setVisible(false);
        }
        customCommand.setVisible(false);

        for (Object command : DataGenerator.getAvailiableCommandsForLvl(GameAppController.lvl)){
            switch ((String) command){
                case "forward":{
                    forwardImage.setVisible(true); break;

                }
                case "left":{
                    leftImage.setVisible(true); break;
                }
                case "right":{
                    rightImage.setVisible(true); break;
                }
                case "loop":{
                    loopImage.setVisible(true); break;
                }
                case "custom":{
                    customCommand.setVisible(true); break;
                }
            }
        }
    }

    public void setAppController(GameAppController appController) {
        this.appController = appController;
    }
}