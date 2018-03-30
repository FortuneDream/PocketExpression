package com.dell.fortune.pocketexpression.module.auth.register;

import android.app.Activity;
import android.text.TextUtils;


import com.dell.fortune.pocketexpression.callback.ToastSaveListener;
import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;
import com.dell.fortune.pocketexpression.config.FlagConstant;
import com.dell.fortune.pocketexpression.config.StrConstant;
import com.dell.fortune.pocketexpression.model.bean.MyUser;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class RegisterPresenter extends BasePresenter<RegisterPresenter.IView> {

    public RegisterPresenter(IView view) {
        super(view);
    }

    public Boolean checkAccount(String email) {
        Pattern pattern = Pattern.compile(FlagConstant.CHECK_EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void register(String account, String password, String confirmPassword, String nickName) {
        Boolean isConfirm = checkAccount(account);//邮箱验证账号
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(nickName) || TextUtils.isEmpty(confirmPassword)) {
            ToastUtil.showToast(StrConstant.COMPLETE_INFO);
        } else if (!isConfirm) {
            ToastUtil.showToast("邮箱格式错误~");
        } else if (!confirmPassword.equals(password)) {
            ToastUtil.showToast("两次输入的密码要相同哦~");
        } else {
            mView.showLoading(true);
            final MyUser user = new MyUser();
            user.setUsername(account);
            user.setPassword(password);
            user.setEmail(account);//账号作为邮箱,打开邮箱验证
            user.setNickName(nickName);
            user.signUp(new ToastSaveListener<MyUser>() {
                @Override
                public void onSuccess(MyUser user) {
                    mView.showLoading(false);
                    ToastUtil.showToast("注册成功，\\(^o^)/~");
                    ((Activity) mContext).setResult(FlagConstant.SUCCESS);
                    mView.finish();
                }
            });
        }
    }

    public interface IView extends IBaseView {
        void finish();

        void showLoading(boolean isShow);
    }
}
