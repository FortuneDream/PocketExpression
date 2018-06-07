/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.model.callback;

import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;

import java.util.List;

public interface OnCheckCollectionListener {
    void onCheckResult(boolean isSynSuccess, List<ExpressionItem> notSaveItems);
}
