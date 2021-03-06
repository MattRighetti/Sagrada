package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;

public class Comitas extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 6 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 4 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"YELLOW\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"value\": 5 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 1 },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"YELLOW\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ]\n" +
            "]";

    /**
     * Creates a new Comitas pattern card
     */
    public Comitas() {
        super("Comitas", 5);
        setGrid(GridCreator.fromString(json));
    }
}
