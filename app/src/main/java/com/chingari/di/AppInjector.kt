package com.chingari.di

import com.chingari.db.database.DatabaseCache
import com.chingari.db.database.DatabaseCache.Companion.getInstance
import com.chingari.mvvm.CurrentWeatherViewModel
import com.chingari.mvvm.ForeCastViewModel
import com.chingari.mvvm.LocationViewModel
import com.chingari.network.RestClient
import com.chingari.network.RestClient.webServices
import com.chingari.repository.CurrentWeatherRepository
import com.chingari.repository.ForecastRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CurrentWeatherRepository(get()) }
    single { ForecastRepository(get()) }

}

val networkModule = module {
    single { RestClient }
    single { webServices() }
}

val databaseModule = module {
    single { DatabaseCache }
    single { getInstance() }
}

val viewModelModule = module {
    viewModel {
        CurrentWeatherViewModel(get())
    }
    viewModel {
        ForeCastViewModel(get())
    }
    viewModel {
        LocationViewModel(get())
    }
}