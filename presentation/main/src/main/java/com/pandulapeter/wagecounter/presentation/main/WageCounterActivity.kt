package com.pandulapeter.wagecounter.presentation.main

import android.animation.Animator
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pandulapeter.wagecounter.domain.GetConfigurationUseCase
import com.pandulapeter.wagecounter.presentation.main.ui.MainApp
import org.koin.android.ext.android.inject

class WageCounterActivity : AppCompatActivity() {

    private val getConfiguration by inject<GetConfigurationUseCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSplashScreen()
        setContent { MainApp(getConfiguration()) }
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