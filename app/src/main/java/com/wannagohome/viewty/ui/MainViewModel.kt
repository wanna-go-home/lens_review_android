package com.wannagohome.viewty.ui

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    val tabPosition = MutableLiveData(0)
    val mainTitle = MutableLiveData("")


    fun setTabPosition(position: Int) {
        tabPosition.value = position

        val title = when (position) {
            1 -> MainTitleEnum.LIPS
            2 -> MainTitleEnum.VIDEO
            3 -> MainTitleEnum.MYPAGE
            else -> return
        }
        setTitleInfo(title)
    }

    fun setTitleInfo(info: MainTitleEnum) {
        mainTitle.value = info.titleName
    }

}