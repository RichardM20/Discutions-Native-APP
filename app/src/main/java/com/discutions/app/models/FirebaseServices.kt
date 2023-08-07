package com.discutions.app.models


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

//servicios de firebase
//login, getData, sendData
class FirebaseServices {
    private var _auth:FirebaseAuth = Firebase.auth
    private val _firebaseFirestore =FirebaseFirestore.getInstance();
    private val _response = ExceptionMessage();
    //methods
      fun loginWithEmailAndPassword(email: String, password: String,callback:(AuthResponse?)->Unit) {
        _auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                    result->
                if(result.isSuccessful){
                   callback(
                       AuthResponse(
                       user = UserData(
                           uid = result.result?.user?.uid,
                           email=result.result.user?.email,
                       ),
                       errorMessage = null,
                   ),
                   );
                } else {
                    val message = _response.isException(result.exception!!);
                    callback(
                        AuthResponse(
                        user = null,
                        errorMessage =message,
                        ),
                    );
                }
            }
      }
    fun registerWithEmailAndPassword(email: String, password: String,callback:(AuthResponse?)->Unit) {
        _auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                    result->
                if(result.isSuccessful){
                    callback(AuthResponse(
                        user = UserData(
                                uid = result.result?.user?.uid,
                                email=result.result.user?.email
                        ),
                        errorMessage = null,
                        ),
                    );
                } else {
                    val message = _response.isException(result.exception!!);
                    callback(AuthResponse(
                            user = null,
                            errorMessage =message,
                        ),
                    );
                }
            }
    }


    fun signInWithGoogle(idToken: String, callback: (Result<FirebaseUser?>) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        _auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
               if(task.isSuccessful){
                   callback(Result.success(task.result.user))
               }  else{
                   callback(Result.failure(task.exception!!));
               }
            }
    }

    fun getAllPosts(onSuccess:(List<PostData>)->Unit, failed:(String)->Unit){
        _firebaseFirestore.collection("posts")
            .addSnapshotListener { snapshots, error ->
                if(error!=null){
                    failed("Failed");
                    return@addSnapshotListener;
                }
                val posts = snapshots?.documents?.mapNotNull {
                    doc->
                    doc.toObject(PostData::class.java);
                }?: emptyList();
                onSuccess(posts);
            }
    }
    fun publishComment(commentsData: CommentsData, uidPost:String, callback:(String)->Unit) {

        val comment = hashMapOf(
            "uidUser" to commentsData.uidUser,
            "username" to commentsData.username,
            "comment" to commentsData.comment,
            "commentAt" to commentsData.commentAt
        );
        _firebaseFirestore.collection("posts")
            .document("$uidPost")
            .update("comments", FieldValue.arrayUnion(comment))
            .addOnCompleteListener {
                    resultTask->
                if(resultTask.isSuccessful){
                    callback("success");
                }else{
                    callback(resultTask.exception?.message.toString());
                }
            }

    }
    fun likeToPost(likeData: LikeData, uidPost: String, callback: (String) -> Unit) {
        println("intent like to post: $uidPost");
        val toLike = hashMapOf(
            "uidUser" to likeData.uidUser,
            "username" to likeData.username,
        )
        val postRef = _firebaseFirestore
            .collection("posts")
            .document("$uidPost")
        postRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("success to get data post");
                val post = task.result.toObject(PostData::class.java)
                println("post :${post?.likes}");
                post?.let {
                    val likes = it.likes
                    val exist = likes.any { like -> like.uidUser == likeData.uidUser }
                    if (exist) {
                        println("this user already liked post");
                        postRef.update("likes", FieldValue.arrayRemove(toLike))
                    } else {
                        println("not like");
                        postRef.update("likes", FieldValue.arrayUnion(toLike))
                    }.addOnCompleteListener { updateTask ->
                        println("task complete");
                        if(updateTask.isSuccessful){
                            callback("success");
                        }else{
                            callback(updateTask.exception?.message.toString());
                        }

                    }
                } ?: callback("Post not found")
            } else {
                println("problem to get data");
                callback(task.exception?.message.toString())
            }
        }
    }
    fun logOut(){
        _auth.signOut();
    }
}