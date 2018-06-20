/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package debug;

import android.database.sqlite.SQLiteDatabase;

import com.dell.fortune.core.common.BaseApplication;
import com.dell.fortune.pocketexpression.model.dao.DaoMaster;
import com.dell.fortune.pocketexpression.util.common.GreenDaoUtil;

public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "Local-Expression");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        GreenDaoUtil.setSession(master.newSession());
    }
}
