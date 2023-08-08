package com.discutions.app.views.activities.complete

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.discutions.app.R
import com.discutions.app.controllers.CompleteProfileController
import com.discutions.app.databinding.ActivityCompleteProfileBinding
import com.discutions.app.interfaces.CompleteProfileState
import com.discutions.app.models.UserPreferences
import com.discutions.app.utils.GenericDialog
import com.discutions.app.utils.LoadingDialog
import com.discutions.app.views.activities.dashboard.DashboardActivity
import java.util.Timer
import java.util.zip.Inflater
import kotlin.concurrent.timerTask

class CompleteProfileActivity : ComponentActivity(), CompleteProfileState {

    private lateinit var  _loadingDialog: LoadingDialog;
    private  lateinit var _controller:CompleteProfileController;
    private lateinit var _binding:ActivityCompleteProfileBinding;
    override fun onCreate(savedInstanceState: Bundle?) {

        _binding= ActivityCompleteProfileBinding.inflate(layoutInflater);
        setContentView(_binding.root);
        _loadingDialog= LoadingDialog(this, Dialog(this));
        _controller= CompleteProfileController(UserPreferences(this));
        saveEvent();
        checkEvent();
        super.onCreate(savedInstanceState)


    }

    private fun saveEvent(){
        _binding.buttonFinish.setOnClickListener {
            _controller.username = _binding.usernameField.text.toString();
            _controller.description = _binding.presentationField.text.toString();
            if(_controller.validateForm()){
                _controller.completeProfile(this);
            }else{
                GenericDialog.showDialog(this,"Information","Please complete the fields correctly");
            }

        }
    }
    private fun checkEvent(){
        _binding.radioGenderGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.maleOption) {
                _controller.gender = "Male";
            } else if (checkedId == R.id.femaleOption) {
                _controller.gender = "Female";
            }
        }
    }
    override fun onLoading(showLoading: Boolean) {
        if(showLoading){
            _loadingDialog.showLoadingDialog("Saving data");
        }else{
            _loadingDialog.hideLoadingDialog();
        }
    }
    override fun onCompleted() {
      Timer().schedule(timerTask {
          startActivity(Intent(applicationContext, DashboardActivity::class.java))
          overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
          finish();
      },500)
    }

    override fun onFailed(errorMessage: String) {
       GenericDialog.showDialog(this,"Failed",errorMessage);
    }
}
