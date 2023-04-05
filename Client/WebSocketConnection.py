import websocket
import json

ws = None
URI = 'ws://localhost:8080/app'


def on_message(ws, message):
    print(message)


def on_error(ws, error):
    print(error)


def on_open(ws):
    print("Connection established")


def on_close(ws):
    print("Connection closed")


def start_websocket():
    global ws
    ws = websocket.WebSocketApp(URI, on_open=on_open, on_message=on_message, on_error=on_error, on_close=on_close)
    ws.on_open = on_open
    ws.run_forever()


def send_message(data):
    ws.send(json.dumps(data))
