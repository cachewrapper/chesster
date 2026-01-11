import { useEffect, useRef, useState } from "react";
import { Client } from '@stomp/stompjs';
import SockJS from "sockjs-client";
import Chess from "chess.js";

export default function ChessPage() {
    const [game, setGame] = useState(new Chess());
    const [selected, setSelected] = useState(null);
    const [playerColor, setPlayerColor] = useState(null);
    const gameUUID = useRef("8aa156a0-5c36-47e1-9234-5cb8132e440a");
    const stompClient = useRef(null);

    useEffect(() => {
        // SockJS подхватывает куки автоматически
        const socket = new SockJS('http://localhost:8083/ws-game');
        const client = new Client({
            webSocketFactory: () => socket,
            debug: (msg) => console.log('[STOMP]', msg)
        });

        client.onConnect = () => {
            console.log('STOMP connected');

            // Подписка на топик игры
            client.subscribe(`/topic/game/${gameUUID.current}`, (msg) => {
                const data = JSON.parse(msg.body);

                if (data.httpStatus === "OK" || data.type === "MOVE") {
                    const newGame = new Chess(game.fen());
                    if (data.previousLocation && data.goalLocation) {
                        newGame.move({
                            from: coordsToSquare(data.previousLocation),
                            to: coordsToSquare(data.goalLocation),
                            promotion: "q"
                        });
                        setGame(newGame);
                    }
                }

                if (data.type === "START") {
                    setPlayerColor(data.color);
                }
            });

            // Join-запрос
            client.publish({
                destination: '/app/join',
                body: JSON.stringify({ gameUUID: gameUUID.current })
            });
        };

        client.onStompError = (frame) => console.error('STOMP error', frame);

        client.activate();
        stompClient.current = client;

        return () => client.deactivate();
    }, []);

    const coordsToSquare = (loc) => `${"abcdefgh"[loc.coordinateY]}${8 - loc.coordinateX}`;
    const squareToCoords = (square) => ({ coordinateX: 8 - parseInt(square[1]), coordinateY: "abcdefgh".indexOf(square[0]) });

    const sendMove = (fromSquare, toSquare) => {
        if (!stompClient.current || !playerColor) return;
        stompClient.current.publish({
            destination: `/app/move/${gameUUID.current}`,
            body: JSON.stringify({ previousLocation: squareToCoords(fromSquare), goalLocation: squareToCoords(toSquare) })
        });
    };

    const handleClick = (square) => {
        if (!playerColor || game.game_over()) return;
        if ((game.turn() === "w" && playerColor !== "w") || (game.turn() === "b" && playerColor !== "b")) return;

        if (selected) {
            const newGame = new Chess(game.fen());
            const move = newGame.move({ from: selected, to: square, promotion: "q" });
            if (move) {
                setGame(newGame);
                sendMove(selected, square);
            }
            setSelected(null);
        } else {
            setSelected(square);
        }
    };

    const renderBoard = () => {
        const board = game.board();
        return (
            <div style={{ display: "grid", gridTemplateColumns: "repeat(8, 60px)", gridTemplateRows: "repeat(8, 60px)", border: "2px solid black" }}>
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
                                    cursor: (game.turn() === playerColor && !game.game_over()) ? "pointer" : "default",
                                    boxSizing: "border-box",
                                    border: selected === square ? "2px solid red" : "none",
                                    color: piece && piece.color === "w" ? "#fff" : "#000"
                                }}
                            >
                                {piece ? { p: "♟", r: "♜", n: "♞", b: "♝", q: "♛", k: "♚" }[piece.type] : null}
                            </div>
                        );
                    })
                )}
            </div>
        );
    };

    return (
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", marginTop: 20 }}>
            <h3>Game UUID: {gameUUID.current}</h3>
            {renderBoard()}
        </div>
    );
}
