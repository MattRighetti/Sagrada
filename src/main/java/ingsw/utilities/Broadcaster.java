package ingsw.utilities;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.User;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Broadcaster {

    private static List<UserObserver> playerToBroadcast(List<Player> playerList, String usernameToExclude) {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (Player player : playerList) {
            if (!player.getUser().getUsername().equals(usernameToExclude) && !player.getUser().isActive()) {
                try {
                    player.getUserObserver().checkIfActive();
                } catch (RemoteException e) {
                    System.err.println("RMI Player " + player.getPlayerUsername() + " is not active, deactivating user");
                }
                playerListToBroadcast.add(player.getUser().getUserObserver());
            }
        }
        return playerListToBroadcast;
    }

    private static List<UserObserver> playerToBroadcast(Map<String, User> userMap, String usernameToExclude) {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (User user : userMap.values()) {
            if (!user.getUsername().equals(usernameToExclude) && !user.isActive()) {
                try {
                    user.getUserObserver().checkIfActive();
                } catch (RemoteException e) {
                    System.err.println("RMI Player " + user.getUsername() + " is not active, deactivating user");
                }
                playerListToBroadcast.add(user.getUserObserver());
            }
        }
        return playerListToBroadcast;
    }

    private static List<UserObserver> playerToBroadcast(Map<String, User> userMap) {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (User user : userMap.values()) {
            try {
                user.getUserObserver().checkIfActive();
                playerListToBroadcast.add(user.getUserObserver());
            } catch (RemoteException e) {
                System.err.println("RMI Player " + user.getUsername() + " is not active, deactivating user");
            }
        }
        return playerListToBroadcast;
    }

    private static List<UserObserver> playerToBroadcast(List<Player> playerList) {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (Player player : playerList) {
            try {
                player.getUserObserver().checkIfActive();
                playerListToBroadcast.add(player.getUser().getUserObserver());
            } catch (RemoteException e) {
                System.err.println("RMI Player " + player.getPlayerUsername() + " is not active, deactivating user");
            }
        }
        return playerListToBroadcast;
    }

    static void broadcastMessage(List<Player> playerList, Message message) {
        for (UserObserver userObserver : playerToBroadcast(playerList, message.sender)) {
            try {
                userObserver.sendMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    static void broadcastResponse(List<Player> playerList, String usernameToExclude, List<Dice> dice) {
        for (UserObserver userObserver : playerToBroadcast(playerList, usernameToExclude)) {
            try {
                userObserver.sendResponse(new DraftedDiceResponse(dice));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponseToAll(List<Player> playerList, List<Dice> dice) {
        for (Player player : playerList) {
            try {
                player.getUser().getUserObserver().sendResponse(new DraftedDiceResponse(dice));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponseToAll(List<Player> playerList, UpdateViewResponse updateViewResponse) {
        for (Player player : playerList) {
            try {
                player.getUserObserver().sendResponse(updateViewResponse);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponseToAll(Map<String, User> users, CreateMatchResponse createMatchResponse) {
        for (User user : users.values()) {
            try {
                user.getUserObserver().sendResponse(createMatchResponse);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponse(Map<String, User> users, String usernameToExclude, CreateMatchResponse createMatchResponse) {
        for (UserObserver userObserver : playerToBroadcast(users, usernameToExclude)) {
            try {
                userObserver.sendResponse(createMatchResponse);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponseToAll(List<Player> playerList, RoundTrackNotification roundTrackNotification) {
        for (UserObserver userObserver : playerToBroadcast(playerList)) {
            try {
                userObserver.sendResponse(roundTrackNotification);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponseToAll(List<Player> playerList, BoardDataResponse boardDataResponse) {
        for (UserObserver userObserver : playerToBroadcast(playerList)) {
            try {
                userObserver.sendResponse(boardDataResponse);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponseToAll(List<Player> playerList, UseToolCardResponse useToolCardResponse) {
        for (UserObserver userObserver : playerToBroadcast(playerList)) {
            try {
                userObserver.sendResponse(useToolCardResponse);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateMovesHistory(List<Player> playerList, List<MoveStatus> movesHistory) {
        for (UserObserver userObserver : playerToBroadcast(playerList)) {
            try {
                userObserver.sendResponse(new MoveStatusNotification(movesHistory));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponseToAll(Map<String, User> users, int numberOfConnectedUsers) {
        for (UserObserver userObserver : playerToBroadcast(users)) {
            try {
                userObserver.onJoin(numberOfConnectedUsers);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponseToAll(Map<String, User> users, List<TripleString> tripleStrings) {
        for (User user : users.values()) {
            try {
                user.getUserObserver().sendResponse(new RankingDataResponse(tripleStrings));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
