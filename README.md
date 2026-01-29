# Aircam 🎥
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)
[![License](https://img.shields.io/badge/License-Proprietary-red.svg)](LICENSE)
[![Sponsor](https://img.shields.io/badge/Sponsor-Buy%20Me%20A%20Coffee-orange.svg)](https://buymeacoffee.com/samiullahkhan)

Aircam is a high-performance, premium Android IP Camera application designed with a modern **Material 3 Glassmorphism UI**. It transforms your Android device into a professional-grade streaming camera with advanced controls, smooth lens transitions, and a beautiful web-based dashboard.

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
This project is licensed under a **Proprietary Commercial License**. All rights are reserved by Digitally Refined (Sami Khan). Unauthorized copying, modification, or distribution is strictly prohibited. For commercial use or redistribution, a licensing fee is required.

Developed with ❤️ by **Digitally Refined** (Sami Khan).

---

<h1 align="center">Hey 👋 I’m Sami</h1>

<p align="center">
  I build <b>polished, performance-first apps</b> across Android, web, and macOS.<br/>
  Obsessed with <b>glass UI, smooth animations</b>, and shipping things people actually use.
</p>

---

<h3 align="center">🚀 Tech Stack</h3>

<div align="center">
  <!-- Frontend -->
  <img src="https://skillicons.dev/icons?i=ts,js,react,nextjs,tailwind" height="45" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/storybook/storybook-original.svg" height="45" />

  <!-- Mobile -->
  <img src="https://skillicons.dev/icons?i=kotlin,androidstudio,flutter,dart" height="45" />

  <!-- Backend -->
  <img src="https://skillicons.dev/icons?i=nestjs,nodejs,go,rust,py,graphql,sqlite,mysql" height="45" />

  <!-- Tools -->
  <img src="https://skillicons.dev/icons?i=git,github,figma,vite,vercel,aws" height="45" />
</div>

---

<h3 align="center">📱 Apps & Projects</h3>

<p align="center">
Apps I built because I wanted them to exist.
</p>

<details>
<summary><b>AppLock</b> 🔐 Smooth & Secure Android App Locker</summary>

- Biometric, PIN & Pattern unlock  
- Inspired by Nothing OS, iOS & One UI  
- Custom animations, gestures & transitions  
- Performance-first, no bloat  

</details>

<details>
<summary><b>LockCanvas</b> 🖥️ App Lock for macOS</summary>

- Translucent lock overlay for selected apps  
- Supports PIN, Pattern & Touch ID  
- No root / admin access required  
- Designed with macOS-native UX principles  

</details>

<details>
<summary><b>Glass Notes</b> 📝 Futuristic Offline-First Notes App</summary>

- Biometric lock & local-first storage  
- Swipe actions, pinning & smooth transitions  
- Gen-Z inspired glass UI & motion design  

</details>

<details>
<summary><b>Glass Animation Tuner</b> ⚙️ Apple-style Android Animation Control</summary>

- Control system animation scales  
- Liquid UI inspired by iOS & visionOS  
- Built for speed, smoothness & aesthetics  

</details>

<details>
<summary><b>SightGuard AI</b> 🧠 (In Progress)</summary>

- AI-powered real-time visual threat detection  
- Emergency assistance for mobile & web  
- Open-source models + cloud-first architecture  

</details>

---

<h3 align="center">📊 GitHub Stats</h3>

<p align="center">
  <img src="https://github-readme-stats.vercel.app/api?username=Sami112xkhan&show_icons=true&theme=transparent&hide_border=true" height="160" />
  <img src="https://github-readme-stats.vercel.app/api/top-langs/?username=Sami112xkhan&layout=compact&theme=transparent&hide_border=true" height="160" />
</p>

---
<h3 align="center">🏆 GitHub Rank</h3>

<p align="center">
  <a href="https://github-ranked.vercel.app/api/rank/Sami112xkhan" target="_blank">
    <img src="https://github-ranked.vercel.app/api/rank/Sami112xkhan" height="180" />
  </a>
</p>

---

<h3 align="center">☕ Support My Work</h3>

<p align="center">
  <a href="https://www.buymeacoffee.com/samiullahkhan" target="_blank">
    <img src="https://img.buymeacoffee.com/button-api/?text=Buy%20me%20a%20coffee&emoji=☕&slug=samiullahkhan&button_colour=FFDD00&font_colour=000000&font_family=Inter&outline_colour=000000&coffee_colour=ffffff" />
  </a>
</p>

---

<h3 align="center">🌐 Let’s Connect</h3>

<p align="center">
  <a href="https://www.linkedin.com/in/sami-ullah-khan112" target="_blank">
    <img src="https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white" height="28" />
  </a>
  <a href="https://twitter.com/your_twitter_handle" target="_blank">
    <img src="https://img.shields.io/badge/X-000000?style=for-the-badge&logo=x&logoColor=white" height="28" />
  </a>
</p>
