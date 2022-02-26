package com.yigithan.movieapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("Title")
    var movieName: String?,
    @SerializedName("Genre")
    var movieGenre: String?,
    @SerializedName("Plot")
    var moviePlot: String?,
    @SerializedName("Year")
    var movieYear: String?,
    @SerializedName("Runtime")
    var movieRuntime: String?,
    @SerializedName("Director")
    var movieDirector: String?,
    @SerializedName("imdbRating")
    var movieImdb: String?,
    @SerializedName("Poster")
    var moviePhoto: String?,
) : Parcelable