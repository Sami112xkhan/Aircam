package com.github.digitallyrefined.androidipcamera.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.github.digitallyrefined.androidipcamera.R
import com.github.digitallyrefined.androidipcamera.helpers.InputValidator
import com.github.digitallyrefined.androidipcamera.helpers.ProHelper
import com.github.digitallyrefined.androidipcamera.helpers.SecureStorage
import com.github.digitallyrefined.androidipcamera.helpers.CameraResolutionHelper
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.view.View

class SettingsActivity : AppCompatActivity() {

    private val secureStorage by lazy { SecureStorage(this) }
    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(this) }

    companion object {
        private const val PICK_CERTIFICATE_FILE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_custom)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        setupUI()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun setupUI() {
        // Back Button
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }

        // --- Pro Status Card ---
        setupProCard()

        // --- Camera Section ---
        setupResolution()
        setupFramerate()
        setupZoom()
        setupAutoStart()

        // --- Security Section ---
        setupPublicAccess()
        setupUsername()
        setupPassword()
        setupPort()

        // --- Advanced Section ---
        setupCertificate()
    }
    
    private fun setupProCard() {
        val badge = findViewById<TextView>(R.id.planStatusBadge)
        val description = findViewById<TextView>(R.id.planDescription)
        val button = findViewById<com.google.android.material.button.MaterialButton>(R.id.manageSubscriptionButton)
        
        val isPro = ProHelper.isProUser(this)
        
        if (isPro) {
            badge.text = "PRO"
            badge.setTextColor(getColor(R.color.background_dark))
            badge.background.setTint(getColor(R.color.pro_gold))
            description.text = "You are on the Pro Plan. Enjoy 4K, 60 FPS, and Custom Resolutions."
            button.text = "Revoke License"
            button.setBackgroundColor(getColor(R.color.error))
            button.setOnClickListener {
                confirmRevoke()
            }
        } else {
            badge.text = "FREE"
            badge.setTextColor(getColor(R.color.text_secondary))
            badge.background.setTintList(null) // Reset tint
            description.text = "Free Plan. Limited to 1080p, 30 FPS. Upgrade for 4K & more."
            button.text = "Upgrade to Pro"
            button.setBackgroundColor(getColor(R.color.pro_gold))
            button.setTextColor(getColor(R.color.background_dark))
            button.setOnClickListener {
                showProDialog()
            }
        }
    }
    
    private fun confirmRevoke() {
        AlertDialog.Builder(this)
            .setTitle("Revoke Pro Access")
            .setMessage("Are you sure? You will revert to the Free plan.")
            .setPositiveButton("Revoke") { _, _ ->
                ProHelper.revokePro(this)
                setupProCard() // Refresh
                Toast.makeText(this, "Reverted to Free Plan", Toast.LENGTH_SHORT).show()
                // Restart logic if needed
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showProDialog() {
        val input = EditText(this)
        input.hint = "Enter Coupon Code"
        input.setPadding(50, 40, 50, 40)
        
        AlertDialog.Builder(this)
            .setTitle("Unlock Pro")
            .setView(input)
            .setPositiveButton("Unlock") { _, _ ->
                val code = input.text.toString()
                if (ProHelper.activateProWithCoupon(this, code)) {
                    Toast.makeText(this, "Welcome to Pro!", Toast.LENGTH_LONG).show()
                    setupProCard()
                    // Send broadcast to update other components?
                } else {
                    Toast.makeText(this, "Invalid Code", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setupResolution() {
        val summary = findViewById<TextView>(R.id.resolutionSummary)
        val currentRes = prefs.getString("camera_resolution", "high") ?: "high"
        
        // Show user-friendly summary
        summary.text = when {
            currentRes.contains("x") -> "Custom ($currentRes)"
            currentRes == "high" -> "High (1080p)"
            currentRes == "medium" -> "Medium (720p)"
            currentRes == "low" -> "Low (480p)"
            currentRes == "ultra" -> "Ultra HD (4K)"
            else -> currentRes
        }

        findViewById<View>(R.id.resolutionOption).setOnClickListener {
            val options = arrayOf("Low (480p)", "Medium (720p)", "High (1080p)", "Ultra HD (4K) ★", "Custom...")
            val values = arrayOf("low", "medium", "high", "ultra", "custom")
            
            // Find current selection index
            var selection = values.indexOf(currentRes)
            if (selection == -1 && currentRes.contains("x")) selection = 4 // Custom
            
            AlertDialog.Builder(this)
                .setTitle("Select Resolution")
                .setSingleChoiceItems(options, selection.coerceAtLeast(0)) { dialog, which ->
                    val selectedValue = values[which]
                    
                    if (selectedValue == "custom") {
                        dialog.dismiss()
                        showCustomResolutionDialog(summary)
                        return@setSingleChoiceItems
                    }
                    
                    // Check Pro for Ultra
                    if (selectedValue == "ultra" && !ProHelper.isProUser(this)) {
                         Toast.makeText(this, "4K requires Pro", Toast.LENGTH_SHORT).show()
                         dialog.dismiss()
                         showProDialog()
                         return@setSingleChoiceItems
                    }

                    prefs.edit().putString("camera_resolution", selectedValue).apply()
                    summary.text = options[which].replace(" ★", "") // Remove star for summary
                    dialog.dismiss()
                    Toast.makeText(this, "Restart server to apply", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
    
    private fun showCustomResolutionDialog(summaryView: TextView) {
        val resolutions = MainActivity.getSupportedResolutions() // Fetch from cache
        if (resolutions.isEmpty()) {
            Toast.makeText(this, "No resolutions found (Start camera first)", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Sort high to low
        val sortedList = resolutions.sortedByDescending { it.width * it.height }
        val options = sortedList.map { "${it.width}x${it.height} (${(it.width * it.height / 1000000.0).format(1)}MP)" }.toTypedArray()
        val values = sortedList.map { "${it.width}x${it.height}" }.toTypedArray()
        
        AlertDialog.Builder(this)
            .setTitle("All Resolutions")
            .setItems(options) { _, which ->
                val selectedRes = values[which]
                
                // Pro check for > 1080p
                val width = selectedRes.split("x")[0].toIntOrNull() ?: 0
                if (width > 1920 && !ProHelper.isProUser(this)) {
                    showProDialog()
                    return@setItems
                }
                
                prefs.edit().putString("camera_resolution", selectedRes).apply()
                summaryView.text = "Custom ($selectedRes)"
                Toast.makeText(this, "Custom resolution set", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Back", null)
            .show()
    }

    // Helper extension for specific decimal format if needed, or use String.format
    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
    
    private fun setupFramerate() {
        val summary = findViewById<TextView>(R.id.fpsSummary)
        val currentFps = prefs.getString("camera_fps", "30") ?: "30"
        summary.text = "$currentFps FPS"
        
        findViewById<View>(R.id.fpsOption).setOnClickListener {
            val options = arrayOf("24 FPS (Cinematic)", "30 FPS (Standard)", "60 FPS (Smooth)")
            val values = arrayOf("24", "30", "60")
            
            val currentIndex = values.indexOf(currentFps).coerceAtLeast(1)
            
            AlertDialog.Builder(this)
                .setTitle("Select Framerate")
                .setSingleChoiceItems(options, currentIndex) { dialog, which ->
                    val selected = values[which]
                    if (selected == "60" && !ProHelper.isProUser(this)) {
                        Toast.makeText(this, "60 FPS requires Pro", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        showProDialog()
                        return@setSingleChoiceItems
                    }
                    
                    prefs.edit().putString("camera_fps", selected).apply()
                    summary.text = "$selected FPS"
                    dialog.dismiss()
                    Toast.makeText(this, "Restart server to apply", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun setupZoom() {
        val summary = findViewById<TextView>(R.id.zoomSummary)
        val currentZoom = prefs.getString("camera_zoom", "1.0") ?: "1.0"
        summary.text = "${currentZoom}x"

        findViewById<View>(R.id.zoomOption).setOnClickListener {
            // Use a FrameLayout for proper margin handling in Dialog
            val container = android.widget.FrameLayout(this)
            val padding = (24 * resources.displayMetrics.density).toInt()
            
            val layout = android.widget.LinearLayout(this).apply {
                orientation = android.widget.LinearLayout.VERTICAL
                setPadding(padding, padding, padding, 0)
            }
            container.addView(layout)
            
            val valueText = TextView(this).apply {
                text = "${currentZoom}x"
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                textSize = 24f
                setTextColor(getColor(R.color.text_primary))
                setTypeface(null, android.graphics.Typeface.BOLD)
            }
            layout.addView(valueText)
            
            val slider = com.google.android.material.slider.Slider(this).apply {
                valueFrom = 1.0f
                valueTo = 4.0f
                stepSize = 0.1f
                value = currentZoom.toFloatOrNull()?.coerceIn(1.0f, 4.0f) ?: 1.0f
                addOnChangeListener { _, value, _ ->
                    valueText.text = String.format("%.1fx", value)
                }
            }
            layout.addView(slider)
            
            AlertDialog.Builder(this)
                .setTitle("Digital Zoom")
                .setView(container)
                .setPositiveButton("Set") { _, _ ->
                    val newVal = String.format("%.1f", slider.value)
                    prefs.edit().putString("camera_zoom", newVal).apply()
                    summary.text = "${newVal}x"
                    
                    // Send broadcast
                    val intent = Intent("com.github.digitallyrefined.androidipcamera.RESTART_CAMERA").apply {
                        setPackage(packageName)
                    }
                    sendBroadcast(intent)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
    
    // ... existing setupAutoStart, setupPublicAccess etc ... we keep them via replace/file logic if contiguous, 
    // but here we are rewriting the setupUI functions.
    
    // Helper to keep file valid (since we replaced setupUI top to bottom) uses existing functions below
    private fun setupAutoStart() {
        val switch = findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.autoStartSwitch)
        val summary = findViewById<TextView>(R.id.autoStartSummary)
        val isAutoStart = prefs.getBoolean("auto_start_server", false)
        switch.isChecked = isAutoStart
        summary.text = if (isAutoStart) "Server starts when app opens" else "Manual start required"
        switch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("auto_start_server", isChecked).apply()
            summary.text = if (isChecked) "Server starts when app opens" else "Manual start required"
        }
    }
    
    private fun setupPublicAccess() {
        val switch = findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.publicAccessSwitch)
        val summary = findViewById<TextView>(R.id.publicAccessSummary)
        val isPublic = prefs.getBoolean("public_access", false)
        switch.isChecked = isPublic
        summary.text = if (isPublic) "Anyone can view" else "Requires Login"
        switch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("public_access", isChecked).apply()
            summary.text = if (isChecked) "Anyone can view" else "Requires Login"
            if (isChecked) Toast.makeText(this, "⚠️ Public access enabled", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupUsername() {
        val summary = findViewById<TextView>(R.id.usernameSummary)
        val current = secureStorage.getSecureString(SecureStorage.KEY_USERNAME, "admin") ?: "admin"
        summary.text = current
        findViewById<View>(R.id.usernameOption).setOnClickListener {
            val input = EditText(this)
            input.setText(current)
            AlertDialog.Builder(this).setTitle("Set Username").setView(input).setPositiveButton("Save") { _, _ ->
                val newVal = input.text.toString()
                secureStorage.putSecureString(SecureStorage.KEY_USERNAME, newVal)
                summary.text = newVal
            }.show()
        }
    }

    private fun setupPassword() {
        val summary = findViewById<TextView>(R.id.passwordSummary)
        summary.text = "••••••••"
        findViewById<View>(R.id.passwordOption).setOnClickListener {
            val input = EditText(this)
            input.hint = "New Password"
            AlertDialog.Builder(this).setTitle("Set Password").setView(input).setPositiveButton("Save") { _, _ ->
                val newVal = input.text.toString()
                secureStorage.putSecureString(SecureStorage.KEY_PASSWORD, newVal)
                Toast.makeText(this, "Password saved", Toast.LENGTH_SHORT).show()
            }.show()
        }
    }
    
    private fun setupPort() {
        findViewById<View>(R.id.portOption).setOnClickListener {
            Toast.makeText(this, "Port is currently fixed to 4444", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCertificate() {
        val summary = findViewById<TextView>(R.id.certificateSummary)
        val currentPath = prefs.getString("certificate_path", null)
        if (currentPath != null) {
            val filename = try { Uri.parse(currentPath).lastPathSegment } catch (e: Exception) { currentPath }
            summary.text = filename ?: "Custom Certificate"
        } else {
            summary.text = "Default (Auto-generated)"
        }
        findViewById<View>(R.id.certificateOption).setOnClickListener {
             val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "*/*"; addCategory(Intent.CATEGORY_OPENABLE) }
            startActivityForResult(Intent.createChooser(intent, "Select TLS Certificate"), PICK_CERTIFICATE_FILE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_CERTIFICATE_FILE && resultCode == Activity.RESULT_OK) {
             data?.data?.let { uri ->
                val certificatePath = uri.toString()
                prefs.edit().putString("certificate_path", certificatePath).apply()
                val filename = try { Uri.parse(certificatePath).lastPathSegment } catch (e: Exception) { certificatePath }
                findViewById<TextView>(R.id.certificateSummary).text = filename
                Toast.makeText(this, "Certificate updated. Restart app required.", Toast.LENGTH_LONG).show()
             }
        }
    }
}
