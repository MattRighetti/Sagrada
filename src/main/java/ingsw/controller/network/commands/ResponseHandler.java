package ingsw.controller.network.commands;

public interface ResponseHandler {

    void handle(LoginUserResponse loginUserResponse);

    void handle(IntegerResponse integerResponse);

    void handle(MessageResponse messageResponse);

    void handle(CreateMatchResponse createMatchResponse);

    void handle(JoinedMatchResponse joinedMatchResponse);

    void handle(UpdateViewResponse updateViewResponse);

    void handle(PatternCardNotification patternCardNotification);

    void handle(LogoutResponse logoutResponse);

    void handle(BoardDataResponse boardDataResponse);

    void handle(Notification notification);

    void handle(DraftedDiceResponse draftedDiceResponse);

}
