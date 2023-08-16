package com.discutions.app.views.fragments.notifications

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.discutions.app.R
import com.discutions.app.controllers.NotificationsController
import com.discutions.app.interfaces.NotificationsDataState
import com.discutions.app.models.NotificationsData
import com.discutions.app.models.UserPreferences
import com.discutions.app.utils.GenericToast
import com.discutions.app.utils.LoadingDialog
import com.discutions.app.views.adapters.HomeFragmentAdapter
import com.discutions.app.views.adapters.NotificationFragmentAdapter

class NotificationsFragment : Fragment(), NotificationsDataState {
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingDialog: LoadingDialog;
    private lateinit var adapter: NotificationFragmentAdapter
    private lateinit var  controller: NotificationsController;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = view.findViewById<RecyclerView>(R.id.notificationsRecycleView);
        recyclerView.layoutManager= LinearLayoutManager(context);
        adapter = NotificationFragmentAdapter(emptyList());
        recyclerView.adapter=adapter;
        controller= NotificationsController(UserPreferences(requireContext()));
        loadingDialog=LoadingDialog(requireContext(), Dialog(requireContext()));

        controller.getNotifications(this);
        return view;
    }

    override fun onLoading(isLoading: Boolean) {
        if(isLoading){
            loadingDialog.showLoadingDialog("Loading");
        }else{
            loadingDialog.hideLoadingDialog();
        }
    }

    override fun onSuccess(notifications: List<NotificationsData>) {
        adapter= NotificationFragmentAdapter(notifications);
        recyclerView.adapter=adapter;
    }

    override fun onFailed(errorMessage: String) {
        GenericToast.showToast(requireContext(),errorMessage,false);
    }


}