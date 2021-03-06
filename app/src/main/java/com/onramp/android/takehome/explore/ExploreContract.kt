package com.onramp.android.takehome.explore

import android.content.Context
import com.onramp.android.takehome.BasePresenter
import com.onramp.android.takehome.BaseView
import com.onramp.android.takehome.imageData.Image
import com.onramp.android.takehome.imageData.source.local.FavoriteImage

interface ExploreContract {

    // functions that make API calls
    interface Presenter : BasePresenter {

        fun onViewCreated()
        suspend fun getRandomImageData(activityContext: Context)
        suspend fun saveFavoriteImage(context: Context, imageData: FavoriteImage)
    }

    // functions that change the view/UI
    interface View : BaseView<Presenter> {
        suspend fun setImagesOnMainThread(activityContext: Context, imageList: ArrayList<Image>)
        suspend fun setBlank()
    }
}