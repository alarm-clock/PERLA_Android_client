package com.example.jmb_bms.connectionService.models

import android.content.Context
import com.example.jmb_bms.model.RefreshVals
import com.example.jmb_bms.model.icons.Symbol
import locus.api.objects.extra.Location
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.collections.HashSet


class TeamProfile(
    val _id: String,
    teamIcon: String,
    var teamName: String,
    var teamLead: String,
    private val appCtx: Context,
    var teamLocation: Location?,
    var teamEntry: MutableSet<String> = Collections.synchronizedSet(HashSet<String>()),
    var thisClientSharingLoc: Boolean = false,
    var sharingLocDelay: RefreshVals = RefreshVals.S5
)
{
    var teamIcon = teamIcon
        set(value) {
            field = value
            teamSymbol = Symbol(value,appCtx)
        }

    var teamSymbol = Symbol(teamIcon,appCtx)

    fun copy() = TeamProfile(_id, teamIcon, teamName, teamLead, appCtx, teamLocation ,teamEntry, thisClientSharingLoc, sharingLocDelay)

    companion object{

        fun createTeamProfileFromParams(params: Map<String, Any?>, appCtx: Context): TeamProfile?
        {
            val _id = params["_id"] as? String ?: return null
            val teamName = params["teamName"] as? String ?: return null
            val teamIcon = params["teamIcon"] as? String ?: return null
            val teamLead = params["teamLead"] as? String ?: return null
            val lat = (params["teamLocation"] as? Map<String, Any?>)?.get("lat") as? Double
            val long = (params["teamLocation"] as? Map<String, Any?>)?.get("long") as? Double

            val teamLocation = if(lat != null && long != null) Location(lat,long) else null
            val teamEntry = params["teamEntry"] as? List<String> ?: return null

            return TeamProfile(
                _id,teamIcon,teamName,teamLead,appCtx,teamLocation, CopyOnWriteArraySet(teamEntry)
            )
        }
    }
}
