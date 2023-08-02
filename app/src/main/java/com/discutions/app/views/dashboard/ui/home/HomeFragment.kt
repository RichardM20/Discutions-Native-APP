package com.discutions.app.views.dashboard.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.discutions.app.R
import com.discutions.app.controllers.HomeFragmentController
import com.discutions.app.interfaces.OnDataFetchListener
import com.discutions.app.utils.LoadingDialog


class HomeFragment : Fragment(), OnDataFetchListener {

    private val _fragmentController = HomeFragmentController();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentController.getPosts(this);
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onLoading(loading: Boolean) {

        println("is loading:$loading");
        if(loading){

        }else{

        }
    }

    override fun onDataFetchSuccess(dataList: List<String>) {

    }

    override fun onDataFetchFailure(errorMessage: String) {

    }

}