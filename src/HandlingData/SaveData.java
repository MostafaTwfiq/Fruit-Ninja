package HandlingData;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FruitJuiceEffect.FruitJuiceEffect;
import GameObjects.FruitJuiceEffect.FruitJuiceExplosions.*;
import GameTypes.ArcadeGameMemento;
import GameTypes.ClassicGameMemento;
import Players.PlayerMemento;
import Players.PlayersCareTaker;
import javafx.animation.Timeline;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Vector;

public final class SaveData {

    private static SaveData dataSaver;

    private PlayersCareTaker playersCareTaker;

    private String xmlFilePath;

    private SaveData(PlayersCareTaker playersCareTaker) {
        this.playersCareTaker = playersCareTaker;

        this.xmlFilePath = "src/HandlingData/fruitNinjaData";
    }

    public static SaveData createDataSaver(PlayersCareTaker playersCareTaker) {
        if (dataSaver == null)
            return dataSaver = new SaveData(playersCareTaker);

        return dataSaver;
    }

    public void saveData() {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            //add elements to Document:
            Element fruitNinjaElement = document.createElementNS("", "fruitNinja");

            //append root element to document:
            document.appendChild(fruitNinjaElement);

            //create sub childes elements:
            Element playersElement = document.createElement("players");

            //append sub childes elements to root element:
            fruitNinjaElement.appendChild(playersElement);

            savePlayers(document, playersElement);

            //for output to file:
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //for good print into file:
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);

            //write to file:
            StreamResult file = new StreamResult(new File(this.xmlFilePath));

            //write data:
            transformer.transform(source, file);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void savePlayers(Document document, Element playersElement) {

        for (int i = 0; i < playersCareTaker.getLength(); i++)
            playersElement.appendChild(createPlayerDataNode(document, playersCareTaker.getMemento(i)));

    }

    private Element createPlayerDataNode(Document document, PlayerMemento playerMemento) {
        Element player = document.createElement("player");

        player.appendChild(createTextNode(document, "playerName", playerMemento.getPlayerName()));
        player.appendChild(createTextNode(document, "classicBestScore",
                String.format("%d", playerMemento.getClassicBestScore())));

        player.appendChild(createTextNode(document, "arcadeBestScore",
                String.format("%d", playerMemento.getArcadeBestScore())));

        player.appendChild(createClassicGameMementoNode(document, playerMemento.getClassicGameMemento()));

        player.appendChild(createArcadeGameMementoNode(document, playerMemento.getArcadeGameMemento()));

        return player;
    }

    private Element createArcadeGameMementoNode(Document document, ArcadeGameMemento memento) {

        Element arcadeGameElement = document.createElement("arcadeGame");

        if (memento == null)
            return arcadeGameElement;

        arcadeGameElement.appendChild(createTextNode(document, "playerNameArcadeMode", memento.getPlayerName()));
        arcadeGameElement.appendChild(createTextNode(document, "currentScore",
                String.format("%d", memento.getCurrentScore())));

        arcadeGameElement.appendChild(createTextNode(document, "currentDifficultyStage",
                String.format("%d", memento.getCurrentDifficultyStage())));

        arcadeGameElement.appendChild(createTextNode(document, "addingObjectsRate",
                String.format("%f", memento.getAddingObjectsRate())));

        arcadeGameElement.appendChild(createTextNode(document, "objectsMovingRate",
                String.format("%f", memento.getObjectsMovingRate())));

        arcadeGameElement.appendChild(createTextNode(document, "objectsRotateRate",
                String.format("%f", memento.getObjectsRotateRate())));

        arcadeGameElement.appendChild(createTextNode(document, "fruitsRatio",
                String.format("%d", memento.getFruitsRatio())));

        arcadeGameElement.appendChild(createTextNode(document, "bombsRatio",
                String.format("%d", memento.getBombsRatio())));

        arcadeGameElement.appendChild(createTextNode(document, "specialFruitsRatio",
                String.format("%d", memento.getSpecialFruitsRatio())));

        arcadeGameElement.appendChild(createTextNode(document, "isMuted",
                String.format("%b", memento.isMuted())));

        arcadeGameElement.appendChild(createTextNode(document, "currentTime",
                String.format("%d", memento.getCurrentTime())));

        arcadeGameElement.appendChild(createTextNode(document, "freezeIsActivated",
                String.format("%b", memento.isFreezeIsActivated())));

        arcadeGameElement.appendChild(createTextNode(document, "currentFreezeDurationTime",
                String.format("%f", memento.getCurrentFreezeDurationTime())));

        arcadeGameElement.appendChild(createTextNode(document, "speedUpIsActivated",
                String.format("%b", memento.isSpeedUpIsActivated())));

        arcadeGameElement.appendChild(createTextNode(document, "currentSpeedUpDurationTime",
                String.format("%f", memento.getCurrentSpeedUpDurationTime())));

        arcadeGameElement.appendChild(createFallingObjectsNode(document, memento.getObjectsVector()));

        arcadeGameElement.appendChild(createFruitJuiceEffectsNode(document, memento.getFruitJuiceVector()));

        return arcadeGameElement;

    }

