package com.discutions.app.views.register

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.discutions.app.controllers.RegisterController

import com.discutions.app.databinding.ActivityRegisterBinding
import com.discutions.app.interfaces.RegisterStateListener
import com.discutions.app.utils.Dialogs
import com.discutions.app.utils.GenericToast
import com.discutions.app.utils.LoadingDialog
import com.discutions.app.views.dashboard.DashboardActivity
import com.discutions.app.views.login.LoginActivity


class RegisterActivity : ComponentActivity(), RegisterStateListener {
    private lateinit var _binding: ActivityRegisterBinding
    private  val _dialog: Dialogs = Dialogs();
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
           _registerController.validateForm {
                   formState->
               println("form state:$formState");
               when (formState) {
                   "invalid-form" -> {
                       _dialog.showDialog(this,"Information","Email or password invalid");
                   }
                   "different-password" -> {
                       _dialog.showDialog(this,"Information","Passwords do not match");
                   }
                   else -> {
                       _registerController.registerWithEmailAndPassword(this);
                   }
               }
           }
       }
    }

    override fun onRegisterSuccess() {
        GenericToast.showToast(this,"Successful registration",false);
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
        return _dialog.showDialog(this, "Failed", err);
    }
}
