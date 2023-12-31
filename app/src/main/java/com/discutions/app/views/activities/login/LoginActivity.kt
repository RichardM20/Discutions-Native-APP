package com.discutions.app.views.activities.login
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.BoringLayout
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import com.discutions.app.R
import com.discutions.app.controllers.LoginController
import com.discutions.app.databinding.ActivityLoginBinding
import com.discutions.app.interfaces.LoginStateListerner
import com.discutions.app.models.UserPreferences

import com.discutions.app.utils.GenericDialog
import com.discutions.app.utils.GenericToast
import com.discutions.app.utils.LoadingDialog
import com.discutions.app.views.activities.complete.CompleteProfileActivity
import com.discutions.app.views.activities.dashboard.DashboardActivity
import com.discutions.app.views.activities.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


class LoginActivity : ComponentActivity(), LoginStateListerner {

    //creamos las instancias
    private lateinit var _binding: ActivityLoginBinding
    private lateinit var _loginController:LoginController;
    private lateinit var googleSignInClient: GoogleSignInClient;
    private var showPassword:Boolean=false;
    private lateinit var  _loadingDialog:LoadingDialog;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inicializamos
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root);

        _loginController=LoginController(UserPreferences(this));
        _loadingDialog= LoadingDialog(this, Dialog(this));

        //servicios
        getRemember();
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //eventos de botones
        checkBoxEvent();
        loginButtonEvent();
        googleIconEvent();
        navigateToRegister();
    }
   private fun getRemember(){
        if(_loginController.preferences.remember==true){
            _binding.passwordFieldValue.setText(_loginController.preferences.password);
            _binding.emailField.setText(_loginController.preferences.email);
            _binding.rememberCheckbox.isChecked=true;
        }
    }


    private fun checkBoxEvent(){
        _binding.rememberCheckbox.setOnCheckedChangeListener { _, isChecked ->
            _loginController.preferences.remember = isChecked
        }
    }
    private  fun loginButtonEvent(){
        _binding.loginButton.setOnClickListener {
            if(_loginController.preferences.remember==true){
                _loginController.preferences.email = _binding.emailField.text.toString()
                _loginController.preferences.password = _binding.passwordFieldValue.text.toString()
            }
            _loginController.email = _binding.emailField.text.toString()
            _loginController.password = _binding.passwordFieldValue.text.toString()
            if (_loginController.validateForm()) {
                _loginController.loginWithEmailAndPassword(this)
            } else {
                GenericDialog.showDialog(this, "Information", "Email or password invalid");
            }
        }
    }
     private  fun navigateToRegister(){
         _binding.registerButton.setOnClickListener {
             startActivity(Intent(applicationContext, RegisterActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
         }
     }
    private fun googleIconEvent(){
        _binding.googleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }
    override fun onLoginSuccess(haveAccount:Boolean) {
//        GenericToast.showToast(this,"Welcome $username",true);
        if(haveAccount){
            startActivity(Intent(applicationContext, DashboardActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish();
        }else{
            startActivity(Intent(applicationContext, CompleteProfileActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish();
        }


    }

    override fun onLoading(showLoading: Boolean) {
        //escuchamos los cambios
      if(showLoading){
          _loadingDialog.showLoadingDialog("Loading");
      }else{
          _loadingDialog.hideLoadingDialog();
      }
    }

    override fun onLoginFailed(err: String) {
        //en caso de que salga algo mal mostramos el mensaje del error
        return GenericDialog.showDialog(this, "Failed", err);
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //resultado del intento de inicio de sesion con google para obtener el tokenID
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account?.idToken
                if (idToken != null) {
                    //pasamos el token obtenido para iniciar asi sesion con firebase (Google)
                    _loginController.loginWithGoogle(idToken,this);
                } else {
                    //indcamos el error
                    //tener en cuenta que no muestro el error en el oyente ya que esta separado obviamente
                    GenericDialog.showDialog(this,"Error","Could not get GoogleToken")
                }
            } catch (e: ApiException) {
                //capturamos el error para mostrarlo
                GenericDialog.showDialog(this,"Error","Operation has been cancelled");
            }
        }
    }
    companion object {
        private const val RC_SIGN_IN = 123
    }
}
