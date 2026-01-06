'use client'

import { useState, useEffect } from "react";
import Chess from "chess.js";

export default function ChessPage() {
    const [game, setGame] = useState(new Chess());
    const [selected, setSelected] = useState(null);

    // Авто‑ход черных
    const autoMoveBlack = () => {
        if (game.game_over()) return;

        if (game.turn() === "b") { // ход черных
            const moves = game.moves();
            if (!moves.length) return;
            const move = moves[Math.floor(Math.random() * moves.length)];
            const newGame = new Chess(game.fen());
            newGame.move(move);
            setGame(newGame);
        }
    };

    // Интервал для автоходов черных
    useEffect(() => {
        const interval = setInterval(autoMoveBlack, 1500);
        return () => clearInterval(interval);
    }, [game]);

    // Твои клики — белые
    const handleClick = (square) => {
        if (game.turn() !== "w" || game.game_over()) return; // только белые

        if (selected) {
            const newGame = new Chess(game.fen());
            const move = newGame.move({ from: selected, to: square, promotion: "q" });
            if (move) setGame(newGame);
            setSelected(null);
        } else {
            setSelected(square);
        }
    };

    // Рендер доски
    const renderBoard = () => {
        const board = game.board();
        return (
            <div style={{
                display: "grid",
                gridTemplateColumns: "repeat(8, 60px)",
                gridTemplateRows: "repeat(8, 60px)",
                border: "2px solid black"
            }}>
                {board.flatMap((row, i) =>
                    row.map((piece, j) => {
                        const isLight = (i + j) % 2 === 0;
                        const square = `${"abcdefgh"[j]}${8 - i}`;
                        return (
                            <div
                                key={square}
                                onClick={() => handleClick(square)}
                                style={{
                                    width: 60,
                                    height: 60,
                                    backgroundColor: isLight ? "#eee" : "#777",
                                    display: "flex",
                                    justifyContent: "center",
                                    alignItems: "center",
                                    fontSize: 32,
                                    cursor: game.turn() === "w" ? "pointer" : "default",
                                    boxSizing: "border-box",
                                    border: selected === square ? "2px solid red" : "none",
                                    color: piece && piece.color === "w" ? "#fff" : "#000"
                                }}
                            >
                                {piece ? {
                                    p: "♟", r: "♜", n: "♞", b: "♝", q: "♛", k: "♚"
                                }[piece.type] : null}
                            </div>
                        );
                    })
                )}
            </div>
        );
    };

    return (
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", marginTop: 20 }}>
            {renderBoard()}
        </div>
    );
}
