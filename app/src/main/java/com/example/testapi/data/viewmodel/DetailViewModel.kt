package com.example.testapi.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapi.data.response.DetailResponse
import com.example.testapi.data.response.ItemsItem
import com.example.testapi.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadings = MutableLiveData<Boolean>()
    val isLoadings : LiveData<Boolean> = _isLoadings

    private val _isLoadingss = MutableLiveData<Boolean>()
    val isLoadingss: LiveData<Boolean> = _isLoadingss

    private val _setDetailData = MutableLiveData<DetailResponse>()
    val setDetailData: LiveData<DetailResponse> = _setDetailData

    private val _showError: MutableLiveData<Boolean> = MutableLiveData(false)
    val showError: LiveData<Boolean> = _showError

    private val _showErrors: MutableLiveData<Boolean> = MutableLiveData(false)
    val showErrors: LiveData<Boolean> = _showErrors

    private val _showFailed: MutableLiveData<Boolean> = MutableLiveData((false))
    val showFailed: LiveData<Boolean> = _showFailed

    private val _showFaileds: MutableLiveData<Boolean> = MutableLiveData((false))
    val showFaileds: LiveData<Boolean> = _showFaileds

    private val _setApiFragment = MutableLiveData<List<ItemsItem>>()
    val setApiFragment: LiveData<List<ItemsItem>> = _setApiFragment

    private val _setApiFragments = MutableLiveData<List<ItemsItem>>()
    val setApiFragments: LiveData<List<ItemsItem>> = _setApiFragments

    companion object {
        private const val TAG = "MainActivity"
    }

    fun findApi(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _setDetailData.value = responseBody!!
                    } else Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onResponse: ${t.message}")
            }
        })
    }

    fun fragmentApi(user: String) {
        _isLoadings.value = true
        _showError.value = false
        val client = ApiConfig.getApiService().getFollowers(user)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadings.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.isNotEmpty()!!) {
                    _setApiFragment.value = responseBody!!
                } else {
                    _showError.value = true
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                _showFailed.value = true
                _isLoadings.value = false
            }
        })
    }

    fun fragmentUpi(user : String) {
        _isLoadingss.value = true
        _showErrors.value = false
        val client = ApiConfig.getApiService().getFollowing(user)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingss.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.isNotEmpty()!!) {
                    _setApiFragments.value = responseBody!!
                } else {
                    _showErrors.value = true
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                _showFaileds.value = true
                _isLoadingss.value = false
            }
        })
    }
}
