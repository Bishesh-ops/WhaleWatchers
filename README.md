# WhaleWatchers: NEPSE Block Trade Radar

## Overview
WhaleWatchers is a polyglot microservices application designed to detect and visualize high-volume block trades (whales) on the Nepal Stock Exchange (NEPSE) in real-time. 

Built as an exercise in mastering multiple enterprise languages and avoiding project scope creep, this system implements the Command Query Responsibility Segregation (CQRS) pattern. It intercepts live market data, filters out retail noise using algorithmic thresholds, persists the anomalies, and serves the data to a lightweight frontend dashboard.

## System Architecture
The pipeline consists of five decoupled components:

1. **Data Ingestion (Python 3.14):** A custom web scraper (`requests`, `BeautifulSoup`) that polls ShareSansar, rotates User-Agents to prevent rate-limiting, extracts live equity data, calculates total NPR turnover, and streams it via WebSockets.
2. **Command Node (Java 25 / Spring Boot):** The processing engine. It listens to the WebSocket stream, utilizes lightweight Virtual Threads to handle concurrent data processing without blocking, applies a 1 Crore NPR (or 50 Lakhs) threshold filter, and maps the data to entities.
3. **Persistence Layer (PostgreSQL):** A Dockerized relational database that permanently stores the detected block trades. Tables are auto-generated and managed via Hibernate.
4. **Query Node (C# .NET 10):** A REST API built with ASP.NET Core and Entity Framework. It connects to the PostgreSQL database, executes optimized LINQ queries to retrieve the top trades, and exposes a JSON endpoint (`/api/whales/top`) with CORS enabled for client access.
5. **Client Dashboard (Vanilla JS / HTML):** A dependency-free frontend utilizing Chart.js. It polls the .NET API asynchronously and updates a live bar chart and data table without requiring page reloads.

## Prerequisites
* Python 3.14
* Java 25 (with Maven wrapper included)
* .NET 10 SDK
* Docker & Docker Compose
* VS Code (with Live Server extension recommended)

## Running Locally

To start the full pipeline, open separate terminal windows for each component and follow this sequence:

### 1. Database
```bash
docker-compose up -d
```
### 2. Java DataCruncher (Command)
```bash
cd DataCruncher
./mvnw spring-boot:run
```
### 3. C# ClientGateway (Query)
```bash
cd ClientGateway
dotnet run
```
### 4. Frontend Dashboard
Open WhaleWatchers-UI/index.html using a local web server (e.g., VS Code Live Server on port 5500).

##  Project Philosophy
This project was strictly time-boxed and scoped to focus on learning and integrating modern language features (Java Virtual Threads, C# LINQ, Python async websockets) rather than infinitely expanding features. By keeping the scope rigid, the architecture was completed end-to-end, demonstrating a functional microservice ecosystem without the trap of scope creep.
