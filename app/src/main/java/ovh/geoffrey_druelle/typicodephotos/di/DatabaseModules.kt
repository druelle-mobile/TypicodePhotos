package ovh.geoffrey_druelle.typicodephotos.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ovh.geoffrey_druelle.typicodephotos.data.local.database.TypicodePhotosDatabase

val databaseModules = module {
    single { TypicodePhotosDatabase.getInstance(androidApplication()) }
    single { get<TypicodePhotosDatabase>().photoDao() }
}