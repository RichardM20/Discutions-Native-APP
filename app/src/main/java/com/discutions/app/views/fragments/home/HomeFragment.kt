package com.discutions.app.views.fragments.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.discutions.app.R
import com.discutions.app.controllers.HomeFragmentController
import com.discutions.app.interfaces.OnDataPostsListener
import com.discutions.app.models.CommentsData
import com.discutions.app.models.PostData
import com.discutions.app.utils.LoadingDialog
import com.discutions.app.views.adapters.HomeFragmentAdapter
import com.discutions.app.views.fragments.FullScreenBottomSheetFragment


class HomeFragment : Fragment(), OnDataPostsListener {

    private lateinit var _fragmentController:HomeFragmentController;
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingDialog: LoadingDialog;
    private lateinit var adapter: HomeFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById<RecyclerView>(R.id.recycleView);
        recyclerView.layoutManager=LinearLayoutManager(context);
        adapter = HomeFragmentAdapter(emptyList(),this);
        recyclerView.adapter=adapter;

        loadingDialog=LoadingDialog(requireContext(), Dialog(requireContext()));
        _fragmentController= HomeFragmentController();
        _fragmentController.getPosts(this);

        return view;
    }


    override fun onLoading(loading: Boolean) {

        println("is loading:$loading");
        if(loading){
            loadingDialog.showLoadingDialog("Loading dat");
        }else{
            loadingDialog.hideLoadingDialog();
        }
    }

    override fun onDataFetchSuccess(postList: List<PostData>) {
        adapter= HomeFragmentAdapter( postList,this);
        recyclerView.adapter=adapter;
    }


    override fun onDataFetchFailure(errorMessage: String) {
        println("Fail: ${errorMessage}");
    }

    override fun onTapCommentIcon(comments: List<CommentsData>) {
        val bundle = Bundle();
        val modal = FullScreenBottomSheetFragment();
        //
        bundle.putSerializable("comments_data_list",ArrayList(comments));//pasamos los datos por medio del bundle
        modal.arguments=bundle;
        modal.show(childFragmentManager, modal.tag)
    }

}