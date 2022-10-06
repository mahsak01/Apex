package com.example.apex

import android.app.Application
import androidx.room.Room
import com.example.apex.data.db.AppDatabase
import com.example.apex.data.implement.ApexRepositoryImplement
import com.example.apex.data.repository.ApexRepository
import com.example.apex.data.source.Remote.ApexRemoteDataSource
import com.example.apex.service.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModules = module {
            single {
                Room.databaseBuilder(this@App, AppDatabase::class.java, "apex_db_app")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }

            single { createApiServiceInstance() }
            factory<ApexRepository> {
                ApexRepositoryImplement(get<AppDatabase>().apexDao(),ApexRemoteDataSource(get()))
            }
            viewModel{ApexViewModel(get())}
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

    }
}