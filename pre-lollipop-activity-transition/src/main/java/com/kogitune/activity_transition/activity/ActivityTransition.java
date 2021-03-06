/*
 * Copyright (C) 2015 takahirom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.kogitune.activity_transition.activity;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.kogitune.activity_transition.core.MoveData;
import com.kogitune.activity_transition.core.TransitionAnimation;

public class ActivityTransition {
    private int duration = 600;
    private View toView;
    private TimeInterpolator interpolator;
    private Animator.AnimatorListener listener;
    private final Intent fromIntent;

    private View rootView;

    private ActivityTransition(Intent intent) {
        this.fromIntent = intent;
    }

    public static ActivityTransition with(Intent intent) {
        return new ActivityTransition(intent);
    }

    public ActivityTransition to(View toView) {
        this.toView = toView;
        return this;
    }

    public ActivityTransition root(View rootView) {
        this.rootView = rootView;
        return this;
    }

    public ActivityTransition duration(int duration) {
        this.duration = duration;
        return this;
    }

    public ActivityTransition interpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public ActivityTransition enterListener(Animator.AnimatorListener listener) {
        this.listener = listener;
        return this;
    }

    public ExitActivityTransition start(Bundle savedInstanceState) {
        if (interpolator == null) {
            interpolator = new AccelerateDecelerateInterpolator();
        }

        final Bundle bundle = fromIntent.getExtras();
        final MoveData moveData = TransitionAnimation.startAnimation(toView, bundle, savedInstanceState, duration, interpolator, listener);
        TransitionAnimation.startAlphaAnimation(rootView, 100, duration - 100, 0, 1);
        return new ExitActivityTransition(moveData);
    }

}
