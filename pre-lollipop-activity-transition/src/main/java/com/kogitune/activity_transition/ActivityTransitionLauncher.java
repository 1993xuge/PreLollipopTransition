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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.kogitune.activity_transition.core.TransitionBundleFactory;

public class ActivityTransitionLauncher {
    private static final String TAG = "TransitionLauncher";

    private final Activity activity;
    private View fromView;
    private Bitmap bitmap;


    private ActivityTransitionLauncher(Activity activity) {
        this.activity = activity;
    }

    public static ActivityTransitionLauncher with(Activity activity) {
        return new ActivityTransitionLauncher(activity);
    }

    public ActivityTransitionLauncher from(View fromView) {
        this.fromView = fromView;
        return this;
    }

    public ActivityTransitionLauncher image(final Bitmap bitmap) {
        this.bitmap = bitmap;
        return this;
    }

    public Bundle createBundle() {
        return TransitionBundleFactory.createTransitionBundle(activity, fromView, bitmap);
    }

    public void launch(Intent intent) {
        intent.putExtras(createBundle());
        activity.startActivityForResult(intent, 1);
        activity.overridePendingTransition(0, 0);
    }

    public void exit(Intent intent) {
        intent.putExtras(createBundle());
        activity.setResult(Activity.RESULT_OK, intent);
        activity.overridePendingTransition(0, 0);
        activity.finish();
    }
}
