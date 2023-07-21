package com.discutions.app.views

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.discutions.app.R
import com.discutions.app.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : ComponentActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(layoutInflater);
        setContentView(R.layout.activity_login);
        binding.emailField.setOnFocusChangeListener { _, focused ->
            if(!focused){
                println("Focused");
            }
            println("None");
        }
        binding.loginButton.setOnClickListener {
            validateEmail();
            Toast.makeText(this, "E Form", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateEmail():String?{
        val emailTextValue = binding.emailField.text.toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(emailTextValue).matches()){
            println("Please enter a valid email");
        }
        return null;
    }
}