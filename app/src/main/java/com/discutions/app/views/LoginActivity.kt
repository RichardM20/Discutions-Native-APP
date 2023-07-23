package com.discutions.app.views
import android.content.DialogInterface
import android.os.Bundle

import android.util.Patterns

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import com.discutions.app.controllers.LoginController
import com.discutions.app.databinding.ActivityLoginBinding
import com.discutions.app.utils.Dialogs


class LoginActivity : ComponentActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var _loginController: LoginController
    private  lateinit var _dialog: Dialogs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        _loginController = LoginController();
        _dialog = Dialogs();
        //
        setContentView(binding.root)

//        events
        binding.loginButton.setOnClickListener {
            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()
            //pass credential
            val isValidCredentials = _loginController.onLoginButtonClick(email, password)
            if (isValidCredentials) {
                _dialog.showDialog(this, "Information", "Success form")
            } else {
                _dialog.showDialog(this, "Information", "Email or password invalid");
            }
        }
    }
}
