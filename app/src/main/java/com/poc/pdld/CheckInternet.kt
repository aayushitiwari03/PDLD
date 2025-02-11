package com.poc.pdld

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.MutableState

fun checkInternetAvailability() : Boolean {
        return true
}

enum class Status{
    ONLINE, OFFLINE
}

class NetworkReceiver(private val isOnline: MutableState<Boolean>) : BroadcastReceiver(

) {
    override fun onReceive(context: Context, intent: Intent) {
        isOnline.value = checkNetworkStatus(context)
    }

    companion object {
        fun register(context: Context, isOnline: MutableState<Boolean>): NetworkReceiver {
            val receiver = NetworkReceiver(isOnline)
            val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            context.registerReceiver(receiver, filter)
            return receiver
        }

        fun unregister(context: Context, receiver: NetworkReceiver) {
            try {
                context.unregisterReceiver(receiver)
            } catch (e: IllegalArgumentException) {
                Log.w("NetworkReceiver", "Receiver not registered or already unregistered.")
            }
        }

        fun checkNetworkStatus(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
    }
}