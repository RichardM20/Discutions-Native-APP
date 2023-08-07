package com.discutions.app.controllers
import com.discutions.app.interfaces.OnCommentListener
import com.discutions.app.interfaces.OnDataPostsListener
import com.discutions.app.models.CommentsData
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.LikeData
import com.discutions.app.models.UserPreferences
import com.google.firebase.Timestamp
import java.util.Timer
import kotlin.concurrent.timerTask

class HomeFragmentController(private val userPreferences: UserPreferences){
   private val firebaseServices = FirebaseServices();
    fun getPosts(listener: OnDataPostsListener){
        listener.onLoading(true);
        Timer().schedule(timerTask {
            firebaseServices.getAllPosts(
                onSuccess = {posts->
                        listener.onLoading(false);
                        listener.onDataFetchSuccess(posts);
                },
                failed ={message->
                    listener.onLoading(false);
                    listener.onDataFetchFailure(message);
                }
            );
            listener.onLoading(false);
        }, 1000)

    }
    fun like(postId: String, listener: OnDataPostsListener){

        try {
            firebaseServices.likeToPost(
                LikeData(
                username = "test",
                uidUser = userPreferences.userId!!,
            ),
                uidPost = postId
            ){
                result->
                if(result=="success"){
                    listener.onLikeSuccess();
                }else{
                    listener.onDataFetchFailure(result);
                }
            }

        }catch (e:Exception){
            println("e: $e");
            listener.onDataFetchFailure("Couldn't like the post");
        }
    }
    fun comment(comment:String, postId:String, listener: OnCommentListener){
      if(comment.isEmpty()){
          println("Empty");
      }else{
          try {
              firebaseServices.publishComment(
                  CommentsData(
                      comment = comment,
                      commentAt = Timestamp.now(),
                      uidUser = userPreferences.userId!!,
                      username = "testCode"
                  ),
                  postId
              ){
                      result->
                  if(result=="success"){
                      listener.onSuccess();
                  }else{
                      listener.onFailed(result);
                  }
              }
          }catch (e:Exception){
              println("exception in comment fun: $e");
              listener.onFailed("Exception in process");
          }
      }
    }

}