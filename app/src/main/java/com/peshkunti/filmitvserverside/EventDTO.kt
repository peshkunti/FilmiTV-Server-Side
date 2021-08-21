package com.peshkunti.filmitvserverside

import com.google.firebase.Timestamp

data class EventDTO(
    val dataTime: Timestamp? = null,
    val channel: String? = null,
    val fresh: Boolean? = null,
    val presentation: String? = null,
)