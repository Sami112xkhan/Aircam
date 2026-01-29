# Aircam 🎥

Aircam is a high-performance, premium Android IP Camera application designed with a modern **Material 3 Glassmorphism UI**. It turns your Android device into a professional-grade streaming camera with advanced controls and a beautiful web interface.

## ✨ Features

- **Premium UI**: Stunning "Glassmorphism" design system with real-time blur and depth effects.
- **Unified Zoom (0.5x - 5.0x)**: Smooth lens transition ("Hybrid Zoom") that blends the Ultrawide and Main lenses seamlessly.
- **Ultra HD Streaming**: Support for 4K resolutions and 60 FPS cinematic streaming.
- **Aircam Web Interface**: Control everything from your browser—Zoom, Flash, Camera switching, and Resolution.
- **Secure Streaming**: Local network HTTPS streaming with Basic Authentication.
- **Client-Side Recording**: Record your stream directly to your browser as High-Quality WebM.
- **Orientation Control**: Switch between Auto-Rotate, Locked Portrait, or Locked Landscape from the web.

## 🚀 Getting Started

### Installation
1. Build and install the APK on your Android device.
2. Grant Camera and Storage permissions.

### Usage
1. Open the Aircam app.
2. Configure your **Username** and **Password** in Settings (highly recommended).
3. Tap **Start Server**.
4. Visit the displayed HTTPS address in your browser (e.g., `https://192.168.1.10:4444`).
   - *Note: You may need to bypass the browser's SSL warning as it uses a self-signed local certificate.*

## 🛠 Tech Stack
- **Kotlin**: Core application logic.
- **CameraX**: High-performance camera API.
- **Ktor/Socket**: Lightweight MJPEG streaming server.
- **Material 3**: Modern design components.
- **Vanilla JS/CSS**: Sleek, responsive web control interface.

## 🔐 Security
Aircam uses a locally generated 2048-bit RSA certificate for HTTPS. Authentication is handled via standard Basic Auth to ensure your stream stays private within your network.

---
Developed by **Digitally Refined** (Sami Khan)
