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

package com.kogitune.activity_transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.kogitune.activity_transition.core.MoveData;
import com.kogitune.activity_transition.core.TransitionAnimation;

public class ExitActivityTransition {
    private final MoveData moveData;
    private TimeInterpolator interpolator;
    private Animator.AnimatorListener listener;


    public ExitActivityTransition(MoveData moveData) {
        this.moveData = moveData;
    }

    public ExitActivityTransition interpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public ExitActivityTransition exitListener(Animator.AnimatorListener listener) {
        this.listener = listener;
        return this;
    }

    public void exit(final Activity activity) {
        exit(activity, null);
    }

    public void exit(final Activity activity, View rootView) {
        if (interpolator == null) {
            interpolator = new DecelerateInterpolator();
        }
        if (rootView != null) {
            TransitionAnimation.startAlphaAnimation(rootView, 100, 0, 1, 0);
        }
        TransitionAnimation.startExitAnimation(moveData, interpolator, new Runnable() {
            @Override
            public void run() {
                activity.finish();
                activity.overridePendingTransition(0, 0);
            }
        }, listener);
    }

}
