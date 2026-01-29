package com.github.digitallyrefined.androidipcamera.helpers

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * Helper class for managing Pro/Premium feature access
 */
object ProHelper {
    private const val PREF_IS_PRO = "is_pro_user"
    private const val PREF_PRO_ACTIVATED_AT = "pro_activated_at"
    
    // SHA-256 hash of "lifetime100"
    private const val HASH_LIFETIME = "f1b86c442a6112a0d1675159a332d3a3818e9e76803071f30bdb6a0777a6494f"
    
    /**
     * Check if user has Pro access
     */
    fun isProUser(context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean(PREF_IS_PRO, false)
    }
    
    /**
     * Attempt to activate Pro with a coupon code
     * @return true if coupon is valid and Pro activated, false otherwise
     */
    fun activateProWithCoupon(context: Context, couponCode: String): Boolean {
        val normalizedCode = couponCode.trim().lowercase()
        val hashedInput = sha256(normalizedCode)
        
        return when (hashedInput) {
            HASH_LIFETIME -> {
                activatePro(context)
                true
            }
            else -> false
        }
    }

    /**
     * Helper to compute SHA-256 hash
     */
    private fun sha256(text: String): String {
        return try {
            val digest = java.security.MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(text.toByteArray())
            hash.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            ""
        }
    }
    
    /**
     * Activate Pro status
     */
    private fun activatePro(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().apply {
            putBoolean(PREF_IS_PRO, true)
            putLong(PREF_PRO_ACTIVATED_AT, System.currentTimeMillis())
            apply()
        }
    }
    
    /**
     * Get the timestamp when Pro was activated
     */
    fun getProActivatedAt(context: Context): Long {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getLong(PREF_PRO_ACTIVATED_AT, 0)
    }
    
    /**
     * Revoke Pro status (for testing/reset purposes)
     */
    fun revokePro(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().apply {
            putBoolean(PREF_IS_PRO, false)
            remove(PREF_PRO_ACTIVATED_AT)
            apply()
        }
    }
}
