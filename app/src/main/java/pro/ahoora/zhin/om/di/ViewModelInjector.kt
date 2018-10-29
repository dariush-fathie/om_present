package pro.ahoora.zhin.om.di

import dagger.Component
import pro.ahoora.zhin.om.viewModels.MainViewModel
import javax.inject.Singleton


@Singleton
@Component(modules = [RetrofitModule::class])
interface ViewModelInjector {

    fun inject(mainViewModel: MainViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun retrofitModule(retrofitModule: RetrofitModule):Builder

    }

}
