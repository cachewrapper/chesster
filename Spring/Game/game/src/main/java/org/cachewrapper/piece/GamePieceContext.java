package org.cachewrapper.piece;

import org.cachewrapper.Game;
import org.cachewrapper.piece.type.GamePiece;
import org.cachewrapper.piece.validator.GamePieceValidator;
import org.cachewrapper.piece.validator.registry.GamePieceValidatorRegistry;
import org.jetbrains.annotations.NotNull;

public class GamePieceContext {

    private final GamePieceValidatorRegistry pieceValidatorRegistry;
    
    public GamePieceContext(@NotNull Game game) {
        this.pieceValidatorRegistry = new GamePieceValidatorRegistry(game);
    }

    @NotNull
    public GamePieceValidator getPieceValidator(@NotNull Class<? extends GamePiece> gamePieceType) {
        return pieceValidatorRegistry.getPieceValidator(gamePieceType);
    }
}
