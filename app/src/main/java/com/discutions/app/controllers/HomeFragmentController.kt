package com.discutions.app.controllers
import com.discutions.app.interfaces.OnCommentListener
import com.discutions.app.interfaces.OnDataPostsListener
import com.discutions.app.interfaces.OnDataUserState
import com.discutions.app.models.CommentsData
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.LikeData
import com.discutions.app.models.UserPreferences
import com.discutions.app.services.FCMService
import com.google.firebase.Timestamp
import java.util.Timer
import kotlin.concurrent.timerTask

class HomeFragmentController(private val userPreferences: UserPreferences){
   private val firebaseServices = FirebaseServices();
    fun getUserData(){
        userPreferences.isLogged=true;
        firebaseServices.getDataUser(userPreferences.userId.toString(),
            onSuccess = {
                userdata->
                userPreferences.username=userdata.username;
            },
            onFailed = {
                errorMessage->
                 println("err on get userdata: $errorMessage");
            }
        );
    }
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
    fun like(token:String,postId: String, listener: OnDataPostsListener){
        try {
            firebaseServices.likeToPost(
                LikeData(
                username = userPreferences.username!!,
                uidUser = userPreferences.userId!!,
            ),
                uidPost = postId
            ){
                result->
                if(result=="success"){
                   try {
                       FCMService.sendNotification(
                           token,
                           "New interaction",
                           "${userPreferences.username} has indicated that he liked your post."
                       );
                   }catch (e:Exception){
                       println(e);
                   }
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
    fun comment(token:String,comment:String, postId:String, listener: OnCommentListener){
      if(comment.isEmpty()){
          println("Empty");
      }else{
          try {
              firebaseServices.publishComment(
                  CommentsData(
                      comment = comment,
                      commentAt = Timestamp.now(),
                      uidUser = userPreferences.userId!!,
                      username = userPreferences.username!!,
                  ),
                  postId
              ){
                      result->
                  if(result=="success"){
                      try {
                          FCMService.sendNotification(
                              token,
                              "New interaction",
                              "${userPreferences.username} has indicated that he liked your post."
                          );
                      }catch (e:Exception){
                          println(e);
                      }
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