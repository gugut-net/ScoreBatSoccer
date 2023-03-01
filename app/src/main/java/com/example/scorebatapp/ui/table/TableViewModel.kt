package com.example.scorebatapp.ui.table

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scorebatapp.data.model.ResponseItemModel
import com.example.scorebatapp.data.repository.Repository
import com.example.scorebatapp.data.standing.Standings
import com.example.scorebatapp.util.ResponseType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TableViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var tableObject: Standings? = null

    private val _result = MutableLiveData<ResponseType<List<Standings>>>()
    val result: LiveData<ResponseType<List<Standings>>> = _result

    fun getStandingList(){
        viewModelScope.launch {
            repository.getStandingList().collect{
                _result.postValue(it)
            }
        }
    }
}
