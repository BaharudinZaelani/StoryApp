package com.bahardev.storyapp.view.signin

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bahardev.storyapp.R
import com.bahardev.storyapp.data.api.ApiConfig
import com.bahardev.storyapp.data.pref.UserModel
import com.bahardev.storyapp.databinding.LoginBinding
import com.bahardev.storyapp.view.ViewModelFactory
import com.bahardev.storyapp.view.main.MainActivity
import com.bahardev.storyapp.view.main.MainViewModel
import kotlinx.coroutines.launch


class LoginActivity: AppCompatActivity() {
    // parameter
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: LoginBinding

    // life cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnim()
        action()

    }

    // helper
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // action
    private fun action() {
        binding.loginButton.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            if ( binding.edLoginEmail.text.toString() == "" ) {
                showToast(getString(R.string.error_email))
                binding.loading.visibility = View.GONE
            }else {
                val mEmail = binding.edLoginEmail.text.toString()
                val mPassword = binding.edLoginPassword.text.toString()
                lifecycleScope.launch {
                    try {
                        val apiService = ApiConfig().getApiService()
                        val ress = apiService.login(email = mEmail, password = mPassword)
                        showToast("Selamat Datang ${ress.data.name}")
                        binding.loading.visibility = View.GONE

                        // save session
                        val user = UserModel(mEmail, "Bearer " + ress.data.token, true)
                        viewModel.setSession(user)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    }catch (e: Exception) {
                        showToast("Error saat login ${e.message}")
                        binding.loading.visibility = View.GONE
                    }
                    finish()
                }
            }
        }
    }

    // animation
    private fun playAnim() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}
