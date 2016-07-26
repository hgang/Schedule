package com.heg.schedule.widget;

import java.util.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.heg.schedule.R;

public abstract class DatePickerDialog extends Dialog implements View.OnClickListener, OnDateChangedListener {

    private Button ok, cancle;
    private DatePicker datePicker;
    private Calendar ca;

    protected DatePickerDialog(Context context) {
        super(context, com.heg.widget.R.style.AlertStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_datepicker_dialog);
        ok = (Button) findViewById(R.id.ok);
        cancle = (Button) findViewById(R.id.cancle);
        datePicker = (DatePicker) findViewById(R.id.date_picker);
        ok.setOnClickListener(this);
        cancle.setOnClickListener(this);
        long date = setDate();
        ca = Calendar.getInstance();
        if (date > 0) {
            ca.setTimeInMillis(date);
        }
        datePicker.init(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH), this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                onOk();
                break;
            case R.id.cancle:
                onCancle();
                break;

            default:
                break;
        }

    }


    protected void onCancle() {
        dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    protected abstract void onOk();

    protected long setDate() {
        return 0;
    }

    public Calendar getDate() {
        ca.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        return ca;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }
}
