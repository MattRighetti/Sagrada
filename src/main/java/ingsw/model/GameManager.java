/*
 *
 * Un GameManager per ogni partita
 * Board passata da SagradaGame al momento della scelta o creazione della nuova partita o preesistente
 *
 * Contiene tutti gli oggetti del gioco perché si occupa del setup della Board e della creazione della stessa
 *
 * SINGLETON
 *
 */

package ingsw.model;

import ingsw.model.cards.patterncard.*;
import ingsw.model.cards.privateoc.*;
import ingsw.model.cards.publicoc.*;
import ingsw.model.cards.toolcards.*;
import ingsw.utilities.Broadcaster;

import java.util.*;

/**
 * Class that handles the entire game process and modifies the model itself
 */
public class GameManager {
    private Board board;
    private int noOfAck;
    private List<Player> playerList;
    private List<PrivateObjectiveCard> privateObjectiveCards;
    private List<PublicObjectiveCard> publicObjectiveCards;
    private List<ToolCard> toolCards;
    private List<PatternCard> patternCards;
    private Round currentRound;

    /**
     * Creates an instance of GameManager with every object needed by the game itself and initializes its players
     * assigning to each of them a PrivateObjectiveCard and asking them to choose a PatternCard.
     *
     * @param players
     */
    public GameManager(List<Player> players) {
        playerList = players;
        setUpGameManager();
        this.board = new Board(choosePublicObjectiveCards(), chooseToolCards(), players);
    }

    private synchronized void setUpGameManager() {
        setUpPrivateObjectiveCards();

        for (Player player : playerList) {
            player.setPrivateObjectiveCard(privateObjectiveCards.get(0));
            privateObjectiveCards.remove(0);
        }

        setUpPublicObjectiveCards();
        setUpToolCards();
        setUpPatternCards();
    }

    private void setUpPatternCards() {
        this.patternCards = new LinkedList<>();
        this.patternCards.add(new AuroraeMagnificus());
        this.patternCards.add(new AuroraSagradis());
        this.patternCards.add(new Batllo());
        this.patternCards.add(new Bellesguard());
        this.patternCards.add(new ChromaticSplendor());
        this.patternCards.add(new Comitas());
        this.patternCards.add(new Firelight());
        this.patternCards.add(new Firmitas());
        this.patternCards.add(new FractalDrops());
        this.patternCards.add(new FulgorDelCielo());
        this.patternCards.add(new Gravitas());
        this.patternCards.add(new Industria());
        this.patternCards.add(new KaleidoscopicDream());
        this.patternCards.add(new LuxAstram());
        this.patternCards.add(new LuxMundi());
        this.patternCards.add(new LuzCelestial());
        this.patternCards.add(new RipplesOfLight());
        this.patternCards.add(new ShadowThief());
        this.patternCards.add(new SunCatcher());
        this.patternCards.add(new SunsGlory());
        this.patternCards.add(new SymphonyOfLight());
        this.patternCards.add(new ViaLux());
        this.patternCards.add(new Virtus());
        this.patternCards.add(new WaterOfLife());
        Collections.shuffle(patternCards);
    }

    private void setUpToolCards() {
        toolCards = new LinkedList<>();
        this.toolCards.add(new CopperFoilBurnisher());
        this.toolCards.add(new CorkBarckedStraightEdge());
        this.toolCards.add(new EglomiseBrush());
        this.toolCards.add(new FluxBrush());
        this.toolCards.add(new FluxRemover());
        this.toolCards.add(new GlazingHammer());
        this.toolCards.add(new GrindingStone());
        this.toolCards.add(new GrozingPliers());
        this.toolCards.add(new Lathekin());
        this.toolCards.add(new LensCutter());
        this.toolCards.add(new RunningPliers());
        this.toolCards.add(new TapWheel());

    }

    private void setUpPublicObjectiveCards() {
        publicObjectiveCards = new LinkedList<>();
        this.publicObjectiveCards.add(new ColorDiagonals());
        this.publicObjectiveCards.add(new ColorVariety());
        this.publicObjectiveCards.add(new ColumnShadeVariety());
        this.publicObjectiveCards.add(new DeepShades());
        this.publicObjectiveCards.add(new ColumnColorVariety());
        this.publicObjectiveCards.add(new LightShades());
        this.publicObjectiveCards.add(new MediumShades());
        this.publicObjectiveCards.add(new RowColorVariety());
        this.publicObjectiveCards.add(new RowShadeVariety());
        this.publicObjectiveCards.add(new ShadeVariety());

    }

