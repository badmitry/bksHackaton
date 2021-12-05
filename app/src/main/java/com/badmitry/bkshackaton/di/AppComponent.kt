package com.badmitry.bkshackaton.di

import android.app.Application
import com.badmitry.bkshackaton.App
import com.badmitry.bkshackaton.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        AppModule::class,
        NavigationModule::class,
        ComponentsModule::class,
        NetworkModule::class,
        SchedulersModule::class,
        InteractorsModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }
}