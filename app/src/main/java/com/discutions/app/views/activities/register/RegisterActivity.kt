package com.discutions.app.views.activities.register

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.discutions.app.controllers.RegisterController

import com.discutions.app.databinding.ActivityRegisterBinding
import com.discutions.app.interfaces.RegisterStateListener
import com.discutions.app.utils.GenericDialog

import com.discutions.app.utils.GenericToast
import com.discutions.app.utils.LoadingDialog
import com.discutions.app.views.activities.dashboard.DashboardActivity
import com.discutions.app.views.activities.login.LoginActivity


class RegisterActivity : ComponentActivity(), RegisterStateListener {
    private lateinit var _binding: ActivityRegisterBinding

    private lateinit var  _loadingDialog: LoadingDialog;
    private val  _registerController:RegisterController=RegisterController();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        _loadingDialog= LoadingDialog(this, Dialog(this));

        //------------------------------------------------------------------//
        registerEvent();
        backToLogin();
    }

    private fun backToLogin(){
        _binding.sectionBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
    private fun registerEvent(){
       _binding.registerButton.setOnClickListener {
           _registerController.email = _binding.emailField.text.toString()
           _registerController.password = _binding.passwordField.text.toString()
           _registerController.confirmPassword = _binding.confirmPasswordField.text.toString()
           //
           _registerController.validateForm {message->
                if(message!="valid-form") {
                    GenericDialog.showDialog(this,"Information","$message");
                }else{
                    _registerController.registerWithEmailAndPassword(this);
                }
           }
       }
    }


    override fun onRegisterSuccess() {
        GenericToast.showToast(this,"Successful registration",true);
        startActivity(Intent(applicationContext, DashboardActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish();
    }

    override fun onLoading(showLoading: Boolean) {
        if(showLoading){
            _loadingDialog.showLoadingDialog("Loading");
        }else{
            _loadingDialog.hideLoadingDialog();
        }
    }

    override fun onRegisterFailed(err: String) {
        return GenericDialog.showDialog(this, "Failed", err);
    }
}
