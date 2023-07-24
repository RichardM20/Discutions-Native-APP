package com.discutions.app.views
import android.os.Bundle

import androidx.activity.ComponentActivity
import com.discutions.app.controllers.LoginController
import com.discutions.app.databinding.ActivityLoginBinding
import com.discutions.app.interfaces.LoginStateListerner
import com.discutions.app.utils.Dialogs


class LoginActivity : ComponentActivity(), LoginStateListerner {

    private lateinit var _binding: ActivityLoginBinding
    private val _loginController:LoginController = LoginController();
    private  val _dialog: Dialogs = Dialogs();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)
//        events
       loginButtonClick();
    }
  private fun  loginButtonClick(){
        _binding.loginButton.setOnClickListener {
            _loginController.email = _binding.emailField.text.toString()
            _loginController.password = _binding.passwordField.text.toString()

            val isValidCredentials = _loginController.validateForm();
            if (isValidCredentials) {
                _loginController.loginWithEmailAndPassword(this);
            } else {
                _dialog.showDialog(this, "Information", "Email or password invalid");
            }
        }
    }
    override fun onLoginSuccess() {
        println("Success login");
    }

    override fun onLoginCancel() {
       return _dialog.showDialog(this,"Information","La operacion se cancelo inesperadamente");
    }

    override fun onLoginFailed(errorMessage: String) {
        return _dialog.showDialog(this, "Failed", errorMessage);
    }
}
