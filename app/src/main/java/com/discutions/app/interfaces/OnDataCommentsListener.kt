package com.discutions.app.interfaces

import com.discutions.app.models.CommentsData

interface OnDataCommentsListener {
    fun onLoadingComments(loading: Boolean);
    fun onDataFetchCommentsSuccess(commentsList:List<CommentsData>);
    fun onDataFetchCommentsFailure(errorMessage:String);
}