# 🛡️ Safe Chat App

**Safe Chat** is a privacy-focused real-time chat application built with **FastAPI (backend)** and **Kotlin (Android client)**.  
It ensures secure communication between users by validating Wi-Fi network safety, encrypting messages, and providing an intuitive modern chat experience.

---

## 🚀 Features

### 🧩 Core Features
- **User Authentication**
  - Register, login, and token-based authentication (JWT)
- **Real-time Messaging**
  - WebSocket-based chat for instant message delivery
- **Unread Message Tracking**
  - Each chatroom displays the number of unread messages
- **Last Message Preview**
  - Chat list shows the most recent message for each chatroom
- **Message Read Status**
  - Messages automatically marked as read once viewed

### 🔒 Safety Features
- **Wi-Fi Safety Checker (Android)**
  - Detects if the current network is safe before connecting to chat
  - Warns users about insecure or suspicious networks
- **End-to-End Encryption (Optional/Planned)**
  - Ensures data confidentiality between sender and receiver

### 💬 Chatroom Management
- Create and manage 1-on-1 chatrooms  
- See last message and unread count for each room  
- Receive message notifications in real time

---

## 🧠 Tech Stack

### Backend (FastAPI)
- **Framework:** FastAPI  
- **Database:** PostgreSQL (with SQLAlchemy ORM)  
- **Auth:** JWT-based authentication  
- **WebSocket:** Real-time messaging  
- **Serializer:** Pydantic models  
- **Deployed via:** Uvicorn + Gunicorn  

### Android App (Kotlin)
- **Architecture:** MVVM + Flow + Jetpack Compose  
- **Networking:** Retrofit + OkHttp  
- **WebSocket:** OkHttp WebSocket client  
- **Persistence:** Room Database  
- **UI:** Material 3, Compose UI  
- **Coroutines:** For async event handling  

---

## 📡 API Overview

| Endpoint | Method | Description |
|-----------|--------|-------------|
| `/api/v1/auth/register` | POST | Register a new user |
| `/api/v1/auth/login` | POST | Login and get JWT token |
| `/api/v1/chatrooms` | GET | Get all chatrooms for a user |
| `/api/v1/chatrooms/{id}/messages` | GET | Fetch messages in a room |
| `/api/v1/chatrooms/{id}/messages` | POST | Send a new message |
| `/ws/chat/{room_id}` | WS | Connect to a chatroom WebSocket |

---

## 📱 Android Screenshots (Preview)

> _Coming soon_  
> (Add screenshots of login, chat list, and chat room views here)

---

## ⚙️ Setup Guide

### Backend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/safe-chat-backend.git
   cd safe-chat-backend
