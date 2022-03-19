package com.jordroid.showcase.application

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.jordroid.showcase.application.di.applicationModule
import com.jordroid.showcase.browsing.di.browsingModule
import com.jordroid.showcase.gallery.di.doggoGalleryModule
import com.jordroid.showcase.quotes.di.quoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class ShowcaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ShowcaseApplication)
            modules(applicationModule)
            modules(quoteModule)
            modules(browsingModule)
            modules(doggoGalleryModule)
        }
    }
}
