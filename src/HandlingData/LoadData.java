package HandlingData;

import GameObjects.FallingObjects.Factories.BombFactory;
import GameObjects.FallingObjects.Factories.FruitFactory;
import GameObjects.FallingObjects.Factories.HalfFruitsFactory;
import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import GameObjects.FruitJuiceEffect.FruitJuiceEffect;
import GameObjects.FruitJuiceEffect.FruitJuiceExplosions.BlueFruitJuiceExplosion;
import GameObjects.FruitJuiceEffect.FruitJuiceExplosions.FruitJuiceExplosionFactory;
import GameTypes.ArcadeGameMemento;
import GameTypes.ClassicGameMemento;
import Players.Player;
import Players.PlayersCareTaker;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Vector;

public class LoadData {

    private PlayersCareTaker playersCareTaker;

    private String xmlFilePath;

    public LoadData() {
        playersCareTaker = new PlayersCareTaker();

        xmlFilePath = "src/HandlingData/fruitNinjaData";
    }

    public void loadData() {

        try {
            File file = new File(xmlFilePath);
            if (file.length() == 0)
                return;

            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = DBF.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFilePath);

            NodeList playersList = document.getElementsByTagName("player");

            loadPlayers(playersList);

        } catch (Exception e) {
            System.out.println("There is an error occurred while loading data.");
        }

    }

    private void loadPlayers(NodeList playersList) {
        if (playersList == null)
            return;

        Node node;
        Element element;

        for (int i = 0; i < playersList.getLength(); i++) {
            node = playersList.item(i);

            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                loadPlayer(element);
            }

        }

    }

    private void loadPlayer(Element playerNode) {
        Player player = new Player();

        String name;
        int classicBestScore;
        int arcadeBestScore;
        ClassicGameMemento classicGameMemento;
        ArcadeGameMemento arcadeGameMemento;

        name = playerNode.getElementsByTagName("playerName").item(0).getTextContent();
        classicBestScore = Integer.parseInt(playerNode.getElementsByTagName("classicBestScore").item(0).getTextContent());
        arcadeBestScore = Integer.parseInt(playerNode.getElementsByTagName("arcadeBestScore").item(0).getTextContent());

        classicGameMemento = loadPlayerClassicGameMemento(playerNode.getElementsByTagName("classicGame").item(0));

        arcadeGameMemento = loadPlayerArcadeGameMemento(playerNode.getElementsByTagName("arcadeGame").item(0));

        player.setName(name);
        player.setClassicBestScore(classicBestScore);
        player.setArcadeBestScore(arcadeBestScore);
        player.setClassicGameMemento(classicGameMemento);
        player.setArcadeGameMemento(arcadeGameMemento);

        playersCareTaker.addMemento(player.getData());
    }

    private ArcadeGameMemento loadPlayerArcadeGameMemento(Node arcadeGameNode) {
        Element arcadeGameElement = (Element) arcadeGameNode;

        if (arcadeGameElement.getElementsByTagName("playerNameArcadeMode").item(0) == null)
            return null;

        String playerName = arcadeGameElement.getElementsByTagName("playerNameArcadeMode").item(0).getTextContent();
        int currentScore = Integer.parseInt(arcadeGameElement.getElementsByTagName("currentScore").item(0).getTextContent());
        int currentDifficultyStage = Integer.parseInt(arcadeGameElement.getElementsByTagName("currentDifficultyStage").item(0).getTextContent());
        double addingObjectsRate = Double.parseDouble(arcadeGameElement.getElementsByTagName("addingObjectsRate").item(0).getTextContent());
        double objectsMovingRate = Double.parseDouble(arcadeGameElement.getElementsByTagName("objectsMovingRate").item(0).getTextContent());
        double objectsRotateRate = Double.parseDouble(arcadeGameElement.getElementsByTagName("objectsRotateRate").item(0).getTextContent());
        int fruitsRatio = Integer.parseInt(arcadeGameElement.getElementsByTagName("fruitsRatio").item(0).getTextContent());
        int bombsRatio = Integer.parseInt(arcadeGameElement.getElementsByTagName("bombsRatio").item(0).getTextContent());
        int specialFruitsRatio = Integer.parseInt(arcadeGameElement.getElementsByTagName("specialFruitsRatio").item(0).getTextContent());
        boolean isMuted = arcadeGameElement.getElementsByTagName("isMuted").item(0).getTextContent().equals("true");

        int currentTime = Integer.parseInt(arcadeGameElement.getElementsByTagName("currentTime").item(0).getTextContent());
        boolean freezeIsActivated = arcadeGameElement.getElementsByTagName("freezeIsActivated").item(0).getTextContent().equals("true");
        double currentFreezeDurationTime = Double.parseDouble(arcadeGameElement.getElementsByTagName("currentFreezeDurationTime").item(0).getTextContent());
        boolean speedUpIsActivated = arcadeGameElement.getElementsByTagName("speedUpIsActivated").item(0).getTextContent().equals("true");
        double currentSpeedUpDurationTime = Double.parseDouble(arcadeGameElement.getElementsByTagName("currentSpeedUpDurationTime").item(0).getTextContent());

        Vector<FallingObjects> objectsVector = loadFallingObjectVector(arcadeGameElement.getElementsByTagName("object"));
        Vector<FruitJuiceEffect> fruitJuiceVector = loadFruitJuiceVector(arcadeGameElement.getElementsByTagName("effect"));

        ArcadeGameMemento arcadeGameMemento = new ArcadeGameMemento();

        arcadeGameMemento.setPlayerName(playerName);

        arcadeGameMemento.setObjectsVector(objectsVector);
        arcadeGameMemento.setFruitJuiceVector(fruitJuiceVector);

        arcadeGameMemento.setCurrentScore(currentScore);

        arcadeGameMemento.setCurrentDifficultyStage(currentDifficultyStage);
        arcadeGameMemento.setAddingObjectsRate(addingObjectsRate);
        arcadeGameMemento.setObjectsMovingRate(objectsMovingRate);
        arcadeGameMemento.setObjectsRotateRate(objectsRotateRate);
        arcadeGameMemento.setFruitsRatio(fruitsRatio);
        arcadeGameMemento.setBombsRatio(bombsRatio);
        arcadeGameMemento.setSpecialFruitsRatio(specialFruitsRatio);

        arcadeGameMemento.setMuted(isMuted);

        arcadeGameMemento.setCurrentTime(currentTime);
        arcadeGameMemento.setFreezeIsActivated(freezeIsActivated);
        arcadeGameMemento.setSpeedUpIsActivated(speedUpIsActivated);
        arcadeGameMemento.setCurrentFreezeDurationTime(currentFreezeDurationTime);
        arcadeGameMemento.setCurrentSpeedUpDurationTime(currentSpeedUpDurationTime);

        return arcadeGameMemento;
    }

    private ClassicGameMemento loadPlayerClassicGameMemento(Node classicGameNode) {
        Element classicGameElement = (Element) classicGameNode;

        if (classicGameElement.getElementsByTagName("playerNameClassicMode").item(0) == null)
            return null;

        String playerName = classicGameElement.getElementsByTagName("playerNameClassicMode").item(0).getTextContent();
        int currentScore = Integer.parseInt(classicGameElement.getElementsByTagName("currentScore").item(0).getTextContent());
        int currentDifficultyStage = Integer.parseInt(classicGameElement.getElementsByTagName("currentDifficultyStage").item(0).getTextContent());;
        double addingObjectsRate = Double.parseDouble(classicGameElement.getElementsByTagName("addingObjectsRate").item(0).getTextContent());;
        double objectsMovingRate = Double.parseDouble(classicGameElement.getElementsByTagName("objectsMovingRate").item(0).getTextContent());;
        double objectsRotateRate = Double.parseDouble(classicGameElement.getElementsByTagName("objectsRotateRate").item(0).getTextContent());;
        int fruitsRatio = Integer.parseInt(classicGameElement.getElementsByTagName("fruitsRatio").item(0).getTextContent());;
        int bombsRatio = Integer.parseInt(classicGameElement.getElementsByTagName("bombsRatio").item(0).getTextContent());;
        int specialFruitsRatio = Integer.parseInt(classicGameElement.getElementsByTagName("specialFruitsRatio").item(0).getTextContent());;
        int missedFruitsCount = Integer.parseInt(classicGameElement.getElementsByTagName("missedFruitsCount").item(0).getTextContent());;
        boolean isMuted = classicGameElement.getElementsByTagName("isMuted").item(0).getTextContent().equals("true");

        Vector<FallingObjects> objectsVector = loadFallingObjectVector(classicGameElement.getElementsByTagName("object"));
        Vector<FruitJuiceEffect> fruitJuiceVector = loadFruitJuiceVector(classicGameElement.getElementsByTagName("effect"));

        ClassicGameMemento classicGameMemento = new ClassicGameMemento();

        classicGameMemento.setPlayerName(playerName);

        classicGameMemento.setObjectsVector(objectsVector);
        classicGameMemento.setFruitJuiceVector(fruitJuiceVector);

        classicGameMemento.setMissedFruitsCount(missedFruitsCount);

        classicGameMemento.setCurrentScore(currentScore);

        classicGameMemento.setCurrentDifficultyStage(currentDifficultyStage);
        classicGameMemento.setAddingObjectsRate(addingObjectsRate);
        classicGameMemento.setObjectsMovingRate(objectsMovingRate);
        classicGameMemento.setObjectsRotateRate(objectsRotateRate);
        classicGameMemento.setFruitsRatio(fruitsRatio);
        classicGameMemento.setBombsRatio(bombsRatio);
        classicGameMemento.setSpecialFruitsRatio(specialFruitsRatio);

        classicGameMemento.setMuted(isMuted);

        return classicGameMemento;
    }

    private Vector<FruitJuiceEffect> loadFruitJuiceVector(NodeList effectsList) {
        Node node;
        Element element;
        Vector<FruitJuiceEffect> fruitJuiceEffectVector = new Vector<>();

        for (int i = 0; i < effectsList.getLength(); i++) {
            node = effectsList.item(i);

            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                fruitJuiceEffectVector.add(loadFruitJuiceEffect(element));
            }

        }

        return fruitJuiceEffectVector;
    }

    private FruitJuiceEffect loadFruitJuiceEffect(Element element) {
        String effectFruitTypeString = element.getElementsByTagName("effectFruitType").item(0).getTextContent();
        FallingObjectType effectFruitType = getFallingObjectType(effectFruitTypeString);
        double fadingCurrentTime = Double.parseDouble(element.getElementsByTagName("fadingCurrentTime").item(0).getTextContent());
        double layoutX = Double.parseDouble(element.getElementsByTagName("layoutX").item(0).getTextContent());
        double layoutY = Double.parseDouble(element.getElementsByTagName("layoutY").item(0).getTextContent());

        FruitJuiceEffect effect;
        if (effectFruitType != null)
            effect = new FruitJuiceExplosionFactory().createJuiceExplosion(effectFruitType);
        else
            effect = new BlueFruitJuiceExplosion(120, 200);

        effect.setLayoutX(layoutX);
        effect.setLayoutY(layoutY);
        effect.getFadingTimeline().jumpTo(Duration.millis(fadingCurrentTime));

        return effect;
    }

    private Vector<FallingObjects> loadFallingObjectVector(NodeList objectNodeList) {
        Node node;
        Element element;
        Vector<FallingObjects> fallingObjectsVector = new Vector<>();

        for (int i = 0; i < objectNodeList.getLength(); i++) {

            node = objectNodeList.item(i);

            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                fallingObjectsVector.add(loadFallingObject(element));
            }

        }

        return fallingObjectsVector;

    }

    private FallingObjects loadFallingObject(Element objectElement) {

        String fallingObjectTypeString = objectElement.getElementsByTagName("type").item(0).getTextContent();
        FallingObjectType fallingObjectType = getFallingObjectType(fallingObjectTypeString);
        int sceneHeight = Integer.parseInt(objectElement.getElementsByTagName("sceneHeight").item(0).getTextContent());
        int sceneWidth = Integer.parseInt(objectElement.getElementsByTagName("sceneWidth").item(0).getTextContent());
        boolean rightCurved = objectElement.getElementsByTagName("rightCurved").item(0).getTextContent().equals("true");
        double maxHeight = Double.parseDouble(objectElement.getElementsByTagName("maxHeight").item(0).getTextContent());
        double xStartPoint = Double.parseDouble(objectElement.getElementsByTagName("xStartPoint").item(0).getTextContent());
        double projectionRange = Double.parseDouble(objectElement.getElementsByTagName("projectionRange").item(0).getTextContent());
        double movingCurrentTime = Double.parseDouble(objectElement.getElementsByTagName("movingCurrentTime").item(0).getTextContent());
        double movingCurrentRate = Double.parseDouble(objectElement.getElementsByTagName("movingCurrentRate").item(0).getTextContent());
        double rotatingCurrentTime = Double.parseDouble(objectElement.getElementsByTagName("rotatingCurrentTime").item(0).getTextContent());
        double rotatingCurrentRate = Double.parseDouble(objectElement.getElementsByTagName("rotatingCurrentRate").item(0).getTextContent());

        FallingObjects fallingObject = createFallingObject(fallingObjectType);
        FallingRandomData objectRandomData = new FallingRandomData(sceneHeight, sceneWidth, rightCurved, maxHeight, xStartPoint, projectionRange);
        ProjectionTimeLine projectionTimeLine = new ProjectionTimeLine(fallingObject, objectRandomData);
        fallingObject.setProjectionTimeLine(projectionTimeLine);
        projectionTimeLine.getMovingTimeLine().setRate(movingCurrentRate);
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(movingCurrentTime));
        projectionTimeLine.getRotationTimeLine().setRate(rotatingCurrentRate);
        projectionTimeLine.getRotationTimeLine().jumpTo(Duration.millis(rotatingCurrentTime));

        fallingObject.setTranslateX(objectRandomData.getXStartPoint());
        fallingObject.setTranslateY(objectRandomData.getSceneHeight());

        return fallingObject;
    }

    private FallingObjects createFallingObject(FallingObjectType type) {
        if (objectIsBomb(type))
            return new BombFactory().createBomb(type);
        else if (objectIsFruit(type))
            return new FruitFactory().createFruit(type);
        else
            return new HalfFruitsFactory().createHalfFruit(type);
    }

    private boolean objectIsBomb(FallingObjectType type) {
        return type == FallingObjectType.dangerousBomb || type == FallingObjectType.fatalBomb
                || type == FallingObjectType.timeBomb;
    }

    private boolean objectIsFruit(FallingObjectType type) {
        return type == FallingObjectType.banana || type == FallingObjectType.goldenWaterMelon
                || type == FallingObjectType.kiwi || type == FallingObjectType.orange
                || type == FallingObjectType.pineapple || type == FallingObjectType.purpleBanana
                || type == FallingObjectType.redApple || type == FallingObjectType.waterMelon;
    }

    private FallingObjectType getFallingObjectType(String objectType) {

        if (objectType.equals("null"))
            return null;

        for (FallingObjectType type : FallingObjectType.values()) {
            if (type.toString().equals(objectType))
                return type;

        }

        return null;
    }

    public PlayersCareTaker getPlayersCareTaker() {
        return playersCareTaker;
    }
}
