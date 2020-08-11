package com.chingari.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chingari.R
import com.chingari.model.ListModel
import com.chingari.mvvm.ForeCastViewModel
import com.chingari.adapter.WeatherAdapter
import com.chingari.utils.showSnack
import kotlinx.android.synthetic.main.activity_weather_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class ForeCastActivity : AppCompatActivity() {

    private val viewModel: ForeCastViewModel by viewModel()
    var weatherList = mutableListOf<ListModel>()
    private lateinit var mWeatherAdapter: WeatherAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_list)
        setUpUI()
        setupViewModel()
        loadCurrentWeatherApi()
    }

    private fun loadCurrentWeatherApi() {
        val lat = intent?.getDoubleExtra("lat", 0.0)
        val log = intent?.getDoubleExtra("lon", 0.0)
        viewModel.loadForeCastApi(lat!!, log!!)

    }

    private fun setupViewModel() {
        viewModel.getWeatherModel().observe(this, Observer {
            if (it != null) {
                progressHideShow(false)
                weatherList = it as MutableList<ListModel>
                notifyAdapter()
            }
        })

        viewModel.getErrorMessage().observe(this, Observer {
            progressHideShow(false)
            showSnack(it)
        })
    }

    private fun setUpUI() {
        rv_weather.run {
            layoutManager = LinearLayoutManager(this@ForeCastActivity)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun notifyAdapter() {
        mWeatherAdapter = WeatherAdapter(weatherList)
        rv_weather.adapter = mWeatherAdapter
        mWeatherAdapter.notifyDataSetChanged()
    }

    private fun progressHideShow(flag: Boolean) {
        if (flag) {
            progress_circular.visibility = View.VISIBLE
            rv_weather.visibility = View.GONE
        } else {
            rv_weather.visibility = View.VISIBLE
            progress_circular.visibility = View.GONE
        }
    }

}
