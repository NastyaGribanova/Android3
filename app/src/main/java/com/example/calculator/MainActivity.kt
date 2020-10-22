package com.example.calculator

import android.animation.AnimatorSet
import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.dynamicanimation.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY
import androidx.dynamicanimation.animation.SpringForce.STIFFNESS_VERY_LOW
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var index = 0
    var animatorSet = AnimatorSet()
    lateinit var scene1: Scene
    lateinit var scene2: Scene
    lateinit var viewGroup: ViewGroup
    lateinit var animation: Animation
    lateinit var animation3: Animation
    lateinit var force: SpringForce
    lateinit var springAnimation: SpringAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_show_hide.setOnClickListener{
            onClick()
        }
        btn_hello.setOnClickListener {
            onClick2()
        }
        viewGroup = findViewById(R.id.scene_root)
        val layoutTransition = viewGroup.layoutTransition
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        scene1 = Scene.getSceneForLayout(viewGroup, R.layout.scene1, this)
        scene2 = Scene.getSceneForLayout(viewGroup, R.layout.scene2, this)
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        animation3 = AnimationUtils.loadAnimation(this, R.anim.rotate3)
        force = SpringForce()

    }

    fun onClick(){
        if (index == 0){
            animatorSet.playSequentially(
                ObjectAnimator.ofFloat(btn_show_hide, "translationY", 300f).setDuration(1000).apply {
                    start()
                },
                ObjectAnimator.ofFloat(btn_hello, "translationY", -200f).setDuration(1000).apply {
                    start()
                },
                ObjectAnimator.ofFloat(btn_hello, View.ROTATION, 0f, 360f).setDuration(1000).apply {
                    start()
                },
                ObjectAnimator.ofFloat(btn_hello2, "translationY", 600f).setDuration(1000).apply {
                    start()
                },
                ObjectAnimator.ofFloat(btn_hello2, View.ROTATION, 360f, 0f).setDuration(1000).apply {
                    start()
                }
            )
            iv_popug.visibility = View.VISIBLE
            iv_popug.startAnimation(animation)
            iv_popug3.visibility = View.VISIBLE
            iv_popug3.startAnimation(animation3)
            TransitionManager.go(scene1, AutoTransition())
            index = 1
        } else {
            animatorSet.playSequentially(
                ObjectAnimator.ofFloat(btn_show_hide, "translationY", 0f).setDuration(1000).apply {
                    start()
                },
                ObjectAnimator.ofFloat(btn_hello, "translationY", 0f).setDuration(1000).apply {
                    start()
                },
                ObjectAnimator.ofFloat(btn_hello, View.ROTATION, 360f, 0f).setDuration(1000).apply {
                    start()
                },
                ObjectAnimator.ofFloat(btn_hello2, "translationY", 0f).setDuration(1000).apply {
                    start()
                },
                ObjectAnimator.ofFloat(btn_hello2, View.ROTATION, 0f, 360f).setDuration(1000).apply {
                    start()
                })
            TransitionManager.go(scene2, AutoTransition())
            index = 0
        }
    }

    fun onClick2(){
        springAnimation = SpringAnimation(btn_hello, DynamicAnimation.ROTATION)
        force.setFinalPosition(btn_hello.x)
        force.setDampingRatio(DAMPING_RATIO_HIGH_BOUNCY).setStiffness(STIFFNESS_VERY_LOW)
        springAnimation.setSpring(force).setStartVelocity(2000f)
        springAnimation.start()
    }

}