package com.discutions.app.controllers
import com.discutions.app.interfaces.OnCommentListener
import com.discutions.app.interfaces.OnDataPostsListener
import com.discutions.app.interfaces.OnDataUserState
import com.discutions.app.models.CommentsData
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.LikeData
import com.discutions.app.models.NotificationsData
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

    private fun sendNotification(uidUserPost:String,token:String, title:String, message:String){
       try {
           FCMService.sendNotification(token, title,message) { result ->
               if (result == "success") {
                   firebaseServices.setNotification(
                       NotificationsData(
                           uidUserPost,
                            title,
                            message,
                            Timestamp.now()
                        )
                   ){
                           task->
                       if(task=="success"){
                           println("success set notification in collection");
                       }else{
                           println("notification ex: $task");
                       }
                   }
               }
           }
       }catch(e:Exception){
           println("Exception sendNotification: $e");
       }
    }
    fun like(token:String,uidUserPost: String,postId: String, listener: OnDataPostsListener){
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
                    println("succes to like");
                   sendNotification(uidUserPost, token, "New interaction","${userPreferences.username} liked your post")
                }else{
                    listener.onDataFetchFailure(result);
                }
            }

        }catch (e:Exception){
            println("e: $e");
            listener.onDataFetchFailure("Couldn't like the post");
        }
    }
    fun comment(uidUserPost:String,token:String,comment:String, postId:String, listener: OnCommentListener){
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
                      sendNotification(uidUserPost,token, "New interaction","${userPreferences.username} has commented on your discussion")
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