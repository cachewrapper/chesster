package org.cachewrapper.piece.data;

import lombok.Builder;

@Builder
public record Location(
        int coordinateX,
        int coordinateY
) {}