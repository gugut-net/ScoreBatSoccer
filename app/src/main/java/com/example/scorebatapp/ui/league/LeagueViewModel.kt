package com.example.scorebatapp.ui.league

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scorebatapp.data.matches.MatchesModel
import com.example.scorebatapp.data.matches.MatchesResponseModel
import com.example.scorebatapp.data.repository.Repository
import com.example.scorebatapp.data.standing.Standings
import com.example.scorebatapp.util.ResponseType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var tableObject: MatchesModel? = null

    private val _result = MutableLiveData<ResponseType<List<MatchesResponseModel>>>()
    val result: LiveData<ResponseType<List<MatchesResponseModel>>> = _result


    fun getMatchesList() {
        viewModelScope.launch {
            _result.postValue(ResponseType.Loading)
            val response = repository.getMatchesList()
            if (response.isSuccessful) {
                response.body()?.let {
                    it.season?.let { faisel ->
                        _result.postValue(ResponseType.Success(faisel))
                    }
                }
            }else {
                _result.postValue(ResponseType.Error(response.message()))
            }
        }
    }

}