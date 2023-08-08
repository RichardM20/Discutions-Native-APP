package com.discutions.app.views.fragments.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.discutions.app.R
import com.discutions.app.controllers.HomeFragmentController
import com.discutions.app.interfaces.OnDataPostsListener
import com.discutions.app.models.CommentsData
import com.discutions.app.models.PostData
import com.discutions.app.models.UserPreferences
import com.discutions.app.services.FCMService
import com.discutions.app.utils.GenericToast
import com.discutions.app.utils.LoadingDialog
import com.discutions.app.views.adapters.HomeFragmentAdapter
import com.discutions.app.views.fragments.FullScreenBottomSheetFragment


class HomeFragment : Fragment(), OnDataPostsListener {

    private lateinit var _fragmentController:HomeFragmentController;
    private lateinit var  _preferences:UserPreferences;
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingDialog: LoadingDialog;
    private lateinit var adapter: HomeFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false);
        _preferences= UserPreferences(requireContext());
        recyclerView = view.findViewById<RecyclerView>(R.id.recycleView);
        recyclerView.layoutManager=LinearLayoutManager(context);
        adapter = HomeFragmentAdapter(_preferences.userId!!,emptyList(),this);
        recyclerView.adapter=adapter;

        loadingDialog=LoadingDialog(requireContext(), Dialog(requireContext()));
        _fragmentController= HomeFragmentController(UserPreferences(requireContext()));
        _fragmentController.getUserData();
        _fragmentController.getPosts(this);

        return view;
    }


    override fun onLoading(loading: Boolean) {
        if(loading){
            loadingDialog.showLoadingDialog("Loading");
        }else{
            loadingDialog.hideLoadingDialog();
        }
    }

    override fun onDataFetchSuccess(postList: List<PostData>) {
        adapter= HomeFragmentAdapter(_preferences.userId!!,postList,this);
        recyclerView.adapter=adapter;
    }


    override fun onDataFetchFailure(errorMessage: String) {
       GenericToast.showToast(requireContext(),errorMessage,false);
    }

    override fun onTapLike(fcmToken:String,postId: String) {
        _fragmentController.like(fcmToken,postId,this);
    }
    override fun onTapCommentIcon(fcmToken:String, comments: List<CommentsData>, postId:String) {
        val bundle = Bundle();
        val modal = FullScreenBottomSheetFragment();
        //
        bundle.putSerializable("comments_data_list",ArrayList(comments));//pasamos los datos por medio del bundle
        bundle.putString("postId",postId)
        bundle.putString("tokenFCM",fcmToken)
        modal.arguments=bundle;
        modal.show(childFragmentManager, modal.tag)
    }

    override fun onLikeSuccess( ) {
             println("success liked");
    }

}