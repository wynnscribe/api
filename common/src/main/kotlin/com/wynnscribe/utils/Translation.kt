package com.wynnscribe.utils

import com.wynnscribe.models.Project.Original.Translation
import com.wynnscribe.models.Project.Original.Translation.Vote

fun List<Translation.Details>.best(): Translation.Details? {
    val sorted = this.filter { it.status != Translation.Status.Rejected }
        .sortedBy { it.votes.sumOf { if(it.type == Vote.Type.Up) 1.toInt() else -1 } }
    return sorted.lastOrNull()
}