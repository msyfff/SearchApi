package com.example.testapi.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapi.data.response.ItemsItem
import com.example.testapi.data.response.TestResponse
import com.example.testapi.data.retrofit.ApiConfig
import com.example.testapi.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _showDefault: MutableLiveData<Boolean> = MutableLiveData(true)
    val showDefault: LiveData<Boolean> = _showDefault

    private val _showError: MutableLiveData<Boolean> = MutableLiveData(false)
    val showError: LiveData<Boolean> = _showError

    private val _showFailed: MutableLiveData<Boolean> = MutableLiveData((false))
    val showFailed: LiveData<Boolean> = _showFailed

    private val _setApiData = MutableLiveData<List<ItemsItem>>()
    val setApiData: LiveData<List<ItemsItem>> = _setApiData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    companion object {
        private const val TAG = "MainActivity"
    }


    fun findApi(user: String) {
        _isLoading.value = true
        _showDefault.value = false
        _showError.value = false
        val client = ApiConfig.getApiService().getApis(user)
        client.enqueue(object : Callback<TestResponse> {
            override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                val responseBody = response.body()
                _isLoading.value = false
                if (response.isSuccessful && responseBody?.items!!.isNotEmpty()) {
                    _setApiData.value = (responseBody.items)
                    _snackbarText.value = Event(responseBody.totalCount)
                } else {
                    _setApiData.value = (responseBody?.items)
                    _showError.postValue(true)
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                _isLoading.value = false
                _showFailed.value = true
                Log.e(TAG, "onResponse: ${t.message}")
            }
        })
    }
}