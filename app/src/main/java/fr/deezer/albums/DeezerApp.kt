package fr.deezer.albums

import android.os.Build
import androidx.multidex.MultiDexApplication
import com.google.android.gms.security.ProviderInstaller
import fr.deezer.albums.di.AppModule.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DeezerApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT == 19) {
            try {
                ProviderInstaller.installIfNeeded(this)
            } catch (ignored: Exception) {
            }
        }

        startKoin {
            androidContext(this@DeezerApp)
            modules(appModule)
        }
    }
    /*companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
            private set
    }*/
}