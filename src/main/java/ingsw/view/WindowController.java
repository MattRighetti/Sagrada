package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.Tuple;
import ingsw.view.nodes.DicePane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    @FXML
    private VBox windowFrameVBox;

    @FXML
    private GridPane patternCardGridPane;

    @FXML
    private Button breakWindowButton;

    private String username;

    private Map<String, Boolean[][]> availablePosition;
    private DicePane[][] dicePanes = new DicePane[4][5];
    private Dice selectedDice;
    private NetworkType networkType;
    private List<Tuple> selectedPositions = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                DicePane dicePane = new DicePane(i, j);
                dicePane.getStyleClass().add("grey");
                dicePane.setOpacity(0.8);
                DiceCursorMouseEvent(dicePane);
                patternCardGridPane.add(dicePane, j, i);
                dicePanes[i][j] = dicePane;
            }
        }
    }

    private void DiceCursorMouseEvent(DicePane dicePane) {
        dicePane.setOnMouseClicked(event -> {
            System.out.println("clicked");
            if (selectedDice != null) {
                patternCardGridPane.setCursor(Cursor.DEFAULT);
                networkType.placeDice(selectedDice, dicePane.getColumnIndex(), dicePane.getRowIndex());
            } else {
                System.out.println("No dice selected");
            }
            selectedDice = null;
        });
    }

    void setAvailablePosition(Map<String, Boolean[][]> availablePosition) {
        this.availablePosition = availablePosition;
    }

    public GridPane getPatternCardGridPane() {
        return patternCardGridPane;
    }

    void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    @FXML
    void onBreakWindowPressed(ActionEvent event) {
        System.out.println("Broken window");
    }

    void setGridPaneBackground(String patternCardName) {
        patternCardGridPane.getStyleClass().add(patternCardName);
    }

    void setSelectedDice(Dice dice) {
        this.selectedDice = dice;
    }

    public Dice getSelectedDice() {
        return selectedDice;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    void updatePatternCard(PatternCard patternCard) {

        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 5; k++) {
                DicePane dicePane = dicePanes[j][k];
                dicePane.getStyleClass().clear();
                if (patternCard.getGrid().get(j).get(k).getDice() != null) {
                    dicePane.getStyleClass().add(patternCard.getGrid().get(j).get(k).getDice().toString());
                    dicePane.getStyleClass().add("dicePaneImageSize");
                }
                DiceCursorMouseEvent(dicePane);
            }
        }
    }

    void updateAvailablePositions(String diceString) {
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 5; k++) {
                if (availablePosition.get(diceString) != null) {
                    System.out.print(availablePosition.get(diceString)[j][k] + "\t");
                    if (!availablePosition.get(diceString)[j][k]) {
                        (dicePanes[j][k]).getStyleClass().add("grey");
                        (dicePanes[j][k]).setDisable(true);
                    } else {
                        (dicePanes[j][k]).getStyleClass().remove("grey");
                        dicePanes[j][k].setDisable(false);
                    }
                } else dicePanes[j][k].getStyleClass().add("grey");
            }
            System.out.println();
        }
    }

    public void unsetSelectedDice() {
        selectedDice = null;
    }

    /**
     * Method used by EglomiseBrush, CopperFoilBurnisher toolcards
     */
    public void moveDiceinPatternCard() {
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 5; k++) {
                DicePane thisDicePane = dicePanes[j][k];

                thisDicePane.setOnMouseClicked(event -> {
                    if (selectedPositions.isEmpty()) {
                        if (!thisDicePane.getStyleClass().isEmpty()) {
                            selectedPositions.add(new Tuple(thisDicePane.getRowIndex(), thisDicePane.getColumnIndex()));
                            System.out.println(thisDicePane.getStyleClass().toString());
                            updateAvailablePositions(thisDicePane.getStyleClass().get(0) + thisDicePane.getRowIndex() + thisDicePane.getColumnIndex());
                        }
                    } else if (selectedPositions.size() == 1) {
                        if (thisDicePane.getStyleClass().isEmpty()) {
                            selectedPositions.add(new Tuple(thisDicePane.getRowIndex(), thisDicePane.getColumnIndex()));
                            networkType.copperFoilBurnisherMove(selectedPositions.get(0), selectedPositions.get(1));

                            selectedPositions.clear();
                        }
                    } else
                        selectedPositions.clear();
                });

            }
        }

    }

    public void corkBackedStraightedge() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                DicePane dicePane = dicePanes[i][j];
                dicePanes[i][j].setOnMouseClicked(event -> {
                    System.out.println("clicked");
                    if (selectedDice != null) {
                        patternCardGridPane.setCursor(Cursor.DEFAULT);
                        networkType.corkBackedStraightedgeMove(selectedDice, dicePane.getRowIndex(), dicePane.getColumnIndex());
                    } else {
                        System.out.println("No dice selected");
                    }
                    selectedDice = null;
                });
            }
        }
    }

    public void moveDiceinPatternCardLathekin() {
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 5; k++) {
                DicePane thisDicePane = dicePanes[j][k];
                thisDicePane.setOnMouseClicked(event -> lathekinMouseEvent(thisDicePane));
            }
        }

    }

    private void lathekinMouseEvent(DicePane thisDicePane) {
        if (selectedPositions.isEmpty()) {
            if (!thisDicePane.getStyleClass().isEmpty() && !thisDicePane.getStyleClass().contains("grey")) {
                selectedPositions.add(new Tuple(thisDicePane.getRowIndex(), thisDicePane.getColumnIndex()));
                System.out.println(thisDicePane.getStyleClass().toString());
                updateAvailablePositions(thisDicePane.getStyleClass().get(0) + thisDicePane.getRowIndex() + thisDicePane.getColumnIndex());
            }
        } else if (selectedPositions.size() == 1) {
            if (!thisDicePane.getStyleClass().contains("grey") ) {
                selectedPositions.add(new Tuple(thisDicePane.getRowIndex(), thisDicePane.getColumnIndex()));
                if (thisDicePane.getStyleClass().isEmpty())
                    networkType.lathekinMove(selectedPositions.get(0), selectedPositions.get(1), false);
                else
                    networkType.lathekinMove(selectedPositions.get(0), selectedPositions.get(1), true);
                selectedPositions.clear();
            } else {
                System.out.println("cannot place dice here " + thisDicePane.getStyleClass() );
            }
        } else
            selectedPositions.clear();
    }

    public void fluxBrushMove() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                DicePane dicePane = new DicePane(i, j);
                dicePane.setOnMouseClicked(event -> {
                    System.out.println("clicked");
                    if (selectedDice != null) {
                        patternCardGridPane.setCursor(Cursor.DEFAULT);
                        networkType.fluxBrushMove(selectedDice, dicePane.getColumnIndex(), dicePane.getRowIndex());
                    } else {
                        System.out.println("No dice selected");
                    }
                    selectedDice = null;
                });
            }
        }
    }


}
