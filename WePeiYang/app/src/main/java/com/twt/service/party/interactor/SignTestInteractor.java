package com.twt.service.party.interactor;

import com.twt.service.party.ui.sign.OnGetTestCallBack;

/**
 * Created by dell on 2016/7/22.
 */
public interface SignTestInteractor {
    void loadTestInfo(String sno, String testType, OnGetTestCallBack callBack);
}
