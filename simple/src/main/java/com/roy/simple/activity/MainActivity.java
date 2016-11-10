package com.roy.simple.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.simple.R;
import com.xzy.roy.photoselect.fragment.AddPhotoFragment;
import com.xzy.roy.photoselect.fragment.onPhotoNumChangedListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, onPhotoNumChangedListener {

    private AddPhotoFragment mFragment;
    private EditText et_input_count;
    private Button bt_set_max_count;
    private Button bt_get_all_path;
    private TextView tv_select_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initView() {
        mFragment = (AddPhotoFragment) getSupportFragmentManager().findFragmentById(R.id.select_photo_fragment);
        et_input_count = (EditText) findViewById(R.id.et_input_count);
        bt_set_max_count = (Button) findViewById(R.id.bt_set_max_count);
        bt_get_all_path = (Button) findViewById(R.id.bt_get_all_path);
        tv_select_count = (TextView) findViewById(R.id.tv_select_count);
    }

    private void initEvent() {
        bt_set_max_count.setOnClickListener(this);
        bt_get_all_path.setOnClickListener(this);
        mFragment.setNumCountListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_set_max_count:
                submit();
                break;
            case R.id.bt_get_all_path:
                Toast.makeText(this, mFragment.getAllSelectedPath().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void submit() {
        // validate
        String count = et_input_count.getText().toString().trim();
        if (!TextUtils.isEmpty(count)) {
            mFragment.setMaxNum(Integer.parseInt(count));
            return;
        }

        // TODO validate success, do something


    }

    @Override
    public void getSelectedPhotoNum(int count) {
        tv_select_count.setText(getString(R.string.current_selected) + count);
    }
}
