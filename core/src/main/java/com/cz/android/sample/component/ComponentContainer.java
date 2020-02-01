package com.cz.android.sample.component;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.cz.android.sample.window.impl.ComponentWindowDelegate;

/**
 * @author Created by cz
 * @date 2020-01-27 17:36
 * @email bingo110@126.com
 */
public interface ComponentContainer {
    /**
     * We check if this object has Annotation:SampleBorder.
     * If this sample object doesn't have this annotation. It won't call the other functions
     * @return
     */
    boolean isComponentAvailable(@NonNull Object object);

    /**
     * This function is an critical function. It's move like a chain. Each component will call this function
     * And return a new view for the next.
     *
     * Tips:
     * 1. If the sample is not a activity or fragment. Take a look on {@link ComponentWindowDelegate}
     *
     * @param context activity context
     * @param object the instance of the sample. It depends on which one that you registered
     * @param parentView The parent view of your original view.
     * @param view your fragment/activity content view
     * @return
     */
    View getComponentView(@NonNull FragmentActivity context,@NonNull Object object,@NonNull ViewGroup parentView,@NonNull View view);

    /**
     * After this component created a new view. This function will call automatically.
     * The view is the one you created. You only have this chance to initialize your code here or it will be changed by the other component.
     * @param context
     * @param object
     * @param view
     */
    void onCreatedView(@NonNull FragmentActivity context,@NonNull Object object,@NonNull View view);

    /**
     * The priority in the component queue. If you want your component run before others
     * @return
     */
    int getComponentPriority();
}
