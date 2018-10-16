package pro.ahoora.zhin.om.base

import androidx.lifecycle.ViewModel
import pro.ahoora.zhin.om.di.DaggerViewModelInjector
import pro.ahoora.zhin.om.di.RetrofitModule
import pro.ahoora.zhin.om.viewModels.MainViewModel

abstract class BaseViewModel : ViewModel() {

    private val injector = DaggerViewModelInjector.builder()
            .retrofitModule(RetrofitModule)
            .build()

    init {
        inject()
    }

    private fun inject(){
        when(this) {
            is MainViewModel-> injector.inject(this)
        }
    }
}