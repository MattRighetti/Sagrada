package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class FinishedMatchesResponseTest {
    private FinishedMatchesResponse finishedMatchesResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        finishedMatchesResponse = new FinishedMatchesResponse(new ArrayList<>());
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        finishedMatchesResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(finishedMatchesResponse);
        finishedMatchesResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(finishedMatchesResponse);
    }
}