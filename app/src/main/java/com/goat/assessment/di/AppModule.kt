package com.goat.assessment.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.location.LocationManager
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    internal fun provideResources(context: Context): Resources {
        return context.resources
    }

    @Provides
    fun provideLocationManager(app: Application): LocationManager {
        return app.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }


}
