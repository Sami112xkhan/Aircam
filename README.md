# Aircam 🎥
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Sponsor](https://img.shields.io/badge/Sponsor-Buy%20Me%20A%20Coffee-orange.svg)](https://buymeacoffee.com/samiullahkhan)

Aircam is a high-performance, premium Android IP Camera application designed with a modern **Material 3 Glassmorphism UI**. It transforms your Android device into a professional-grade streaming camera with advanced controls, smooth lens transitions, and a beautiful web-based dashboard.

![Aircam Banner](screenshot.webp)

## 🌟 Key Features

### 🎨 Premium Design & UI
- **Glassmorphism Interface**: A state-of-the-art UI utilizing real-time blur, depth effects, and Material 3 design principles.
- **Micro-animations**: Smooth transitions and interactive elements for a premium user experience.

### 🔍 Advanced Camera Controls
- **Unified Zoom (0.5x - 5.0x)**: seamless zooming from Ultrawide to Telephoto.
- **Hybrid Zoom Logic**: Eliminates the harsh "jump" when switching between lenses (e.g., at 1.0x) by using software interpolation between 1.0x and 1.3x.
- **Variable Framerate**: Stream at cinematic 24 FPS, standard 30 FPS, or smooth 60 FPS (device dependent).
- **Ultra HD Support**: Stream in 4K resolution with optimized image processing to minimize lag.

### 🌐 Powerful Web Interface
- **Remote Control Dashboard**: Change resolutions, toggle flashlight, switch cameras, and adjust zoom directly from your browser.
- **Client-Side WebM Recording**: High-quality recording directly to your computer without taxing the phone's CPU.
- **Orientation Control**: Force the stream into Portrait or Landscape mode remotely.

### 🔐 Security & Networking
- **HTTPS/SSL**: Local TLS encryption via 2048-bit RSA certificates.
- **Basic Auth**: Protect your stream with a customizable username and password.
- **Adaptive Bitrate**: Efficient MJPEG streaming that balances quality and network stability.

---

## 🚀 Getting Started

### Prerequisites
- Android device running **6.0 (Marshmallow)** or higher.
- A stable Wi-Fi connection (Both phone and PC must be on the same network).

### Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/Sami112xkhan/Aircam.git
   ```
2. Open the project in **Android Studio**.
3. Build and run the app on your device.

### Basic Setup
1. **Initialize**: Grant the necessary Camera and Network permissions.
2. **Secure Your Stream**: Go to **Settings** and set your preferred Username and Password.
3. **Launch**: Tap the **Start Server** button.
4. **Connect**: Open the displayed URL (e.g., `https://192.168.1.5:4444`) in your browser.
   > [!IMPORTANT]
   > Since the app uses a self-signed certificate for local encryption, your browser will show a security warning. Click **Advanced** -> **Proceed** to access the dashboard.

---

## 🛠 Technical Overview

- **Core**: Written 100% in **Kotlin**.
- **Camera API**: Built on **CameraX** for stable, multi-lens support across millions of devices.
- **Server**: Custom lightweight HTTP server optimized for MJPEG streaming.
- **Frontend**: Vanilla JS/CSS web dashboard utilizing modern CSS Grid/Flexbox and HSL color tokens.

---

## � Support the Project

If you find Aircam useful and would like to support its development, you can buy me a coffee! Your support helps keep the project updated and improved.

[![Buy Me A Coffee](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://buymeacoffee.com/samiullahkhan)

---

## 📜 License
This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

Developed with ❤️ by **Digitally Refined** (Sami Khan).
