package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIHandler;
import ingsw.controller.network.socket.ServerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UseToolCardRequestTest {
    private UseToolCardRequest useToolCardRequest;
    private ingsw.controller.network.rmi.RMIHandler rmiHandler;
    private ServerController serverController;

    @BeforeEach
    void setUp() {
        useToolCardRequest = new UseToolCardRequest("Test");
        rmiHandler = Mockito.mock(RMIHandler.class);
        serverController = Mockito.mock(ServerController.class);
    }

    @Test
    void handle() {
        useToolCardRequest.handle(rmiHandler);
        Mockito.verify(rmiHandler, Mockito.times(1)).handle(useToolCardRequest);
        useToolCardRequest.handle(serverController);
        Mockito.verify(serverController, Mockito.times(1)).handle(useToolCardRequest);
    }
}