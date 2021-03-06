import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Bryan Quinn on 5/17/2016.
 *
 * This is the view for the tic tac toe game
 *
 */
public class GUI extends Application implements Observer {

    private BorderPane border;
    private Button[][] buttons;
    private Label message;
    private Model model;

    @Override
    public void init() {
        Parameters params = getParameters();
        int dimension = 0;
        try{
            dimension = Integer.parseInt(params.getRaw().get(0));
        } catch (Exception ex){
            System.out.println("Usage: dimension of game board");
            System.exit(-1);
        }
        this.model = new Model(dimension);
        this.model.addObserver(this);
        border = new BorderPane();
        this.buttons = new Button[dimension][dimension];
        this.message = new Label();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = makeButttons();
        grid.setAlignment(Pos.CENTER);

        Button restart = new Button("Restart");

        restart.setOnAction(e -> restart());

        HBox bottom = new HBox();
        bottom.setSpacing(10);
        bottom.getChildren().addAll(restart);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(10, 10, 10, 10));

        message = new Label("Let the game begin!");
        message.setTextAlignment(TextAlignment.CENTER);
        HBox top = new HBox();
        top.getChildren().add(message);
        top.setAlignment(Pos.CENTER);

        border.setTop(top);
        border.setPadding(new Insets(10, 10, 10, 10));
        border.setBottom(bottom);
        border.setCenter(grid);

        Scene scene = new Scene(border);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight() * (.5));
        primaryStage.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth() * (.5));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private GridPane makeButttons() {
        GridPane grid = new GridPane();
        for (int col = 0; col < this.model.getDim(); col++) {
            for (int row = 0; row < this.model.getDim(); row++) {
                Button current = new Button();
                current.setPadding(new Insets(10, 10, 10, 10));
                Image zeroImg = new Image(getClass().getResourceAsStream("resources/white.png"));
                ImageView zeroIcon = new ImageView(zeroImg);
                current.setGraphic(zeroIcon);
                int finalCol = col;
                int finalRow = row;
                current.setOnAction(e -> setTile(finalCol, finalRow));
                buttons[col][row] = current;
                grid.add(current, col, row);
            }
        }
        return grid;

    }

    private void setTile(int col, int row){
        model.addX(col,row);
    }

    private void setButtonBackground(Button button, String bgImgName) {
        Image laserImg = new Image(getClass().getResourceAsStream("resources/" + bgImgName));
        ImageView laserIcon = new ImageView(laserImg);
        button.setGraphic(laserIcon);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("resources/" + bgImgName).toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        button.setBackground(background);
    }

    private void restart(){
        //clear the board
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Calls update");
        for (int i = 0; i < model.getDim(); i++) {
            for (int j = 0; j < model.getDim(); j++) {
                if (model.getPosition(i,j) == 'X'){
                    setButtonBackground(buttons[i][j], "pillarX.png");
                    System.out.println("Changes button");
                } else if (model.getPosition(i,j) == 'O'){
                    setButtonBackground(buttons[i][j], "pillar0.png");
                    System.out.println("Changes button");
                }

            }

        }

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
