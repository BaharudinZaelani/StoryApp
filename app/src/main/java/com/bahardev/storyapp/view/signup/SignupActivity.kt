package com.bahardev.storyapp.view.signup

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bahardev.storyapp.data.api.ApiConfig
import com.bahardev.storyapp.databinding.SignupBinding
import com.bahardev.storyapp.view.signin.LoginActivity
import kotlinx.coroutines.launch

class SignupActivity: AppCompatActivity() {
    private lateinit var binding: SignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
        action()
    }

    // Animation
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    // Function Helper
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun moveToLoginPage() {
        startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
    }
    // action
    private fun action() {
        binding.signupButton.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            if ( binding.edRegisterEmail.text.toString() == "" ) {
                Toast.makeText(this@SignupActivity, "Field Email tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                binding.loading.visibility = View.GONE
            }else if ( binding.edRegisterName.text.toString() == "" ) {
                Toast.makeText(this@SignupActivity, "Field Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                binding.loading.visibility = View.GONE
            }else if ( binding.edRegisterPassword.text.toString() == "" ) {
                binding.loading.visibility = View.GONE
            }
            else {
                binding.loading.visibility = View.VISIBLE
                // success
                val result = ArrayList<String>()
                val email = binding.edRegisterEmail.text.toString()
                val password = binding.edRegisterPassword.text.toString()
                val name = binding.edRegisterName.text.toString()
                result.add(email)
                result.add(password)
                result.add(name)

                lifecycleScope.launch {
                    try {
                        binding.loading.visibility = View.GONE
                        val apiService = ApiConfig().getApiService()
                        apiService.register(name, email, password)
                        showToast("Berhasil Registrasi!")
                        moveToLoginPage()

                    }catch (e: Exception) {
                        binding.loading.visibility = View.GONE
                        showToast("Error saat registrasi : ${e.message.toString()}")
                    }
                }
            }
        }
    }
}