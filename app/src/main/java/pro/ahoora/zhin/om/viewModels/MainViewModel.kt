package pro.ahoora.zhin.om.viewModels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pro.ahoora.zhin.om.base.BaseViewModel
import pro.ahoora.zhin.om.model.Patient
import pro.ahoora.zhin.om.repositories.local.RESTApi
import javax.inject.Inject

class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var api: RESTApi

    private lateinit var subscription: Disposable

    private val tag = this.javaClass.canonicalName

    val list: MutableLiveData<List<String>> = MutableLiveData()
    var patinet: MutableLiveData<List<Patient>> = MutableLiveData()
    var patientProgressVisibility: MutableLiveData<Int> = MutableLiveData()


    init {
        patientProgressVisibility.value = View.INVISIBLE
    }

    private lateinit var patientDisposable: Disposable
    fun getPatient(id: Int) {
        patientProgressVisibility.value = View.VISIBLE
        patientDisposable = api.getAllLocalPatient()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ item: List<Patient>? ->
                    item ?: throw NullPointerException("getPatient() : \n\tnull response")
                    patinet.value = item
                    patientProgressVisibility.value = View.INVISIBLE
                }, {
                    Log.e("MainViewModel", it.message)
                })
    }


    override fun onCleared() {
        super.onCleared()
        if (this::subscription.isInitialized)
            subscription.dispose()
    }

    private fun subscribed() {
        Log.e(tag, "subscribed")
    }

    private fun terminated() {
        Log.e(tag, "terminated")
    }

    private fun error() {
        Log.e(tag, "error")
    }

    private fun completed() {
        Log.e(tag, "completed")
    }

    private fun next(list: List<String>) {
        Log.e(tag, "next - ${list.size}")
    }


}
