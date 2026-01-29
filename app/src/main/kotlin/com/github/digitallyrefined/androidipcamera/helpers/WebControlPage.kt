package com.github.digitallyrefined.androidipcamera.helpers

object WebControlPage {
    fun getHtml(isPro: Boolean): String {
        return """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aircam</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Flex:opsz,wght@8..144,300;400;500;600&display=swap" rel="stylesheet">
    <style>
        :root {
            --md-sys-color-primary: #D0BCFF;
            --md-sys-color-on-primary: #381E72;
            --md-sys-color-primary-container: #4F378B;
            --md-sys-color-on-primary-container: #EADDFF;
            --md-sys-color-surface: #141218;
            --md-sys-color-surface-container: #1D1B20;
            --md-sys-color-on-surface: #E6E1E5;
            --md-sys-color-outline: #938F99;
            --md-sys-color-error: #F2B8B5;
            
            --radius-large: 24px;
            --radius-medium: 16px;
        }

        body {
            font-family: 'Roboto Flex', sans-serif;
            background-color: var(--md-sys-color-surface);
            color: var(--md-sys-color-on-surface);
            margin: 0;
            display: flex;
            height: 100vh;
            overflow: hidden;
        }

        /* Expressive Layout */
        .app-container {
            display: grid;
            grid-template-columns: 1fr 360px;
            width: 100%;
            height: 100%;
        }

        .video-feed {
            background: #000;
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
        }
        
        #stream {
            max-width: 100%;
            max-height: 100%;
            object-fit: contain;
        }

        .controls-sidebar {
            background-color: var(--md-sys-color-surface-container);
            padding: 24px;
            display: flex;
            flex-direction: column;
            gap: 24px;
            border-left: 1px solid rgba(255,255,255,0.08);
            overflow-y: auto;
        }

        /* Expressive Header */
        .sidebar-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        h2 { margin: 0; font-weight: 500; font-size: 24px; }
        
        .badge {
            font-size: 12px;
            padding: 4px 12px;
            border-radius: 50px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        
        .badge.pro {
            background: var(--md-sys-color-primary-container);
            color: var(--md-sys-color-on-primary-container);
        }
        
        .badge.free {
            background: #333;
            color: #ccc;
            cursor: pointer;
            border: 1px solid var(--md-sys-color-outline);
        }

        /* M3 Controls */
        .control-section {
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .label-row {
            display: flex;
            justify-content: space-between;
            font-size: 14px;
            color: var(--md-sys-color-on-surface);
            opacity: 0.8;
            font-weight: 500;
        }

        /* M3 Select */
        .m3-select {
            appearance: none;
            background-color: rgba(255,255,255,0.05);
            border: 1px solid var(--md-sys-color-outline);
            border-radius: var(--radius-medium);
            color: inherit;
            padding: 16px;
            font-size: 16px;
            width: 100%;
            cursor: pointer;
            transition: all 0.2s;
        }
        .m3-select:hover { background-color: rgba(255,255,255,0.08); }

        /* M3 Slider */
        input[type=range] {
            width: 100%;
            height: 44px; /* Touch target */
            background: transparent;
            cursor: pointer;
        }

        /* Camera Switcher Segmented Button */
        .segmented-btn-group {
            display: flex;
            background: rgba(255,255,255,0.05);
            border-radius: 50px;
            padding: 4px;
            border: 1px solid var(--md-sys-color-outline);
        }

        .segmented-btn {
            flex: 1;
            background: transparent;
            border: none;
            color: var(--md-sys-color-on-surface);
            padding: 10px;
            border-radius: 50px;
            font-size: 13px;
            font-weight: 500;
            cursor: pointer;
        }
        
        .segmented-btn.active {
            background-color: var(--md-sys-color-primary-container);
            color: var(--md-sys-color-on-primary-container);
        }

        /* Action Buttons */
        .action-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
        }

        .m3-btn {
            background-color: var(--md-sys-color-primary);
            color: var(--md-sys-color-on-primary);
            border: none;
            padding: 16px;
            border-radius: var(--radius-large);
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: transform 0.1s;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }
        
        .m3-btn.secondary {
            background-color: rgba(255,255,255,0.08);
            color: var(--md-sys-color-primary);
        }
        
        .m3-btn:active { transform: scale(0.98); }

        /* Upgrade Modal */
        #upgrade-modal {
            display: none;
            position: fixed;
            top: 0; left: 0; width: 100%; height: 100%;
            background: rgba(0,0,0,0.8);
            z-index: 100;
            align-items: center;
            justify-content: center;
        }
        
        .modal-content {
            background: var(--md-sys-color-surface-container);
            padding: 32px;
            border-radius: var(--radius-large);
            max-width: 400px;
            width: 90%;
            text-align: center;
        }
        
        .promo-text { margin-bottom: 24px; line-height: 1.5; opacity: 0.9; }
        
        .coupon-input {
            width: 100%;
            padding: 16px;
            box-sizing: border-box;
            background: rgba(0,0,0,0.2);
            border: 1px solid var(--md-sys-color-outline);
            border-radius: var(--radius-medium);
            color: white;
            margin-bottom: 16px;
            font-size: 16px;
        }

        @media (max-width: 800px) {
            .app-container { grid-template-columns: 1fr; grid-template-rows: 1fr auto; }
            .controls-sidebar { 
                border-left: none; 
                border-top: 1px solid rgba(255,255,255,0.1); 
                height: 50vh; 
            }
        }
    </style>
</head>
<body>
    <div class="app-container">
        <div class="video-feed">
            <img id="stream" src="/stream" alt="Connecting...">
        </div>
        
        <div class="controls-sidebar">
            <div class="sidebar-header">
                <h2>Controls</h2>
                <div id="status-badge" class="badge ${if(isPro) "pro" else "free"}" 
                     onclick="${if(!isPro) "showUpgrade()" else ""}">
                    ${if(isPro) "PRO ACTIVE" else "FREE VERSION"}
                </div>
            </div>

            <!-- Camera Switcher -->
            <div class="control-section">
                <div class="label-row"><span>Camera</span></div>
                <div class="segmented-btn-group">
                    <button class="segmented-btn" onclick="switchCam('back_wide')">0.5x</button>
                    <button class="segmented-btn active" onclick="switchCam('back_main')">1x (Main)</button>
                    <button class="segmented-btn" onclick="switchCam('front')">Front</button>
                </div>
            </div>

            <!-- Orientation (New) -->
            <div class="control-section">
                <div class="label-row"><span>Orientation</span></div>
                <select id="orientation" class="m3-select" onchange="api('orientation', {type: this.value})">
                    <option value="auto" selected>Auto-Rotate</option>
                    <option value="portrait">Lock Portrait</option>
                    <option value="landscape">Lock Landscape</option>
                </select>
            </div>

             <!-- Resolution -->
            <div class="control-section">
                <div class="label-row"><span>Resolution</span></div>
                <select id="resolution" class="m3-select" onchange="toggleCustomRes(this.value)">
                    <option value="640x480">Low (480p)</option>
                    <option value="1280x720">Medium (720p)</option>
                    <option value="1920x1080" selected>High (1080p)</option>
                    <option value="3840x2160">Ultra HD (4K) ★</option>
                    <option value="custom">Custom...</option>
                </select>
                
                <!-- Secondary Custom Dropdown -->
                <select id="custom-resolution" class="m3-select" style="display: none; margin-top: 8px;" onchange="applyResolution(this.value)">
                    <option disabled selected>Select Resolution</option>
                </select>
            </div>

            <!-- Framerate (New) -->
            <div class="control-section">
                <div class="label-row"><span>Framerate</span></div>
                <select id="fps" class="m3-select" onchange="changeFps(this.value)">
                    <option value="24">24 FPS (Cinematic)</option>
                    <option value="30" selected>30 FPS (Standard)</option>
                    <option value="60">60 FPS (Smooth) ★</option>
                </select>
            </div>
            
            <!-- Zoom -->
            <div class="control-section">
                <div class="label-row">
                    <span>Digital Zoom</span>
                    <span id="zoom-val">1.0x</span>
                </div>
                <input type="range" id="zoom" min="0.5" max="5.0" step="0.1" value="1.0">
            </div>

            <!-- Actions -->
            <div class="action-grid">
                <button class="m3-btn secondary" onclick="toggleFlash()">
                    <span>↯ Flash</span>
                </button>
                <button class="m3-btn" onclick="takePhoto()">
                    <span>📸 Photo</span>
                </button>
            </div>
            
            <button id="recordBtn" class="m3-btn" style="width: 100%; margin-top: 12px; background-color: var(--md-sys-color-primary-container); color: var(--md-sys-color-on-primary-container)" onclick="toggleRecord()">
                <span>🔴 Record</span>
            </button>
            
            <div style="font-size: 11px; opacity: 0.5; margin-top: 24px; line-height: 1.4;">
                🔒 <strong>Note:</strong> Your browser may say "Not Secure". This is normal for local connections. Your stream is safe within your local network.
            </div>
        </div>
    </div>

    <!-- Upgrade Modal -->
    <div id="upgrade-modal">
        <div class="modal-content">
            <h3>Unlock Pro Features</h3>
            <p class="promo-text">Get 4K streaming, 60 FPS, Photo Capture, and more!</p>
            <input type="text" id="coupon" class="coupon-input" placeholder="Enter coupon code">
            <div class="action-grid">
                <button class="m3-btn secondary" onclick="closeUpgrade()">Cancel</button>
                <button class="m3-btn" onclick="submitCoupon()">Unlock</button>
            </div>
        </div>
    </div>

    <script>
        const isPro = ${isPro};
        let currentCam = 'back_main';

        function api(endpoint, body = {}) {
            return fetch('/api/' + endpoint, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(body)
            });
        }
        
        // --- Init ---
        window.onload = function() {
            loadResolutions();
        };

        async function loadResolutions() {
            try {
                const res = await fetch('/api/resolutions');
                if (!res.ok) throw new Error('API fail');
                const list = await res.json();
                
                // Populate Secondary Custom Dropdown
                const customSelect = document.getElementById('custom-resolution');
                
                // Sort high to low
                list.sort((a, b) => (b.width * b.height) - (a.width * a.height));
                
                list.forEach(r => {
                    const val = r.width + 'x' + r.height;
                    const mp = (r.width * r.height / 1000000).toFixed(1);
                    const label = val + ' (' + mp + ' MP)';
                    
                    const opt = document.createElement('option');
                    opt.value = val;
                    opt.text = label;
                    customSelect.appendChild(opt);
                });
            } catch (e) {
                console.error(e);
            }
        }
        
        function toggleCustomRes(val) {
             const customSelect = document.getElementById('custom-resolution');
             if (val === 'custom') {
                 customSelect.style.display = 'block';
             } else {
                 customSelect.style.display = 'none';
                 applyResolution(val);
             }
        }
        
        function applyResolution(val) {
            if (!checkPro(val)) {
                // Revert to 1080p
                document.getElementById('resolution').value = '1920x1080';
                document.getElementById('custom-resolution').style.display = 'none';
                return;
            }
            api('resolution', { value: val });
        }

        // --- Pro Logic ---
        function checkPro(feature) {
            if (!isPro) {
                // 4K check (width > 1920) or 60fps or photo
                if (feature === '60fps' || feature === 'photo') {
                    showUpgrade();
                    return false;
                }
                if (feature.includes('x')) {
                     const width = parseInt(feature.split('x')[0]);
                     if (width > 1920) {
                         showUpgrade();
                         return false;
                     }
                }
            }
            return true;
        }

        function showUpgrade() { document.getElementById('upgrade-modal').style.display = 'flex'; }
        function closeUpgrade() { document.getElementById('upgrade-modal').style.display = 'none'; }
        
        async function submitCoupon() {
            const code = document.getElementById('coupon').value;
            const res = await api('coupon', { code });
            const data = await res.json();
            if (data.success) {
                alert('Pro Unlocked! reloading...');
                location.reload();
            } else {
                alert('Invalid code');
            }
        }

        // --- Recording Logic ---
        let mediaRecorder;
        let recordedChunks = [];
        let isRecording = false;

        function toggleRecord() {
            if (isRecording) {
                stopRecord();
            } else {
                startRecord();
            }
        }

        function startRecord() {
            const img = document.getElementById('stream');
            const canvas = document.createElement('canvas');
            canvas.width = img.naturalWidth || 1920;
            canvas.height = img.naturalHeight || 1080;
            const ctx = canvas.getContext('2d');
            
            // Create a stream from the canvas
            const stream = canvas.captureStream(30); // 30 FPS
            mediaRecorder = new MediaRecorder(stream, { mimeType: 'video/webm' });

            mediaRecorder.ondataavailable = (event) => {
                if (event.data.size > 0) {
                    recordedChunks.push(event.data);
                }
            };

            mediaRecorder.onstop = () => {
                const blob = new Blob(recordedChunks, { type: 'video/webm' });
                const url = URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.style.display = 'none';
                a.href = url;
                a.download = 'rec_' + Date.now() + '.webm';
                document.body.appendChild(a);
                a.click();
                window.URL.revokeObjectURL(url);
                recordedChunks = [];
            };

            // Start recording loop
            mediaRecorder.start();
            isRecording = true;
            
            // UI Update
            const btn = document.getElementById('recordBtn');
            btn.classList.add('recording');
            btn.innerHTML = '<span>⏹ Stop</span>';
            btn.style.backgroundColor = 'var(--md-sys-color-error)';
            
            // Draw loop
            function draw() {
                if (!isRecording) return;
                ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
                requestAnimationFrame(draw);
            }
            draw();
        }

        function stopRecord() {
            if (mediaRecorder && isRecording) {
                mediaRecorder.stop();
                isRecording = false;
                
                // UI Update
                const btn = document.getElementById('recordBtn');
                btn.classList.remove('recording');
                btn.innerHTML = '<span>🔴 Record</span>';
                btn.style.backgroundColor = ''; // Reset to default
            }
        }

        // --- Controls ---
        
        // Resolution UI Sync is now handled by toggleCustomRes/applyResolution above
        
        
        function changeFps(val) {
            if (val === '60' && !checkPro('60fps')) {
                document.getElementById('fps').value = '30';
                return;
            }
            api('fps', { value: val });
        }

        // Zoom (Throttled)
        const zoomSlider = document.getElementById('zoom');
        zoomSlider.oninput = (e) => {
            document.getElementById('zoom-val').innerText = e.target.value + 'x';
            if (!window.zoomTimeout) {
                window.zoomTimeout = setTimeout(() => {
                    api('zoom', { value: e.target.value });
                    window.zoomTimeout = null;
                }, 100);
            }
        };

        // Camera Switch
        function switchCam(id) {
            document.querySelectorAll('.segmented-btn').forEach(b => b.classList.remove('active'));
            event.target.classList.add('active');
            currentCam = id;
            api('camera', { id: id });
            
            // Reload resolutions after short delay for camera to switch
            setTimeout(loadResolutions, 1500);
        }

        // Flash
        function toggleFlash() {
            if (currentCam === 'front') {
                api('flashlight', { type: 'screen' });
            } else {
                api('flashlight', { type: 'torch' });
            }
        }

        // Photo
        function takePhoto() {
            if (!checkPro('photo')) return;
            // Trigger direct download from server API
            window.location.href = '/api/capture';
        }
    </script>
</body>
</html>
        """.trimIndent()
    }
}

