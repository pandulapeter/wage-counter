package com.pandulapeter.wagecounter.presentation.main

import android.animation.Animator
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class WageCounterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSplashScreen()
        setContent { Main(supportFragmentManager) }
    }

    private fun handleSplashScreen() = installSplashScreen().setOnExitAnimationListener { splashScreen ->
        splashScreen.view.animate()
            .scaleX(2f)
            .scaleY(2f)
            .alpha(0f)
            .setInterpolator(AccelerateInterpolator())
            .setListener(
                object : Animator.AnimatorListener {
                    override fun onAnimationStart(animator: Animator?) = Unit
                    override fun onAnimationEnd(animator: Animator?) = splashScreen.remove()
                    override fun onAnimationCancel(animator: Animator?) = Unit
                    override fun onAnimationRepeat(animator: Animator?) = Unit
                }
            )
            .start()
    }
}