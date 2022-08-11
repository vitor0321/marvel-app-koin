package com.example.marvelapp.presentation.fragment.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.core.usecase.GetIntroUseCase
import com.example.core.usecase.SaveIntroUseCase
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.marvelapp.presentation.common.extensions.watchStatus

class IntroViewModel(
    private val saveIntroUseCase: SaveIntroUseCase,
    private val getIntroUseCase: GetIntroUseCase,
    private val coroutineDispatchers: CoroutinesDispatchers
) : ViewModel() {

    private val actionIntro = MutableLiveData<ActionIntro>()

    val stateIntro: LiveData<UiStateIntro> = actionIntro
        .switchMap { actionIntro ->
            liveData(coroutineDispatchers.main()) {
                when (actionIntro) {
                    is ActionIntro.GetIntro -> {
                        getIntroUseCase.invoke()
                            .collect { slidesResult ->
                                when (slidesResult) {
                                    true -> emit(UiStateIntro.HideIntro)
                                    else -> emit(UiStateIntro.ShowIntro)
                                }
                            }
                    }
                    is ActionIntro.SaveIntro -> {
                        val slides = actionIntro.slides
                        saveIntroUseCase.invoke(
                            SaveIntroUseCase.Params(slides)
                        ).watchStatus(
                            loading = { emit(UiStateIntro.ApplyState.Loading) },
                            success = { emit(UiStateIntro.ApplyState.Success) },
                            error = { emit(UiStateIntro.ApplyState.Error) }
                        )
                    }
                }
            }
        }

    init{
        actionIntro.value = ActionIntro.GetIntro
    }

    fun saveIntroState(slides: Boolean) {
        actionIntro.value = ActionIntro.SaveIntro(slides)
    }

    sealed class UiStateIntro {
        object ShowIntro : UiStateIntro()
        object HideIntro : UiStateIntro()

        sealed class ApplyState : UiStateIntro() {
            object Loading : ApplyState()
            object Success : ApplyState()
            object Error : ApplyState()
        }
    }

    sealed class ActionIntro {
        object GetIntro : ActionIntro()
        data class SaveIntro(val slides: Boolean) : ActionIntro()
    }
}