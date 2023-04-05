import json
import threading
import websocket
import tkinter as tk

ws = None

def on_message(ws, message):
    print(message)


def on_error(ws, error):
    print(error)


def on_close(ws):
    print("WebSocket closed")


def on_open(ws):
    print("WebSocket opened")


def send_message():
    data = {
        "name": "button_press",
        "value": "pressed"
    }
    ws.send(json.dumps(data))


def start_websocket():
    global ws
    ws = websocket.WebSocketApp("ws://localhost:8080/app",
                                on_message=on_message,
                                on_error=on_error,
                                on_close=on_close)
    ws.on_open = on_open
    ws.run_forever()


t = threading.Thread(target=start_websocket)
t.daemon = True
t.start()

root = tk.Tk()
button = tk.Button(root, text="Send message", command=send_message)
button.pack()

root.mainloop()
