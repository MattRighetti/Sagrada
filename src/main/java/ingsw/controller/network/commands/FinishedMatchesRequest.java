package ingsw.controller.network.commands;

public class FinishedMatchesRequest implements Request {
    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}
