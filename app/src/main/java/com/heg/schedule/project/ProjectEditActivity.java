package com.heg.schedule.project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.heg.database.beans.ProjectBean;
import com.heg.database.helper.DBOpenHelper;
import com.heg.schedule.base.BaseActivity;
import com.heg.schedule.db.DBHelper;
import com.heg.schedule.R;
import com.heg.schedule.widget.DatePickerDialog;
import com.heg.baselib.utils.DateUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProjectEditActivity extends BaseActivity {

    public static final String PARAM_PROJECT = "param_project";
    @BindView(R.id.loading_progress)
    ProgressBar mLoadingProgress;
    @BindView(R.id.spinner_platform_name)
    Spinner mSpinnerPlatformName;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_type)
    EditText mEtType;
    @BindView(R.id.et_principle)
    EditText mEtPrinciple;
    @BindView(R.id.et_interest_rate)
    EditText mEtInterestRate;
    @BindView(R.id.et_interest)
    EditText mEtInterest;
    @BindView(R.id.et_start_time)
    EditText mEtStartTime;
    @BindView(R.id.et_end_time)
    EditText mEtEndTime;
    @BindView(R.id.btn_finish)
    Button mBtnFinish;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.layout_edit_form)
    LinearLayout mLayoutEditForm;
    @BindView(R.id.edit_form)
    ScrollView mEditForm;

    private ProjectBean mProject;

    View datePickerView;
    DatePickerDialog mDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_project_edit;
    }

    @Override
    public void init() {
        mProject = getIntent().getParcelableExtra(PARAM_PROJECT);

        mDialog = new DatePickerDialog(this) {
            @Override
            protected void onOk() {
                dismiss();
                Calendar ca = mDialog.getDate();
                if (datePickerView == mEtStartTime) {
                    mEtStartTime.setText(DateUtil.formatDate(ca.getTimeInMillis()));
                } else if (datePickerView == mEtEndTime) {
                    mEtEndTime.setText(DateUtil.formatDate(ca.getTimeInMillis()));
                }
            }
        };
        initValues();
    }

    private void initValues() {
        List<String> platforms = DBHelper.getPlatForms();
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, platforms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        mSpinnerPlatformName.setAdapter(adapter);
        if (mProject != null) {
            mEtName.setText(mProject.getName());
            mEtType.setText(mProject.getType());
            mEtInterest.setText(mProject.getInterest() + "");
            mEtInterestRate.setText(mProject.getInterest_rate() + "");
            mEtPrinciple.setText(mProject.getPrincipal() + "");
            mEtStartTime.setText(DateUtil.dateToString(mProject.getStart_time(), DateUtil.DF_YYYY_MM_DD));
            mEtEndTime.setText(DateUtil.dateToString(mProject.getEnd_time(), DateUtil.DF_YYYY_MM_DD));
            mSpinnerPlatformName.setSelection(platforms.indexOf(mProject.getPlatform_name()));
        }
    }


    private void onFinishClick() {
        // Reset errors.
        mEtName.setError(null);
        if (TextUtils.isEmpty(mEtName.getText())) {
            mEtName.setError("项目名不能为空");
            return;
        }
        if (mProject == null) {
            mProject = new ProjectBean();
        }
        mProject.setName(mEtName.getText().toString());
        mProject.setType(mEtType.getText().toString());
        mProject.setPlatform_name(mSpinnerPlatformName.getSelectedItem().toString());
        if(!TextUtils.isEmpty(mEtInterest.getText())){
            mProject.setInterest(Double.parseDouble(mEtInterest.getText().toString()));
        }
        if(!TextUtils.isEmpty(mEtInterestRate.getText())){
            mProject.setInterest_rate(Double.parseDouble(mEtInterestRate.getText().toString()));
        }
        mProject.setPrincipal(Double.parseDouble(mEtPrinciple.getText().toString()));
        mProject.setStart_time(DateUtil.formatDateTime(mEtStartTime.getText().toString(), DateUtil.DF_YYYY_MM_DD));
        mProject.setEnd_time(DateUtil.formatDateTime(mEtEndTime.getText().toString(), DateUtil.DF_YYYY_MM_DD));
        DBOpenHelper.getInstances(this).getSession().getProjectBeanDao().insertOrReplace(mProject);
        finish();
    }
    private void onDeleteClick() {
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

    @OnClick({R.id.et_start_time, R.id.et_end_time, R.id.btn_finish,R.id.btn_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_start_time:
                datePickerView = view;
                mDialog.show();
                break;
            case R.id.et_end_time:
                datePickerView = view;
                mDialog.show();
                break;
            case R.id.btn_finish:
                onFinishClick();
                break;
            case R.id.btn_delete:
                onDeleteClick();
                break;
        }
    }

}

