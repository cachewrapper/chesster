package org.cachewrapper.board;

import lombok.Getter;
import org.cachewrapper.Game;
import org.cachewrapper.board.handler.impl.GameMoveHandler;
import org.cachewrapper.board.handler.payload.HandleMovePayload;
import org.cachewrapper.board.parser.GameBoardParser;
import org.cachewrapper.board.parser.factory.PieceFactoryFacade;
import org.cachewrapper.board.parser.factory.registry.PieceFactoryRegistry;
import org.cachewrapper.board.parser.impl.FenGameBoardParser;
import org.cachewrapper.board.tracker.GameBoardPieceTracker;
import org.cachewrapper.piece.data.Location;
import org.cachewrapper.piece.type.GamePiece;
import org.cachewrapper.player.GamePlayerColor;
import org.cachewrapper.player.tracker.GamePlayerTracker;
import org.cachewrapper.player.type.GamePlayer;
import org.cachewrapper.player.type.impl.GamePlayerActive;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Scope("prototype")
public class GameBoardContext {

    private final GameBoardPieceTracker boardPieceTracker = new GameBoardPieceTracker();

    private final GameMoveHandler gameMoveHandler;
    private final GameBoardParser gameBoardParser;
    private final Game game;

    public GameBoardContext(@NotNull Game game) {
        this.game = game;

        final PieceFactoryRegistry pieceFactoryRegistry = new PieceFactoryRegistry();
        final PieceFactoryFacade pieceFactoryFacade = new PieceFactoryFacade(pieceFactoryRegistry);

        this.gameBoardParser = new FenGameBoardParser(pieceFactoryFacade);
        this.gameMoveHandler = new GameMoveHandler(game, boardPieceTracker);
    }

    public void loadGameBoard() {
//        final GamePlayerTracker playerTracker = game.getPlayerContext().addActiveGamePlayer();
//        final List<GamePlayerActive> gameActivePlayerList = playerTracker.getGamePlayersByType(GamePlayerActive.class);
//
//        final UUID whitePlayerUUID = gameActivePlayerList.stream()
//                .filter(gamePlayer -> gamePlayer.getPlayerColor() == GamePlayerColor.WHITE)
//                .findFirst()
//                .orElseThrow()
//                .getPlayerUUID();
//
//        final UUID blackPlayerUUID = gameActivePlayerList.stream()
//                .filter(gamePlayer -> gamePlayer.getPlayerColor() == GamePlayerColor.BLACK)
//                .findFirst()
//                .orElseThrow()
//                .getPlayerUUID();
//
//        final Map<Location, GamePiece> loadedGamePieces = gameBoardParser.parseGameBoard(whitePlayerUUID, blackPlayerUUID);
//        loadedGamePieces.forEach(boardPieceTracker::addPieceLocation);
    }

    public boolean moveGamePiece(@NotNull Location previousLocation, @NotNull Location goalLocation) {
        final GamePiece gamePiece = boardPieceTracker.getPiece(previousLocation);
        final HandleMovePayload handleMovePayload = new HandleMovePayload(previousLocation, goalLocation, gamePiece);

        boolean canMove = gameMoveHandler.handle(handleMovePayload);

        if (canMove) {
            System.out.println("Can move: true");
            return true;
        } else {
            System.out.println("Can move: false");
            return false;
        }
    }
}