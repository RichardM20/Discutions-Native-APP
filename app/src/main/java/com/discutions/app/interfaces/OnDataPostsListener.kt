package com.discutions.app.interfaces

import com.discutions.app.models.CommentsData
import com.discutions.app.models.PostData

interface OnDataPostsListener {
    fun onLoading(loading: Boolean);
    fun onLikeSuccess();
    fun onDataFetchSuccess(postList:List<PostData>);
    fun onDataFetchFailure(errorMessage: String);
    fun onTapCommentIcon(post: PostData);
    fun onTapLike(post:PostData);

}