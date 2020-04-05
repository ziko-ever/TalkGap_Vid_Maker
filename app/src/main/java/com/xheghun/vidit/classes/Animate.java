package com.xheghun.vidit.classes;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.talkgap.R;

public class Animate {

    public static void fadeOutAnimation(View view,Context context) {
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.fade_out);
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
   }

    public static void fadeOutAnimationIV(View view,Context context) {
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.fade_out);
        view.startAnimation(animation);
        view.setVisibility(View.INVISIBLE);
    }

   public static void fadeInAnimation (View view,Context context) {
        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.fade_in);
        view.startAnimation(animation);
   }

   public static void slideOutDown(View view, Context context) {
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_down);
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
   }

    public static void slideInDown(View view, Context context) {
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_down);
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
    }

    public static void slideOutUp(View view, Context context) {
        view.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        view.startAnimation(animation);
    }

   public static void slideInUp(View view, Context context) {
        view.setVisibility(View.VISIBLE);
       Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
       view.startAnimation(animation);
   }

    public static void slideOutLeft(View view, Context context) {
        view.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        view.startAnimation(animation);
    }

    public static void slideOutRight(View view, Context context) {
        view.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
        view.startAnimation(animation);
    }

    public static void slideInLeft(View view, Context context) {
        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        view.startAnimation(animation);
    }

    public static void slideInRight(View view, Context context) {
        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        view.startAnimation(animation);
    }

}
