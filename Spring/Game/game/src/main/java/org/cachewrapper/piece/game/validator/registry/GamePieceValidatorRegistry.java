package org.cachewrapper.piece.game.validator.registry;

import org.cachewrapper.Game;
import org.cachewrapper.piece.game.type.GamePiece;
import org.cachewrapper.piece.game.validator.GamePieceValidator;
import org.cachewrapper.piece.game.validator.impl.PawnPieceValidator;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePieceValidatorRegistry {

    private final Map<Class<? extends GamePiece>, GamePieceValidator> pieceValidators = new HashMap<>();

    public GamePieceValidatorRegistry(@NotNull Game game) {
        registerDefaults(game);
    }

    @NotNull
    public GamePieceValidator getPieceValidator(@NotNull Class<? extends GamePiece> gamePieceType) {
        return pieceValidators.get(gamePieceType);
    }

    private void registerDefaults(@NotNull Game game) {
        List.of(
                new PawnPieceValidator(game)
        ).forEach(this::registerValidator);
    }

    private void registerValidator(@NotNull GamePieceValidator gamePieceValidator) {
        final Class<? extends GamePiece> gamePieceType = gamePieceValidator.getPieceType();
        pieceValidators.put(gamePieceType, gamePieceValidator);
    }
}