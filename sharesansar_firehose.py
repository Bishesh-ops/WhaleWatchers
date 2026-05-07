import asyncio
import websockets
import json
import requests
from bs4 import BeautifulSoup
from datetime import datetime, timezone
import random

URL = "https://www.sharesansar.com/live-trading"

USER_AGENTS = [
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.0 Safari/605.1.15",
    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36"
]

def scrape_live_market():
    """
    Synchronous function to make the web request and parse HTML.
    We run this in a background thread later so it doesn't block the WebSocket.
    """
    headers = {
        "User-Agent": random.choice(USER_AGENTS),
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
        "Accept-Language": "en-US,en;q=0.5"
    }
    
    try:
        response = requests.get(URL, headers=headers, timeout=10)
        response.raise_for_status()
        
        soup = BeautifulSoup(response.text, 'html.parser')
        # with open("debug_sharesansar.html", "w", encoding="utf-8") as f:
        #     f.write(soup.prettify())
        
        table = soup.find('table')
        if not table:
            print("Could not find the data table in the HTML.")
            return []

        trades = []
        tbody = table.find('tbody')
        
        for row in tbody.find_all('tr'):
            cols = row.find_all('td')

            if len(cols) >= 9:
                try:
                    symbol = cols[1].text.strip()

                    ltp_str = cols[2].text.replace(',', '').strip()
                    vol_str = cols[8].text.replace(',', '').strip()
                    
                    if not ltp_str or not vol_str:
                        continue
                        
                    price = float(ltp_str)
                    volume = int(float(vol_str))
                    turnover = price * volume
                    
                    if turnover > 0:
                        trades.append({
                            "ticker": symbol,
                            "contractType": "EQUITY",
                            "strikePrice": round(price, 2),
                            "expirationDate": "2099-12-31", 
                            "totalPremium": round(turnover, 2), 
                            "volume": volume,
                            "timestamp": datetime.now(timezone.utc).isoformat()
                        })
                except ValueError:

                    continue
                    
        return trades
        
    except Exception as e:
        print(f"Scraping Error: {e}")
        return []

async def sharesansar_websocket_server(websocket):
    print("Java Backend Connected! Initializing ShareSansar Scraper...")
    
    try:
        while True:
            print("Scraping ShareSansar Live Market...")
            
            trades = await asyncio.to_thread(scrape_live_market)
            
            if trades:
                print(f"Successfully scraped {len(trades)} active tickers.")
                for trade in trades:
                    await websocket.send(json.dumps(trade))
                    await asyncio.sleep(0.02)
            else:
                print("No trades found. Market might be closed or layout changed.")
            
            await asyncio.sleep(random.uniform(45, 60))
            
    except websockets.exceptions.ConnectionClosed:
        print("Java Backend disconnected.")

async def main():
    async with websockets.serve(sharesansar_websocket_server, "localhost", 8765):
        print("ShareSansar Scraper Engine running on ws://localhost:8765")
        await asyncio.Future()

if __name__ == "__main__":
    asyncio.run(main())