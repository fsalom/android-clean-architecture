package es.rudo.androidbaseproject.data.model

import com.google.gson.annotations.SerializedName

data class ReponseApiModel(
    @SerializedName("info") val info: InfoApiModel? = InfoApiModel(),
    @SerializedName("results") val results : List<CharacterApiModel> = listOf()
)

data class InfoApiModel(
    @SerializedName("count") var count : Int? = 0,
    @SerializedName("pages") var pages : Int? = 0,
    @SerializedName("next") var next  : String? = "null",
    @SerializedName("prev") var prev  : String? = ""
)