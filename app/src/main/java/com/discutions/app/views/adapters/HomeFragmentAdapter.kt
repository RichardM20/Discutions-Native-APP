package com.discutions.app.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.discutions.app.R
import com.discutions.app.models.PostData
import java.text.SimpleDateFormat
import java.util.Locale


class HomeFragmentAdapter(private val posts:List<PostData>)
    : RecyclerView.Adapter<HomeFragmentAdapter.HomeFragmentViewHolder>(){
        class HomeFragmentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
            private val userNameText:TextView=itemView.findViewById(R.id.usernameText);
            private val postData:TextView=itemView.findViewById(R.id.postText);
            private val likes:TextView=itemView.findViewById(R.id.likesContent);
            private val comments:TextView=itemView.findViewById(R.id.commentsContent);
            private val published:TextView=itemView.findViewById(R.id.publishedText);
            fun bind(post:PostData){
                userNameText.text=post.username;
                postData.text=post.post;
                likes.text=post.likes.toString();
                comments.text=post.comments.toString();
                published.text= SimpleDateFormat("dd MMMM yyyy hh:mm a", Locale.US).format(post.publishedAt?.toDate())
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_content, parent, false);
        return HomeFragmentViewHolder(itemView);
    }

    override fun getItemCount(): Int = posts.size;

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        val post=posts[position];
        holder.bind(post);
    }
}