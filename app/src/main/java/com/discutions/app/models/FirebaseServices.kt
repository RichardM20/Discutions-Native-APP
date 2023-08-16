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
    fun post(postData: PostData, callback: (String) -> Unit){
        val post = hashMapOf(
            "uidUser" to postData.uidUser,
            "username" to postData.username,
            "uidPost" to postData.uidPost,
            "fcmToken" to postData.fcmToken,
            "comments" to postData.comments,
            "likes" to postData.likes,
            "post" to postData.post,
            "publishedAt" to postData.publishedAt
        )
        val collectionReference = _firebaseFirestore.collection("posts")
        collectionReference.add(post)
            .addOnSuccessListener { documentReference ->
                val postId = documentReference.id
                collectionReference.document("${postId}")
                    .update("uidPost", postId)
                    .addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            callback("success")
                        } else {
                           callback(updateTask.exception?.message.toString());
                        }
                    }
            }
            .addOnFailureListener { exception ->
               callback(exception.message.toString());
            }
    }
    fun setNotification(notification:NotificationsData, callback: (String) -> Unit){
        println("set notification()");
        val notify = hashMapOf(
            "uidNotification" to notification.uidNotification,
            "title" to notification.title,
            "body" to notification.body,
            "emittedAt" to notification.emittedAt,
        )
        _firebaseFirestore
            .collection("notifications")
            .add(notify)
            .addOnCompleteListener {
                result->
                if(result.isSuccessful){

                    callback("success")
                }else{
                    callback(result.exception?.message.toString());
                }
            }
    }

    fun getAllNotifications(onSuccess:(List<NotificationsData>)->Unit, failed:(String)->Unit){
        _firebaseFirestore
            .collection("notifications")
            .addSnapshotListener { snapshots, error ->
                if(error!=null){
                    failed("Failed");
                    return@addSnapshotListener;
                }
                val notifications = snapshots?.documents?.mapNotNull {
                        doc->
                        doc.toObject(NotificationsData::class.java);
                }?: emptyList();
                onSuccess(notifications);
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
    fun addUser(uidUser:String, callback: (String) -> Unit){
        _firebaseFirestore
            .collection("users")
            .document("$uidUser")
            .get()
            .addOnCompleteListener {
                result->
                if(result.isSuccessful){
                    val doc = result.result;
                    if(doc.exists()){
                        callback("exist");
                    }else{
                        callback("not-found");
                    }
                }

            }

    }
    fun getDataUser(uidUser: String, onSuccess: (ProfileData) -> Unit, onFailed:(String)->Unit){
        _firebaseFirestore
            .collection("users")
            .document("$uidUser")
            .get()
            .addOnCompleteListener {
                result->
                if(result.isSuccessful){
                    val userData = result.result.toObject(ProfileData::class.java);
                    if(userData!=null){
                        onSuccess(userData);
                    }
                }else{
                    println("exception in getDataUser: ${result.exception}");
                    onFailed(result.exception?.message.toString());
                }
            }
    }
    fun completeProfile(profileData: ProfileData,failed:(String)->Unit,success: (ProfileData?) -> Unit){
        val user = hashMapOf(
            "uidUser" to profileData.uidUser,
            "username" to profileData.username,
            "gender" to profileData.gender,
            "email" to profileData.email,
            "description" to profileData.description,
            "tokenFCM" to profileData.fcmToken
        )
        _firebaseFirestore
            .collection("users")
            .document("${profileData.uidUser}")
            .set(user)
            .addOnCompleteListener {
                result->
                if(result.isSuccessful){
                    success(profileData);
                }else{
                    failed(result.exception?.message.toString());
                }
            }
    }

    fun publishComment(commentsData: CommentsData, uidPost:String, callback:(String)->Unit) {
        println("call: publishComment()");
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
        println("call: likeToPost()\nuser: $uidPost");
        val toLike = hashMapOf(
            "uidUser" to likeData.uidUser,
            "username" to likeData.username,
        )
        val postRef = _firebaseFirestore
            .collection("posts")
            .document("$uidPost")
        postRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val post = task.result.toObject(PostData::class.java)
                post?.let {
                    val likes = it.likes
                    //verificamos si hay coincidencia
                    val exist = likes.any { like -> like.uidUser == likeData.uidUser }
                    if (exist) {
                        //si exixte lo removemos de la lista
                        postRef.update("likes", FieldValue.arrayRemove(toLike))
                    } else {
                        //de lo contrario se agrega
                        postRef.update("likes", FieldValue.arrayUnion(toLike))
                    }.addOnCompleteListener { updateTask ->
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