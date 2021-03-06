package com.cz.android.sample.processor.clazz;

import android.app.Activity;
import android.content.Intent;

import com.cz.android.sample.api.ActionProcessor;
import com.cz.android.sample.api.item.RegisterItem;

/**
 * @author Created by cz
 * @date 2020-01-27 15:48
 * @email bingo110@126.com
 */
@ActionProcessor
public class ActivityClassActionProcessor<C extends Activity> extends ClassActionProcessor<C> {
    @Override
    public boolean isInstance(Class item) {
        return super.isInstance(item)&& Activity.class.isAssignableFrom(item);
    }

    @Override
    public void run(C context, RegisterItem registerItem, Class clazz){
        Intent intent = new Intent(context, clazz);
        intent.putExtra("title",registerItem.title);
        intent.putExtra("desc",registerItem.desc);
        context.startActivity(intent);
    }
}
