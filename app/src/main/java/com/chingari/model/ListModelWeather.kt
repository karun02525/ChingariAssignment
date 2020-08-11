package com.chingari.model
import com.google.gson.annotations.SerializedName


data class ListModelWeather(
    @SerializedName("city")
    var city: City? = null,
    @SerializedName("cnt")
    var cnt: Int? = null,
    @SerializedName("cod")
    var cod: String? = null,
    @SerializedName("list")
    var list: List<ListModel>? = null,
    @SerializedName("message")
    var message: Int? = null
)

data class City(
    @SerializedName("coord")
    var coord: Coord? = null,
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("sunrise")
    var sunrise: Int? = null,
    @SerializedName("sunset")
    var sunset: Int? = null,
    @SerializedName("timezone")
    var timezone: Int? = null
)

data class ListModel (
    @SerializedName("clouds")
    var clouds: Clouds? = null,
    @SerializedName("dt")
    var dt: Long? = null,
    @SerializedName("dt_txt")
    var dtTxt: String? = null,
    @SerializedName("main")
    var main: Main? = null,
    @SerializedName("pop")
    var pop: Double? = null,
    @SerializedName("rain")
    var rain: Rain? = null,
    @SerializedName("sys")
    var sys: Sys? = null,
    @SerializedName("visibility")
    var visibility: Int? = null,
    @SerializedName("weather")
    var weather: List<Weather>? = null,
    @SerializedName("wind")
    var wind: Wind? = null
)


