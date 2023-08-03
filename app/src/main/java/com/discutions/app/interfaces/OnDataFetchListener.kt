package com.discutions.app.interfaces

import com.discutions.app.models.PostData

interface OnDataFetchListener {
    fun onLoading(loading: Boolean);
    fun onDataFetchSuccess(postList:List<PostData>);
    fun onDataFetchFailure(errorMessage: String);
}