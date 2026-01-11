package org.cachewrapper.board.parser.factory;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.board.parser.factory.registry.PieceFactoryRegistry;
import org.cachewrapper.piece.type.GamePiece;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@RequiredArgsConstructor
public class PieceFactoryFacade {

    private final PieceFactoryRegistry pieceFactoryRegistry;

    public GamePiece createGamePiece(@NotNull char identifier, @NotNull UUID playerUUID) {
      PieceFactory<?> pieceFactory = pieceFactoryRegistry.getPieceFactory(String.valueOf(identifier).toLowerCase());
      if (pieceFactory == null) {
          return null;
      }

      return pieceFactory.createPiece(playerUUID);
    }
}