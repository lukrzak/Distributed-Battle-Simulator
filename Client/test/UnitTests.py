import asyncio
import websockets

async def hello ():
    async with websockets.connect("ws://localhost:8000") as websocket:
        await websocket.send("jeb sie serwer")
        response = await websocket.recv()
        print(f" response: {response}")

asyncio.run(hello())
