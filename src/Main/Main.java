package Main;

import GameSystem.ScoreBoard.ScoreBoard;
import GameTypes.ArcadeGame;
import GameTypes.ClassicGame;
import HandlingData.LoadData;
import HandlingData.SaveData;
import Players.Player;
import Players.PlayersCareTaker;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private LoadData loader;
    private SaveData saver;

    private Player player;
    private PlayersCareTaker playersCareTaker;

    @Override
    public void start(Stage primaryStage) throws Exception{

        loader = new LoadData();
        loader.loadData();

        playersCareTaker = loader.getPlayersCareTaker();

        player = new Login(playersCareTaker).show();

        saver = SaveData.createDataSaver(playersCareTaker);

        showMain();
    }

    private void showMain() {
        Object nextStage = new FruitNinjaMain(player, playersCareTaker).show();
        checkForNextStage(nextStage);
    }

    private void checkForNextStage(Object nextStage) {
        if (nextStage instanceof ClassicGame)
            ((ClassicGame) nextStage).play();

        if (nextStage instanceof ArcadeGame)
            ((ArcadeGame) nextStage).play();

        if (nextStage instanceof ScoreBoard)
            ((ScoreBoard) nextStage).show();

        showMain();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
