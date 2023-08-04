package com.discutions.app.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.discutions.app.R
import com.discutions.app.models.CommentsData
import com.discutions.app.utils.TextDateParse


class BottomModalSheetAdapter(private val commentsList: List<CommentsData>)
    :RecyclerView.Adapter<BottomModalSheetAdapter.BottomModalSheetViewHolder>(){

    class BottomModalSheetViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private val username = itemView.findViewById<TextView>(R.id.commentUserNameText);
        private val comment = itemView.findViewById<TextView>(R.id.commentText);
        private val commentAt = itemView.findViewById<TextView>(R.id.commentPublishedText);
        fun bind(comments:CommentsData){
            username.text = comments.username;
            comment.text=comments.comment;
            commentAt.text= TextDateParse.toTextDate(comments.commentAt);
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BottomModalSheetViewHolder {
        //indcamos que pintara
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.comment_content, parent, false);
        return  BottomModalSheetViewHolder(itemView);
    }

    override fun onBindViewHolder(
        holder:  BottomModalSheetViewHolder,
        position: Int
    ) {
      val comment = commentsList[position];
        holder.bind(comment);
    }

    override fun getItemCount(): Int = commentsList.size;

}