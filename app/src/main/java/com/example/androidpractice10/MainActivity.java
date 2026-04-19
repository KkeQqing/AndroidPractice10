package com.example.androidpractice10;

import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btStart,btStop;
    private ImageView ivShow;
    private AnimationDrawable animationDrawable;

    private Button btPlay,btReversePlay;
    private Animation clockwise,anticlockwise;
    private Button bt_alpha, bt_rotation, bt_scale,bt_start, bt_translation, bt_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btStart = findViewById(R.id.bt_start);
        btStop = findViewById(R.id.bt_stop);
        ivShow = findViewById(R.id.iv_show);
        animationDrawable =(AnimationDrawable)ivShow.getBackground();

        MyListener myListener = new MyListener();

        btStart.setOnClickListener(myListener);
        btStop.setOnClickListener(myListener);

        // 补间动画设置监听
        btPlay=(Button) findViewById(R.id.bt_play);
        btReversePlay= (Button) findViewById(R.id.bt_reverse_play);
        btPlay.setOnClickListener(myListener);
        btReversePlay.setOnClickListener(myListener);
        clockwise = AnimationUtils.loadAnimation(this, R.anim.tween_anim);
        clockwise.setFillAfter(true);
        anticlockwise = AnimationUtils.loadAnimation(this, R.anim.tween_anim_reverse);
        anticlockwise.setFillAfter(true);
        btPlay.setOnClickListener(myListener);
        btReversePlay.setOnClickListener(myListener);

        //属性动画
        bt_alpha = (Button) findViewById(R.id.bt_alpha);
        bt_rotation = (Button)
                findViewById(R.id.bt_rotation);
        bt_scale = (Button) findViewById(R.id.bt_scale);
        bt_translation = (Button)
                findViewById(R.id.bt_translation);
        bt_start = (Button) findViewById(R.id.bt_group);
        bt_value = (Button) findViewById(R.id.bt_value);
        bt_alpha.setOnClickListener(myListener);
        bt_rotation.setOnClickListener(myListener);
        bt_scale.setOnClickListener(myListener);
        bt_translation.setOnClickListener(myListener);
        bt_start.setOnClickListener(myListener);
        bt_value.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(animationDrawable == null)
                return;
            if(v.getId() == R.id.bt_start){
                animationDrawable.start();
            }else if(v.getId() == R.id.bt_stop){
                animationDrawable.stop();
            }else if(v.getId() == R.id.bt_play){
                ivShow.startAnimation(clockwise);
            } else if (v.getId() == R.id.bt_reverse_play) {
                ivShow.startAnimation(anticlockwise);
            }else if (v.getId() == R.id.bt_alpha) {  //属性动画
                ObjectAnimator anim1 = ObjectAnimator.ofFloat(ivShow, "alpha", 1f, 0.1f, 1f, 0.5f, 1f);
                anim1.start();
            }else if (v.getId() == R.id.bt_rotation) {
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(ivShow, "rotation", 0f, 360f);
                anim2.start();
            }else if (v.getId() == R.id.bt_scale) {
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(ivShow, "scaleY", 0f, 360f);
                anim2.start();
            }else if (v.getId() == R.id.bt_translation) {
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(ivShow, "translationY", 0f, 360f);
                anim2.start();
            }else if (v.getId() == R.id.bt_group) {
                animatorSet();
            }else if (v.getId() == R.id.bt_value) {
                animatorValue();
            }
        }
    }

    private void animatorSet() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(ivShow, "alpha", 1f, 0.1f, 1f, 0.5f, 1f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(ivShow, "rotation", 0f, 360f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(ivShow, "scaleY", 1f, 3f, 1f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(ivShow, "translationY", 0f, 300f);

        AnimatorSet animSet = new AnimatorSet(); //AnimatorSet用于管理和组合多个动画，使得它们可以按照特定的顺序或同时执行
        AnimatorSet.Builder builder = animSet.play(anim1); //builder将以anim1为基础来构建其他动画与它的关
        builder.with(anim2).after(anim3).before(anim4); // anim3先执行，然后再同步执行anim1、anim2，最后执行anim4
        animSet.setDuration(6000);
        animSet.start();
    }

    // 插值器和估值器
    private void animatorValue() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(ivShow, "alpha", 1f, 0.1f, 1f, 0.5f, 1f);
        anim1.setInterpolator(new AccelerateInterpolator()); //AccelerateInterpolator使得动画在开始时速度较慢，随着时间推移逐渐加速。它通过一个简单的
        anim1.setEvaluator(new FloatEvaluator());
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(ivShow, "rotation", 0f, 360f);
        anim2.setInterpolator(new DecelerateInterpolator());
        anim2.setEvaluator(new FloatEvaluator());
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(ivShow, "scaleY", 1f, 3f, 1f);
        anim3.setInterpolator(new LinearInterpolator());
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(ivShow, "translationY", 0f, 300f);
        anim4.setInterpolator(new BounceInterpolator()); //弹跳效果的插值器
        anim4.setEvaluator(new FloatEvaluator());

        AnimatorSet animSet = new AnimatorSet();
        AnimatorSet.Builder builder = animSet.play(anim1);
        builder.with(anim2).after(anim3).before(anim4);
        animSet.setDuration(6000);
        animSet.start();
    }
}