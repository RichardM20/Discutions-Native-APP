package com.discutions.app.views
import android.app.Dialog
import android.content.Intent
import android.os.Bundle

import androidx.activity.ComponentActivity
import com.discutions.app.R
import com.discutions.app.controllers.LoginController
import com.discutions.app.databinding.ActivityLoginBinding
import com.discutions.app.interfaces.LoginStateListerner
import com.discutions.app.utils.Dialogs
import com.discutions.app.utils.LoadingDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.runBlocking


class LoginActivity : ComponentActivity(), LoginStateListerner {

    //creamos las instancias
    private lateinit var _binding: ActivityLoginBinding
    private val _loginController:LoginController = LoginController();
    private lateinit var googleSignInClient: GoogleSignInClient;
    private  val _dialog: Dialogs = Dialogs();
    private lateinit var  _loadingDialog:LoadingDialog;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inicializamos
        _binding = ActivityLoginBinding.inflate(layoutInflater) //le indicamos que  xml dibujara en pocas palabras
        setContentView(_binding.root)//indicamos muestre  xml indicado previamente
        _loadingDialog= LoadingDialog(this, Dialog(this));

        //servicio de google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //eventos de botones
        loginButtonEvent();
        googleIconEvent();
        navigateToRegister();
    }
    private  fun loginButtonEvent(){
        //indicamos cuando ocurrira el evento, en este caso al dar click
        _binding.loginButton.setOnClickListener {
            _loginController.email = _binding.emailField.text.toString()
            _loginController.password = _binding.passwordField.text.toString()
            val isValidCredentials = _loginController.validateForm();
            if (isValidCredentials) {
                _loginController.loginWithEmailAndPassword(this)
            } else {
                _dialog.showDialog(this, "Information", "Email or password invalid");
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
    override fun onLoginSuccess() {
        //emitimos el evento que sucedio
        //navegamos a la pantalla indicada
//        startActivity(Intent(applicationContext, RegisterActivity::class.java))
        //animacion de paso
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onLoading(showLoading: Boolean) {
        //escuchamos los cambios
      if(showLoading){
          _loadingDialog.showLoadingDialog("Loading");
      }else{
          _loadingDialog.hideLoadingDialog();
      }
    }

    override fun onLoginFailed(errorMessage: String) {
        //en caso de que salga algo mal mostramos el mensaje del error
        return _dialog.showDialog(this, "Failed", errorMessage);
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
                    _dialog.showDialog(this,"Error","Could not get GoogleToken")
                }
            } catch (e: ApiException) {
                //capturamos el error para mostrarlo
                _dialog.showDialog(this,"Error","Code: ${e.statusCode}\nStatus: ${e.status}\nErr: ${e.message}");
            }
        }
    }
    companion object {
        private const val RC_SIGN_IN = 123
    }
}
