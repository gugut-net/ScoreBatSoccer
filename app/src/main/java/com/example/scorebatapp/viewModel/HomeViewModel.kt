package com.example.scorebatapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scorebatapp.data.model.ResponseItemModel
import com.example.scorebatapp.data.repository.Repository
import com.example.scorebatapp.util.ResponseType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var homeObject: ResponseItemModel? = null

    private val _selected = MutableLiveData<ResponseType<List<ResponseItemModel>>>()
    val selected: LiveData<ResponseType<List<ResponseItemModel>>> = _selected

    fun getCompetitionList() {
        viewModelScope.launch {
            _selected.postValue(ResponseType.Loading)
            val response = repository.getCompetitionList()
            if (response.isSuccessful) {
                response.body()?.let {
                    it.response?.let { faisel ->
                        _selected.postValue(ResponseType.Success(faisel))
                    }
                }
            }else {
                _selected.postValue(ResponseType.Error(response.message()))
            }
        }
    }

}