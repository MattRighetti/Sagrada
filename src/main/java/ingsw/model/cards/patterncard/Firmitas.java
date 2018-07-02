package ingsw.model.cards.patterncard;

import ingsw.utilities.GridCreator;
import ingsw.utilities.GridJSONPath;

public class Firmitas extends PatternCard {

    private String json = "[\n" +
            "    [\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 6 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 3 }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 3 },\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 2 },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 1 },\n" +
            "      { \"color\": \"BLANK\" }\n" +
            "    ],\n" +
            "    [\n" +
            "      { \"color\": \"BLANK\" },\n" +
            "      { \"value\": 1 },\n" +
            "      { \"value\": 5 },\n" +
            "      { \"color\": \"PURPLE\" },\n" +
            "      { \"value\": 4 }\n" +
            "    ]\n" +
            "]";

    public Firmitas() {
        super("Firmitas", 5);
        setGrid(GridCreator.fromString(json));
    }
}
