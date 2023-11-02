package com.bahardev.storyapp.view.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bahardev.storyapp.databinding.WelcomeBinding
import com.bahardev.storyapp.view.ViewModelFactory
import com.bahardev.storyapp.view.main.MainActivity
import com.bahardev.storyapp.view.main.MainViewModel
import com.bahardev.storyapp.view.signin.LoginActivity
import com.bahardev.storyapp.view.signup.SignupActivity

class WelcomeActivity: AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: WelcomeBinding

    // Life Cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                finish()
            }
        }
        playAnimation()
        action()
    }

    // Function
    private fun action() {
        binding.signupButton.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, SignupActivity::class.java))
        }
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
        }
    }

    // Animation
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val together = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(600),
                ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(600)
            )
        }
        AnimatorSet().apply {
            playSequentially(
                ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(600),
                ObjectAnimator.ofFloat(binding.descTextView, View.ALPHA, 1f).setDuration(600),
                together
            )
            start()
        }

    }
}