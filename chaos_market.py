import asyncio
import json
import websockets
import random
from datetime import datetime, timedelta, timezone

TICKERS = ["AAPL", "TSLA", "NVDA", "AMD", "NABIL", "NICA", "CBIL"]

async def market_firehose(websocket):
    print("Client Connected to the Chaos Engine!")
    try:
        while True:
            is_whale = random.random() < 0.05
            
            premium = random.uniform(1000000, 10000000) if is_whale else random.uniform(1000, 50000)
            
            trade = {
                "ticker": random.choice(TICKERS),
                "contractType": random.choice(["CALL", "PUT"]),
                "strikePrice": round(random.uniform(50, 600), 2),
                "expirationDate": (datetime.now() + timedelta(days=random.randint(1, 60))).strftime("%Y-%m-%d"),
                "totalPremium": round(premium, 2),
                "volume": random.randint(1, 5000),
                "timestamp": datetime.now(timezone.utc).isoformat()
            }
            
            await websocket.send(json.dumps(trade))
            
            await asyncio.sleep(random.uniform(0.1, 0.5))
            
    except websockets.exceptions.ConnectionClosed:
        print("Client disconnected.")
        

async def main():
    async with websockets.serve(market_firehose, "localhost", 8765):
        print("Whale Watcher Chaos Generator running on ws://localhost:8765")
        await asyncio.Future()  

if __name__ == "__main__":
    asyncio.run(main())