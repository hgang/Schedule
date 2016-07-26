package com.heg.schedule.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.heg.schedule.R;

import butterknife.ButterKnife;

/**
 * Created by hegang on 16/7/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = View.inflate(this, getLayoutResId(), null);
        setContentView(mRootView);
        ButterKnife.bind(this);
        initToolbar();
        init();
    }

    protected void initToolbar() {
        View toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar((Toolbar) toolbar);
        }
    }

    public abstract int getLayoutResId();

    public abstract void init();
}
