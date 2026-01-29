package com.github.digitallyrefined.androidipcamera.activities

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.digitallyrefined.androidipcamera.R
import com.github.digitallyrefined.androidipcamera.helpers.ProHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ProActivity : AppCompatActivity() {

    private lateinit var proCard: MaterialCardView
    private lateinit var alreadyProCard: MaterialCardView
    private lateinit var couponInput: TextInputEditText
    private lateinit var couponInputLayout: TextInputLayout
    private lateinit var activateButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pro)

        // Initialize views
        proCard = findViewById(R.id.proCard)
        alreadyProCard = findViewById(R.id.alreadyProCard)
        couponInput = findViewById(R.id.couponInput)
        couponInputLayout = findViewById(R.id.couponInputLayout)
        activateButton = findViewById(R.id.activateButton)

        // Back button
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }

        // Check if already pro
        updateProStatus()

        // Activate button click
        activateButton.setOnClickListener {
            attemptActivation()
        }

        // Handle keyboard done action
        couponInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                attemptActivation()
                true
            } else {
                false
            }
        }
    }

    private fun updateProStatus() {
        if (ProHelper.isProUser(this)) {
            proCard.visibility = View.GONE
            alreadyProCard.visibility = View.VISIBLE
        } else {
            proCard.visibility = View.VISIBLE
            alreadyProCard.visibility = View.GONE
        }
    }

    private fun attemptActivation() {
        val couponCode = couponInput.text?.toString() ?: ""

        if (couponCode.isBlank()) {
            couponInputLayout.error = "Please enter a coupon code"
            return
        }

        couponInputLayout.error = null

        if (ProHelper.activateProWithCoupon(this, couponCode)) {
            // Success!
            Toast.makeText(this, "🎉 Pro activated! Enjoy ad-free access forever!", Toast.LENGTH_LONG).show()
            updateProStatus()
        } else {
            // Invalid code
            couponInputLayout.error = "Invalid coupon code"
            Toast.makeText(this, "Invalid coupon code. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }
}
