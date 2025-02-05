package ovh.geoffrey_druelle.typicodephotos

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ovh.geoffrey_druelle.typicodephotos.di.databaseModules
import ovh.geoffrey_druelle.typicodephotos.di.networkModules
import ovh.geoffrey_druelle.typicodephotos.di.viewModelModules
import timber.log.Timber

class TypicodePhotosApp: Application() {

    companion object {
        lateinit var appContext : Context
        lateinit var instance: TypicodePhotosApp
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        instance = this

        if (!isRoboUnitTest() || BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(appContext)
        }

        startKoin {
            androidLogger()
            androidContext(this@TypicodePhotosApp)
            modules(
                listOf(
                    networkModules,
                    databaseModules,
                    viewModelModules
                )
            )
        }
    }

    private fun isRoboUnitTest(): Boolean {
        return "robolectric" == Build.FINGERPRINT
    }

    fun getVersionName(): String {
        try {
            val packageInfo = this.packageManager.getPackageInfo(packageName, 0)
            return packageInfo.versionName
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
            Timber.d(e)
        }
        return "0.0.0"
    }
}