    private Element createClassicGameMementoNode(Document document, ClassicGameMemento memento) {

        Element classicGameElement = document.createElement("classicGame");

        if (memento == null)
            return classicGameElement;

        classicGameElement.appendChild(createTextNode(document, "playerNameClassicMode", memento.getPlayerName()));
        classicGameElement.appendChild(createTextNode(document, "currentScore",
                String.format("%d", memento.getCurrentScore())));

        classicGameElement.appendChild(createTextNode(document, "currentDifficultyStage",
                String.format("%d", memento.getCurrentDifficultyStage())));

        classicGameElement.appendChild(createTextNode(document, "addingObjectsRate",
                String.format("%f", memento.getAddingObjectsRate())));

        classicGameElement.appendChild(createTextNode(document, "objectsMovingRate",
                String.format("%f", memento.getObjectsMovingRate())));

        classicGameElement.appendChild(createTextNode(document, "objectsRotateRate",
                String.format("%f", memento.getObjectsRotateRate())));

        classicGameElement.appendChild(createTextNode(document, "fruitsRatio",
                String.format("%d", memento.getFruitsRatio())));

        classicGameElement.appendChild(createTextNode(document, "bombsRatio",
                String.format("%d", memento.getBombsRatio())));

        classicGameElement.appendChild(createTextNode(document, "specialFruitsRatio",
                String.format("%d", memento.getSpecialFruitsRatio())));

        classicGameElement.appendChild(createTextNode(document, "missedFruitsCount",
                String.format("%d", memento.getMissedFruitsCount())));

        classicGameElement.appendChild(createTextNode(document, "isMuted",
                String.format("%b", memento.isMuted())));

        classicGameElement.appendChild(createFallingObjectsNode(document, memento.getObjectsVector()));

        classicGameElement.appendChild(createFruitJuiceEffectsNode(document, memento.getFruitJuiceVector()));

        return classicGameElement;
    }

    private Element createFruitJuiceEffectsNode(Document document, Vector<FruitJuiceEffect> fruitJuiceVector) {

        Element fruitJuiceEffectNode = document.createElement("fruitJuiceEffect");

        for (int i = 0; i < fruitJuiceVector.size(); i++) {
            fruitJuiceEffectNode.appendChild(createFruitJuiceEffectNode(document, fruitJuiceVector.get(i)));
        }

        return fruitJuiceEffectNode;

    }

