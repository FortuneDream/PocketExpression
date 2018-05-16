package com.dell.fortune.pocketexpression.module.joy;


import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.tools.dialog.DialogEditSureCancel;

import butterknife.BindView;
import butterknife.OnClick;

public class BarrageActivity extends BaseActivity<BarragePresenter.IView, BarragePresenter>
        implements BarragePresenter.IView {

    @BindView(R.id.add_text_fab)
    FloatingActionButton addTextFab;
    @BindView(R.id.barrage_tv)
    TextView barrageTv;

    @Override
    protected BarragePresenter createPresenter() {
        return new BarragePresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_barrage;
    }

    @Override
    public void initView() {
        hideStatusBar();//隐藏状态栏
    }

    private void hideStatusBar() {

    }


    @OnClick(R.id.add_text_fab)
    public void onViewClicked() {
        final DialogEditSureCancel editSureCancel = new DialogEditSureCancel(mContext);
        editSureCancel.getIvLogo().setImageResource(R.mipmap.ic_launcher);
        editSureCancel.getTvTitle().setText("设置弹幕");
        editSureCancel.getTvSure().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editSureCancel.getEditText().getText().toString();
                barrageTv.setText(content);
                editSureCancel.dismiss();
            }
        });
        editSureCancel.show();
    }
}