package com.twtstudio.tjwhm.lostfound.detail;

import com.twtstudio.tjwhm.lostfound.base.BaseContract;
import com.twtstudio.tjwhm.lostfound.release.ReleaseContract;
import com.twtstudio.tjwhm.lostfound.waterfall.WaterfallBean;

/**
 * Created by tjwhm on 2017/7/7.
 **/

public interface DetailContract {
    public interface DetailView extends BaseContract.BaseView{
        void setDetailData(DetailBean detailData);
    }
    public interface DetailPresenter {
        void loadDetailData(int id);
        void setDetailData(DetailBean detailData);
        void loadDetailDataForEdit(int id,ReleaseContract.ReleaseView releaseView);
        void setEditData(DetailBean detailBean);
    }
}
