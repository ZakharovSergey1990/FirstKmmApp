package com.example.firstkmmapp.di

import com.example.firstkmmapp.KMMContext
import com.example.firstkmmapp.api.TestHttpApi
import com.example.firstkmmapp.api.TestHttpApiImpl
import com.example.firstkmmapp.datasource.DatabaseQueries
import com.example.firstkmmapp.datasource.DatabaseQueriesImpl
import com.example.firstkmmapp.datasource.DriverFactory
import com.example.firstkmmapp.datasource.localdatasource.*
import com.example.firstkmmapp.repository.*
import org.kodein.di.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object MultiplatformSDK {
    internal val di: DirectDI
        get() = requireNotNull(_di)
    private var _di: DirectDI? = null

    fun init(context: KMMContext){
        print("init MultiplatformSDK")
        val configurationModule = DI.Module(
            name = "configurationModule",
            init = {
                //  bind<Configuration>() with singleton {configuration}
                bind <DriverFactory>() with singleton { DriverFactory(context) }
                bind <DatabaseQueries>() with singleton { DatabaseQueriesImpl(instance()) }
                bind <AlbumDataSource>() with singleton { AlbumDataSourceImpl(instance()) }
                bind <PhotoDataSource>() with singleton { PhotoDataSourceImpl(instance()) }
                bind <UserDataSource>() with singleton { UserDataSourceImpl(instance()) }
                bind <AlbumRepository>() with singleton { AlbumRepositoryImpl(instance()) }
                bind <PhotoRepository>() with singleton { PhotoRepositoryImpl(instance()) }
                bind <TestHttpApi>() with singleton { TestHttpApiImpl() }
                bind <UserRepository>() with singleton { UserRepositoryImpl(instance(), instance(), instance(), instance()) }
            }
        )

        if(_di != null){
            _di = null
        }

        val direct = DI{
            importAll(configurationModule)
        }.direct
        print("init MultiplatformSDK direct = ")
        _di = direct
    }
}

val MultiplatformSDK.userRepository: UserRepository
    get() = MultiplatformSDK.di.instance()

val MultiplatformSDK.albumRepository: AlbumRepository
    get() = MultiplatformSDK.di.instance()

val MultiplatformSDK.photoRepository: PhotoRepository
    get() = MultiplatformSDK.di.instance()
