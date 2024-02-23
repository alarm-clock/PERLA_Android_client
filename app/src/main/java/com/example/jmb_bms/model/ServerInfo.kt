package com.example.jmb_bms.model

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServerInfo(context: Context, private val shPref: SharedPreferences) {

    var ipV4: String = ""
        set(value) {
            ipV4WasEntered = value != ""
            field = value
            shPref.editPreferences { editor ->
                editor.putString("ServerInfo_IP", value)
                editor.putBoolean("ServerInfo_IPent",ipV4WasEntered)
                editor.apply()
            }
        }

    var ipV4WasEntered: Boolean = false
        private set


    private var _port: Int = -1
    val port : Int get() = _port

    var portString: String = ""
        set(value) {
            portWasEntered = value != ""
            if(portWasEntered)
            {
                _port = try {
                    value.toInt()
                } catch (_: Exception)
                {
                    -1
                }
                portWasEntered = _port != -1

            } else {
                _port = -1
            }
            field = value
            shPref.editPreferences { editor ->
                editor.putString("ServerInfo_Port",if(_port != -1) value else "")
                editor.putBoolean("ServerInfo_PortEnt", portWasEntered)
                editor.apply()
            }
        }

    var portWasEntered: Boolean = false
        private set

    var userName: String = ""
        set(value) {
            userNameWasEntered = value != ""
            field = value

            shPref.editPreferences { editor ->
                editor.putString("ServerInfo_User", value)
                editor.putBoolean("ServerInfo_UserEnt",userNameWasEntered)
                editor.apply()
            }
        }
    var userNameWasEntered: Boolean = false
        private set

    fun everyThingEntered() = (userNameWasEntered && portWasEntered && ipV4WasEntered)


    val symbol : Symbol

    init{
        ipV4 = shPref.getString("ServerInfo_IP", "") ?: ""
        ipV4WasEntered = shPref.getBoolean("ServerInfo_IPent", false)

        portString = shPref.getString("ServerInfo_Port", "") ?: ""
        portWasEntered = shPref.getBoolean("ServerInfo_PortEnt", false)

        userName = shPref.getString("ServerInfo_User", "") ?: ""
        userNameWasEntered = shPref.getBoolean("ServerInfo_UserEnt", false)

        val symbolCode = shPref.getString("ServerInfo_Symbol", "")

        symbol = if (symbolCode == "" || symbolCode == null) Symbol(context)
                     else Symbol(symbolCode, context)

        println("$ipV4WasEntered $portWasEntered $userNameWasEntered")
    }
    private fun SharedPreferences.editPreferences(operation: (SharedPreferences.Editor) -> Unit)
    {
        val scope = CoroutineScope(Dispatchers.IO)

        scope.launch {
            withContext(Dispatchers.IO)
            {
                val ed = edit()
                operation(ed)
                ed.apply()
            }
        }
    }
}