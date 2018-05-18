package ingsw.controller.network.rmi;

import ingsw.controller.RemoteController;
import ingsw.controller.network.commands.*;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.model.RemoteSagradaGame;
import ingsw.model.User;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIHandler implements RequestHandler {
    private ResponseHandler rmiController;
    private RMIUserObserver rmiUserObserver;
    private RemoteSagradaGame sagradaGame;
    private RemoteController controller;
    private User user;

    /**
     * RMIHandler constructor which retrieves SagradaGame and sets
     * @param rmiController
     * @param rmiUserObserver
     */
    public RMIHandler(RMIController rmiController, RMIUserObserver rmiUserObserver) {
        try {
            this.sagradaGame = (RemoteSagradaGame) LocateRegistry.getRegistry().lookup("sagrada");
        } catch (RemoteException e) {
            System.err.println("Could not retrieve SagradaGame");
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: SagradaGame");
        }
        this.rmiController = rmiController;
        this.rmiUserObserver = rmiUserObserver;
    }

    @Override
    public Response handle(LoginUserRequest loginUserRequest) {
        try {
            user = sagradaGame.loginUser(loginUserRequest.username, rmiUserObserver);
            return new LoginUserResponse(user, sagradaGame.getConnectedUsers());
        } catch (RemoteException e) {
            System.err.println("Remote Exception");
            e.printStackTrace();
        } catch (InvalidUsernameException e) {
            System.err.println("Username has already been taken");
        }
        return null;
    }

    @Override
    public Response handle(ChosenPatternCardRequest chosenPatternCardRequest) {
        return null;
    }

    @Override
    public Response handle(CreateMatchRequest createMatchRequest) throws RemoteException {
        controller = sagradaGame.createMatch(createMatchRequest.matchName);
        if (controller != null) {
            return new CreateMatchResponse(createMatchRequest.matchName);
        }
        return null;
    }
}
