package com.udacity.sanketbhat.bakingapp.test;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {

    private AtomicBoolean mIdle = new AtomicBoolean(false);
    @Nullable
    private volatile ResourceCallback mCallback;

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    public void setIdle(boolean isIdleNow) {
        mIdle.set(isIdleNow);
        if (isIdleNow && mCallback != null) {
            Objects.requireNonNull(mCallback).onTransitionToIdle();
        }
    }
}
