package com.saitejajanjirala.imdbclonekotlin.ui.bookmark

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.imdbclonekotlin.db.DatabaseService
import com.saitejajanjirala.imdbclonekotlin.listeners.BookMarkListener
import com.saitejajanjirala.imdbclonekotlin.models.Result
import com.saitejajanjirala.imdbclonekotlin.network.ApiService
import com.saitejajanjirala.imdbclonekotlin.utils.Helper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val apiService: ApiService,
    private val helper: Helper,
    private val databaseService: DatabaseService
) : ViewModel() {
    val bookMarkResults=MutableLiveData<List<Result>>()
    val msg=MutableLiveData<String>()
    fun clearBookMarks() {
        viewModelScope.launch {
            try {
                coroutineScope {
                    try {
                        val resultDao = databaseService.resultDao()
                        resultDao.clearBookMarks()
                        getData()
                    } catch (e: Exception) {
                        msg.postValue("something went wrong")
                    }
                }
            }
            catch (e:Exception){
                Log.i("clear bookmarks error",e.message.toString())
                msg.postValue("something went wrong")
            }
        }
    }

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            try{
                val results=databaseService.resultDao().bookMarkedResults()
                Log.i("bookmark results",results.toString())
                bookMarkResults.postValue(results)
            }
            catch (e:Exception){
                Log.i("get data error bookmark",e.message.toString())
            }
        }
    }

    fun setBookMarkStatus(result: Result?, position: Int, listener: BookMarkListener?) {
        viewModelScope.launch {
            try{
               if(result!=null && listener!=null){
                   result.bookmarked=!result.bookmarked
                   databaseService.resultDao().updateBookMarked(result)
                   listener.onBookMarkClicked(position,result)
               }
            }
            catch (e:Exception){
                msg.postValue("Something went wrong")
                Log.i("get data error bookmark",e.message.toString())
            }
        }
    }


}