    private void setUpPrivateObjectiveCards() {
        privateObjectiveCards = new LinkedList<>();
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.BLUE));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.GREEN));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.RED));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.PURPLE));
        this.privateObjectiveCards.add(new PrivateObjectiveCard(Color.YELLOW));
        Collections.shuffle(privateObjectiveCards);
    }

    private Set<ToolCard> chooseToolCards() {
        Collections.shuffle(toolCards);
        return new HashSet<>(toolCards.subList(0, 3));
    }

    private Set<PublicObjectiveCard> choosePublicObjectiveCards() {
        Collections.shuffle(publicObjectiveCards);
        return new HashSet<>(publicObjectiveCards.subList(0, 3));
    }

    private void pickPatternCards() {
        for (Player player : playerList) {
            Set<PatternCard> pickedPatternCards = new HashSet<>(patternCards.subList(0, 4));
            for (int i = 0; i < 4; i++) {
                patternCards.remove(0);
            }
            //TODO broadcast
        }
    }

    public List<Player> getPlayerList() {
        return playerList;
    }


    public PatternCard setPatternCardForPlayer(String username, PatternCard patternCard) {
        for (Player player : playerList) {
            if (player.getPlayerUsername().equals(username)) {
                player.setPatternCard(patternCard);
                noOfAck++;
            }
        }

        return patternCard;
    }

    /**
     * Method that waits for every users to choose a patternCard
     */
    public void waitForEveryPatternCard() {
        new Thread(() -> {
            while (noOfAck < 4) {

            }
            resetAck();
            startMatch();
        });
    }

    /**
     * Method that drafts the dice from the board and sends them to every user view
     */
    public void draftDiceFromBoard() {
        Broadcaster.broadcastResponseToAll(playerList, board.draftDice());
        waitForDiceAck();
    }

    /**
     * Method that stalls the program until every user has received every dice
     */
    private void waitForDiceAck() {
        while (noOfAck < 4) {
        }
        resetAck();

    }

    /**
     * Method called when an user selected a patternCard from the view
     *
     * @param player
     * @param toolCard
     */
    public void useToolCard(Player player, ToolCard toolCard) {
        //method toolCard.action(player.getPatternCard());
    }

    public void placeDice(Dice dice, int rowIndex, int columnIndex) {
        // TODO completa
        /*
        1 - controlla se il dado è nella lista dei draftedDice
        2 - posiziona il dado nella patternCard
        3 - rimuovi dado dalla draftedDice
        4 - aggiorna le view degli utenti
        5 - currentRound.makeMove();
         */
    }

    public void endTurn() {
        currentRound.setPlayerEndedTurn(true);
    }

    /**
     * Method that resets the received acks to zero
     */
    private void resetAck() {
        noOfAck = 0;
    }

    /**
     * Method that increments the acks received
     */
    private void ackReceived() {
        noOfAck++;
    }

    /**
     * Method that alerts the user to draft, this activates a button "Draft" in the view
     *
     * @param player
     */
    private void notifyDraftToPlayer(Player player) {
        player.notifyDraft();
    }

    /**
     * Method that opens a thread dedicated to the match
     */
    private void startMatch() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                notifyDraftToPlayer(playerList.get(0));
                startRound();
            }

        });
    }

    /**
     * Method that starts the single round
     */
    private void startRound() {
        currentRound = new Round(this);
        //Rounds going forward
        for (int i = 0; i < playerList.size(); i++) {
            currentRound.startForPlayer(playerList.get(i));
            do {

            } while (!currentRound.hasPlayerEndedTurn().get());
        }

        for (int i = playerList.size() - 1; i >= 0; i--) {
            currentRound.startForPlayer(playerList.get(i));
            do {

            } while (!currentRound.hasPlayerEndedTurn().get());
        }
    }

    /**
     * Method that notifies the player with a patternCard's mask which indicates the available positions in which
     * a dice can be placed
     *
     * @param player
     */
    public List<Boolean[][]> sendAvailablePositions(Player player) {
        return player.getPatternCard().computeAvailablePositions(board.getDraftedDice());
    }
}
