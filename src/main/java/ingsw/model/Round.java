package ingsw.model;

import java.util.Set;

public class Round {

    private static int currentRound = 0;
    private static int noRound;
    private Set<Dice> unusedDice;

    public Round(Set<Dice> unusedDice) {
        currentRound++;
        noRound = currentRound;
        this.unusedDice = unusedDice;

    }

}
