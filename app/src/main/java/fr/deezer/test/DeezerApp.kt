package fr.deezer.test

import android.app.Application
import android.os.Build
import fr.deezer.test.di.AppComponent
import fr.deezer.test.di.AppModule
import fr.deezer.test.di.DaggerAppComponent
import com.google.android.gms.security.ProviderInstaller

class DeezerApp :Application(){

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        if (Build.VERSION.SDK_INT == 19) {
            try {
                ProviderInstaller.installIfNeeded(this)
            } catch (ignored: Exception) {
            }
        }
    }
    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
            private set
    }
}