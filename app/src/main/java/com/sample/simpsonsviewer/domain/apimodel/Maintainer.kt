package com.sample.simpsonsviewer.domain.apimodel

import com.google.gson.annotations.SerializedName

data class Maintainer(

    @SerializedName("github")
    val github: String = ""
)
