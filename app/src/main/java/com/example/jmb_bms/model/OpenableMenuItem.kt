package com.example.jmb_bms.model

import com.example.jmb_bms.viewModel.ServerInfoVM

data class OpenableMenuItem(val id: String,
                            val text: String,
                            val topLevelMod2525: TopLevelMods2525?,
                            val iconCode: IconCode2525?,
                            val onClick : ((vm: ServerInfoVM) -> Unit)? = null)