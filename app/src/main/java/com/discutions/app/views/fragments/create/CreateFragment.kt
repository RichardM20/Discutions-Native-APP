package com.discutions.app.views.fragments.create

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.discutions.app.R
import com.discutions.app.controllers.CreateController
import com.discutions.app.interfaces.PostState
import com.discutions.app.models.UserPreferences
import com.discutions.app.utils.GenericDialog
import com.discutions.app.utils.GenericToast
import com.discutions.app.utils.LoadingDialog
import com.google.android.material.textfield.TextInputEditText

class CreateFragment : Fragment(), PostState {
    private lateinit var createController: CreateController;
    private lateinit var loadingDialog: LoadingDialog;
    private lateinit var  post:TextInputEditText;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)
        loadingDialog= LoadingDialog(requireContext(), Dialog(requireContext()));
        createController= CreateController(UserPreferences(requireContext()));
        postEvent(view);
        return view;
    }

    private fun postEvent(view: View){
         post = view.findViewById<TextInputEditText>(R.id.fieldPost);

        view.findViewById<Button>(R.id.buttonPost).setOnClickListener {
           createController.post=post.text.toString();
            if(createController.post.length<3){
                GenericDialog.showDialog(requireContext(),"Information","Your post must contain at least 3 characters.");
            }else{
                createController.post(this);
            }
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if(isLoading){
            loadingDialog.showLoadingDialog("Posting");
        }else{
            loadingDialog.hideLoadingDialog();
        }
    }

    override fun onSuccess() {
       GenericToast.showToast(requireContext(),"Posted discussion", true);
        post.setText("");
        requireActivity().supportFragmentManager.popBackStackImmediate(
            R.id.nav_home,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onFailed(errorMessage: String) {
       GenericDialog.showDialog(requireContext(),"Failed",errorMessage);

    }
}