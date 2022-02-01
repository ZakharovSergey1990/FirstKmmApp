package com.example.firstkmmapp.di

import com.example.firstkmmapp.KMMContext
import com.example.firstkmmapp.api.TestHttpApi
import com.example.firstkmmapp.api.TestHttpApiImpl
import com.example.firstkmmapp.data.DriverFactory
import com.example.firstkmmapp.data.UserDataSource
import com.example.firstkmmapp.data.UserDataSourceImpl
import com.example.firstkmmapp.repository.UserRepository
import com.example.firstkmmapp.repository.UserRepositoryImpl
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
                bind <UserDataSource>() with singleton { UserDataSourceImpl(instance()) }
                bind <TestHttpApi>() with singleton { TestHttpApiImpl() }
                bind <UserRepository>() with singleton { UserRepositoryImpl(instance(), instance()) }
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
