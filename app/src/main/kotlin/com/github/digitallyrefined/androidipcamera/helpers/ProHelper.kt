package com.github.digitallyrefined.androidipcamera.helpers

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * Helper class for managing Pro/Premium feature access
 */
object ProHelper {
    private const val PREF_IS_PRO = "is_pro_user"
    private const val PREF_PRO_ACTIVATED_AT = "pro_activated_at"
    
    // Valid coupon codes
    private const val COUPON_LIFETIME = "lifetime100"
    
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
        
        return when (normalizedCode) {
            COUPON_LIFETIME -> {
                activatePro(context)
                true
            }
            else -> false
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
