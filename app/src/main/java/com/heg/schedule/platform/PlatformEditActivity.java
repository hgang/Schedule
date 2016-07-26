package com.heg.schedule.platform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.heg.database.beans.PlatformBean;
import com.heg.database.helper.DBOpenHelper;
import com.heg.schedule.base.BaseActivity;
import com.heg.schedule.R;

import butterknife.BindView;
import butterknife.OnClick;

public class PlatformEditActivity extends BaseActivity {

    public static final String PARAM_PLATFORM = "param_platform";
    @BindView(R.id.loading_progress)
    ProgressBar mLoadingProgress;
    @BindView(R.id.project_name)
    AutoCompleteTextView mProjectName;
    @BindView(R.id.btn_finish)
    Button mBtnFinish;
    @BindView(R.id.email_login_form)
    LinearLayout mEmailLoginForm;
    @BindView(R.id.edit_form)
    ScrollView mEditForm;

    private PlatformBean mPlatform;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_platform_edit;
    }

    @Override
    public void init() {
        mPlatform = getIntent().getParcelableExtra(PARAM_PLATFORM);
        if (mPlatform != null) {
            mProjectName.setText(mPlatform.getName());
        }
    }


    private void onFinishClick() {
        // Reset errors.
        mProjectName.setError(null);
        if (TextUtils.isEmpty(mProjectName.getText())) {
            mProjectName.setError("平台名不能为空");
            return;
        }
        // Store values at the time of the login attempt.
        String name = mProjectName.getText().toString();
        if (mPlatform == null) {
            mPlatform = new PlatformBean();
            mPlatform.setName(name);
        }
        DBOpenHelper.getInstances(this).getSession().getPlatformBeanDao().insertOrReplace(mPlatform);
        finish();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mEditForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mEditForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mEditForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mLoadingProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoadingProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoadingProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoadingProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mEditForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_finish)
    public void onClick() {
        onFinishClick();
    }
}

