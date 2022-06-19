package com.iav.bowling

import android.app.Application
import com.iav.bowling.di.ApplicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

       //  Start Koin
        startKoin{
            androidLogger()  // use AndroidLogger as Koin Logger - default Level.INFO
            androidContext(this@MainApplication) // use the Android context given there
            androidFileProperties() // load properties from assets/koin.properties file
            koin.loadModules(// module list
                listOf(
                    ApplicationModule.applicationModule,
                )
            )
        }
    }
    
}
