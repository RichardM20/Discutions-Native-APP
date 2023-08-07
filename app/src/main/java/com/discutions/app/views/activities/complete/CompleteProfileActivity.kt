package com.discutions.app.views.activities.complete

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.discutions.app.controllers.CompleteProfileController
import com.discutions.app.databinding.ActivityCompleteProfileBinding
import com.discutions.app.interfaces.CompleteProfileState
import com.discutions.app.models.UserPreferences
import com.discutions.app.utils.GenericDialog
import com.discutions.app.utils.LoadingDialog
import com.discutions.app.views.activities.dashboard.DashboardActivity
import java.util.zip.Inflater

class CompleteProfileActivity : ComponentActivity(), CompleteProfileState {

    private lateinit var  _loadingDialog: LoadingDialog;
    private  lateinit var _controller:CompleteProfileController;
    private lateinit var _binging:ActivityCompleteProfileBinding;
    override fun onCreate(savedInstanceState: Bundle?) {

        _binging= ActivityCompleteProfileBinding.inflate(layoutInflater);

        _loadingDialog= LoadingDialog(this, Dialog(this));
        _controller= CompleteProfileController(UserPreferences(this));
        super.onCreate(savedInstanceState)

    }

    private fun saveEvent(){
        //
    }
    override fun onLoading(showLoading: Boolean) {
        if(showLoading){
            _loadingDialog.showLoadingDialog("Saving data");
        }else{
            _loadingDialog.hideLoadingDialog();
        }
    }
    override fun onCompleted() {
        startActivity(Intent(applicationContext, DashboardActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish();
    }

    override fun onFailed(errorMessage: String) {
       GenericDialog.showDialog(this,"Failed",errorMessage);
    }
}
