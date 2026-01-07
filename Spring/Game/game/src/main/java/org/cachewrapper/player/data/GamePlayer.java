package org.cachewrapper.player.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.player.GamePlayerColor;

@Getter
@RequiredArgsConstructor
public class GamePlayer {

    private final GamePlayerColor gamePlayerColor;
}