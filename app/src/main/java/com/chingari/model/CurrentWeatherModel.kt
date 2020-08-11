package com.chingari.model
import com.google.gson.annotations.SerializedName


data class CurrentWeatherModel(
    @SerializedName("base")
    var base: String? = null,
    @SerializedName("clouds")
    var clouds: Clouds? = null,
    @SerializedName("cod")
    var cod: Int? = null,
    @SerializedName("coord")
    var coord: Coord? = null,
    @SerializedName("dt")
    var dt: Long? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("main")
    var main: Main? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("rain")
    var rain: Rain? = null,
    @SerializedName("sys")
    var sys: Sys? = null,
    @SerializedName("timezone")
    var timezone: Int? = null,
    @SerializedName("visibility")
    var visibility: Int? = null,
    @SerializedName("weather")
    var weather: List<Weather>? = null,
    @SerializedName("wind")
    var wind: Wind? = null
)

data class Clouds(
    @SerializedName("all")
    var all: Int? = null
)

data class Coord(
    @SerializedName("lat")
    var lat: Double? = null,
    @SerializedName("lon")
    var lon: Double? = null
)

data class Main(
    @SerializedName("feels_like")
    var feelsLike: Double? = null,
    @SerializedName("grnd_level")
    var grndLevel: Int? = null,
    @SerializedName("humidity")
    var humidity: String? = null,
    @SerializedName("pressure")
    var pressure: String? = null,
    @SerializedName("sea_level")
    var seaLevel: String? = null,
    @SerializedName("temp")
    var temp: Double? = null,
    @SerializedName("temp_max")
    var tempMax: Double? = null,
    @SerializedName("temp_min")
    var tempMin: Double? = null
)

data class Rain(
    @SerializedName("1h")
    var h: Double? = null
)

data class Sys(
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("sunrise")
    var sunrise: Long? = null,
    @SerializedName("sunset")
    var sunset: Long? = null
)

data class Weather(
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("icon")
    var icon: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("main")
    var main: String? = null
)

data class Wind(
    @SerializedName("deg")
    var deg: Int? = null,
    @SerializedName("speed")
    var speed: String? = null
)