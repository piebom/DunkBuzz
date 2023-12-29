package com.pieterbommele.dunkbuzz.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

/**
 * An OkHttp interceptor that checks for network connectivity before making a network request.
 *
 * @param context The application context used to access the connectivity service.
 */
class NetworkConnectionInterceptor(val context: Context) : Interceptor {
    /**
     * Intercepts the network request and checks if there is an active network connection.
     * Throws an [IOException] if there is no active connection.
     *
     * @param chain The interceptor chain.
     * @return The response from the network if there is a connection.
     * @throws IOException If there is no active network connection.
     */
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        if (!isConnected(context=context)) {
            Log.i("retrofit", "there is no connection")
            throw IOException()
        } else {
            val builder = chain.request().newBuilder()
            return@run chain.proceed(builder.build())
        }
    }

    /**
     * Checks if the device is currently connected to a network.
     *
     * @param context The application context used to access the connectivity service.
     * @return True if there is an active network connection, false otherwise.
     */
    fun isConnected(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }

        return result
    }
}
