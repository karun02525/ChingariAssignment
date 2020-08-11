package com.chingari.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chingari.R
import com.chingari.model.ListModel
import com.chingari.utils.dateFormatStyle
import kotlinx.android.synthetic.main.weather_item.view.*

class WeatherAdapter (var list: List<ListModel>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount():Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =  holder.bindItems(list[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItems(model: ListModel) {
            itemView.run {
                tv_day.text= model.dt?.dateFormatStyle() +" "+ model.dtTxt
                tv_temp.text="Temperature : "+model.main?.temp+ "°C" + ", Wind Speed : "+model.wind?.speed+ "km/h"
                tv_clouds.text="Weather : "+ model.weather?.get(0)!!.main+ ", " + model.weather?.get(0)!!.description
            }
        }
    }


}