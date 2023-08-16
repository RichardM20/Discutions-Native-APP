package com.discutions.app.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.discutions.app.R
import com.discutions.app.interfaces.OnDataPostsListener
import com.discutions.app.models.NotificationsData
import com.discutions.app.models.PostData
import com.discutions.app.utils.TextDateParse
import java.text.SimpleDateFormat
import java.util.Locale


class NotificationFragmentAdapter(private val notifications:List<NotificationsData>)
    : RecyclerView.Adapter<NotificationFragmentAdapter.NotificationFragmentAdapterViewHolder>(){
    class NotificationFragmentAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private val title:TextView=itemView.findViewById(R.id.notificationTitle);
        private val body:TextView=itemView.findViewById(R.id.notificationBody);
        private val date:TextView=itemView.findViewById(R.id.notificationDate);


        fun bind(notification:NotificationsData){
            title.text=notification.title;
            body.text=notification.body;
            date.text= TextDateParse.toTextDate(notification.emittedAt);
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationFragmentAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_content, parent, false);
        return NotificationFragmentAdapterViewHolder(itemView);
    }

    override fun getItemCount(): Int = notifications.size;

    override fun onBindViewHolder(holder: NotificationFragmentAdapterViewHolder, position: Int) {
        val notification=notifications[position];
        holder.bind(notification);
    }
}