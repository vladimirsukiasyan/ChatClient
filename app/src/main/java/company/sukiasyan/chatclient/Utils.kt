package company.sukiasyan.chatclient

import android.content.Context

const val HTTP_CONFIGURATION="HTTP_CONFIGURATION"
const val DEFAULT_PORT="8080"
const val DEFAULT_HOST="localhost"

fun Context.saveHttpConfig(host:String, port:String){
    val edit = getSharedPreferences(HTTP_CONFIGURATION, Context.MODE_PRIVATE)?.edit()
    edit?.putString("host", host)?.apply()
    edit?.putString("port", port)?.apply()
}

fun Context.getPort()= getSharedPreferences(HTTP_CONFIGURATION,Context.MODE_PRIVATE).getString("port", DEFAULT_PORT)!!

fun Context.getHost()= getSharedPreferences(HTTP_CONFIGURATION,Context.MODE_PRIVATE).getString("localhost", DEFAULT_HOST)!!