    private Element createFruitJuiceEffectNode(Document document, FruitJuiceEffect effect) {

        Element effectNode = document.createElement("effect");

        FallingObjectType effectFruitType = getEffectFruitType(effect);
        if (effectFruitType != null)
            effectNode.appendChild(createTextNode(document, "effectFruitType",
                effectFruitType.toString()));
        else
            effectNode.appendChild(createTextNode(document, "effectFruitType", "null"));

        effectNode.appendChild(createTextNode(document, "fadingCurrentTime",
                String.format("%f", effect.getFadingTimeline().getCurrentTime().toMillis())));

        effectNode.appendChild(createTextNode(document, "layoutX",
                String.format("%f", effect.getLayoutX())));

        effectNode.appendChild(createTextNode(document, "layoutY",
                String.format("%f", effect.getLayoutY())));

        return effectNode;
    }

    private FallingObjectType getEffectFruitType(FruitJuiceEffect effect) {
        if (effect instanceof RedFruitJuiceExplosion)
            return FallingObjectType.waterMelon;
        else if (effect instanceof GoldFruitJuiceExplosion)
            return FallingObjectType.goldenWaterMelon;
        else if (effect instanceof GreenFruitJuiceExplosion)
            return FallingObjectType.kiwi;
        else if (effect instanceof OrangeFruitJuiceExplosion)
            return FallingObjectType.orange;
        else if (effect instanceof YellowFruitJuiceExplosion) {

            if (effect.getFitHeight() == 120 && effect.getFitWidth() == 200)
                return FallingObjectType.pineapple;
            else
                return FallingObjectType.redApple;

        }
        else if (effect instanceof TransparentFruitJuiceExplosion)
            return FallingObjectType.banana;

        return null; //null will be useful to know if it a blue explosion.
    }

    private Element createFallingObjectsNode(Document document, Vector<FallingObjects> fallingObjects) {

        Element fallingObjectsNode = document.createElement("fallingObjects");

        for (int i = 0; i < fallingObjects.size(); i++) {

            fallingObjectsNode.appendChild(createFallingObjectNode(document, fallingObjects.get(i)));
        }

        return fallingObjectsNode;
    }

    private Element createFallingObjectNode(Document document, FallingObjects object) {

        FallingRandomData objectData = object.getProjectionTimeLine().getFallingRandomData();
        Timeline movingTimeline = object.getProjectionTimeLine().getMovingTimeLine();
        Timeline rotateTimeline = object.getProjectionTimeLine().getRotationTimeLine();

        Element objectNode = document.createElement("object");
        objectNode.appendChild(createTextNode(document, "type",
                object.getFallingObjectType().toString()));

        objectNode.appendChild(createTextNode(document, "sceneHeight",
                String.format("%d", objectData.getSceneHeight())));

        objectNode.appendChild(createTextNode(document, "sceneWidth",
                String.format("%d", objectData.getSceneWidth())));

        objectNode.appendChild(createTextNode(document, "rightCurved",
                String.format("%b", objectData.isRightCurved())));

        objectNode.appendChild(createTextNode(document, "maxHeight",
                String.format("%f", objectData.getObjMaxHeight())));

        objectNode.appendChild(createTextNode(document, "xStartPoint",
                String.format("%f", objectData.getXStartPoint())));

        objectNode.appendChild(createTextNode(document, "projectionRange",
                String.format("%f", objectData.getProjectionRange())));

        objectNode.appendChild(createTextNode(document, "movingCurrentTime",
                String.format("%f", movingTimeline.getCurrentTime().toMillis())));

        objectNode.appendChild(createTextNode(document, "movingCurrentRate",
                String.format("%f", movingTimeline.getRate())));

        objectNode.appendChild(createTextNode(document, "rotatingCurrentTime",
                String.format("%f", rotateTimeline.getCurrentTime().toMillis())));

        objectNode.appendChild(createTextNode(document, "rotatingCurrentRate",
                String.format("%f", rotateTimeline.getRate())));

        return objectNode;

    }

    private Element createTextNode(Document document, String nodeName, String value) {
        Element textNode = document.createElement(nodeName);
        textNode.appendChild(document.createTextNode(value));
        return textNode;
    }

    public PlayersCareTaker getPlayersCareTaker() {
        return playersCareTaker;
    }
}
