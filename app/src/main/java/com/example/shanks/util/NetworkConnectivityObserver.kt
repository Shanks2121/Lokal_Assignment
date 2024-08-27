package com.example.shanks.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData


// Handles network state changes
class NetworkConnectivityObserver(private val context: Context) : LiveData<Boolean>() {
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Update network connectivity status here
            value = NetworkUtils.isOnline(context!!)
        }
    }

    override fun onActive() {
        super.onActive()
        // Register receiver to listen to network changes
        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        context.registerReceiver(receiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        // Unregister receiver when LiveData is not active
        context.unregisterReceiver(receiver)
    }
}
