import websocket


def on_message(ws, message):
    print(message)


def on_error(ws, error):
    print(error)


def on_close(ws):
    print("WebSocket closed")


def on_open(ws):
    print("WebSocket opened")
    ws.send("Hello, server!")


if __name__ == "__main__":
    ws = websocket.WebSocketApp("ws://localhost:8080/app",
                                on_message=on_message,
                                on_error=on_error,
                                on_close=on_close)
    ws.on_open = on_open
    ws.run_forever()
