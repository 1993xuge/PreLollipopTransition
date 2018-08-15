package com.kogitune.activity_transition;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created at 2018/8/15 下午7:09.
 *
 * @author yixu.wang
 */

public class Utils {

    public static View cloneView(View originView) {
        int[] locations = new int[2];
        originView.getLocationOnScreen(locations);
        Log.d("xuge", "   cloneView: " + locations[0] + "   " + locations[1]);

        View cloneView = new View(originView.getContext());
        if (originView instanceof ImageView) {
            cloneView = new ImageView(originView.getContext());
            ((ImageView) cloneView).setImageDrawable(((ImageView) originView).getDrawable());
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(originView.getWidth(), originView.getHeight());
        layoutParams.leftMargin = locations[0];
        layoutParams.topMargin = locations[1] - getStatusBarHeight(originView.getContext());
        cloneView.setLayoutParams(layoutParams);

        return cloneView;
    }

    public static ViewGroup getRootView(Context context) {
        return getRootView(scanForActivity(context));
    }

    public static ViewGroup getRootView(Activity activity) {
        if (activity == null) {
            return null;
        }
        View rootView = activity.findViewById(android.R.id.content);
        if (rootView == null || !(rootView instanceof ViewGroup)) {
            return null;
        }
        return (ViewGroup) rootView;
    }

    public static Activity scanForActivity(Context context) {
        if (context == null) return null;

        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    public static int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }
}
