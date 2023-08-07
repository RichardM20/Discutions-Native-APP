package com.discutions.app.interfaces

import com.discutions.app.models.CommentsData
import com.discutions.app.models.PostData

interface OnDataPostsListener {
    fun onLoading(loading: Boolean);
    fun onLikeSuccess();
    fun onDataFetchSuccess(postList:List<PostData>);
    fun onDataFetchFailure(errorMessage: String);
    fun onTapCommentIcon(fcmToken:String,comments: List<CommentsData>, postId:String);
    fun onTapLike(fcmToken:String,postId:String);

}