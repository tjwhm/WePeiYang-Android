package com.twtstudio.tjwhm.lostfound.release;

import android.widget.Toast;

import com.twt.wepeiyang.commons.network.RetrofitProvider;
import com.twt.wepeiyang.commons.network.RxErrorHandler;
import com.twtstudio.tjwhm.lostfound.base.BaseBean;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tjwhm on 2017/7/6.
 **/

public class ReleasePresenterImpl implements ReleaseContract.ReleasePresenter {

    ReleaseContract.ReleaseView releaseView;
    ReleaseApi releaseApi;

    public ReleasePresenterImpl(ReleaseContract.ReleaseView releaseView) {
        this.releaseView = releaseView;
    }


    @Override
    public void uploadReleaseData(final Map<String, Object> map, String lostOrFound) {
        releaseApi = RetrofitProvider.getRetrofit().create(ReleaseApi.class);
        releaseApi.updateRelease(map, lostOrFound)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::successCallBack, new RxErrorHandler());
    }

    @Override
    public void uploadReleaseDataWithPic(Map<String, Object> map, String lostOrFound, File file) {
        releaseApi = RetrofitProvider.getRetrofit().create(ReleaseApi.class);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("pic[]", file.getName(), imageBody);//imgfile 后台接收图片流的参数名
        builder.addFormDataPart("title",String.valueOf(map.get("title")));
        builder.addFormDataPart("time",String.valueOf(map.get("time")));
        builder.addFormDataPart("place",String.valueOf(map.get("place")));
        builder.addFormDataPart("detail_type",String.valueOf(map.get("detail_type")));
        builder.addFormDataPart("card_number",String.valueOf(map.get("card_number")));
        builder.addFormDataPart("card_name",String.valueOf(map.get("card_name")));
        builder.addFormDataPart("name",String.valueOf(map.get("name")));
        builder.addFormDataPart("phone",String.valueOf(map.get("phone")));
        builder.addFormDataPart("item_description",String.valueOf("item_description"));
        builder.addFormDataPart("other_tag","");
        builder.addFormDataPart("duration",String.valueOf(map.get("duration")));
        List<MultipartBody.Part> parts = builder.build().parts();
        releaseApi.updateReleaseWithPic(lostOrFound,parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::successCallBack, new RxErrorHandler());
    }

    @Override
    public void successCallBack(BaseBean baseBean) {
        releaseView.successCallBack();
    }

    @Override
    public void updateEditData(Map<String, Object> map, String lostOrFound, int id) {
        releaseApi = RetrofitProvider.getRetrofit().create(ReleaseApi.class);
        String lof;
        if (Objects.equals(lostOrFound, "editFound")) {
            lof = "found";
        } else {
            lof = "lost";
        }
        releaseApi.updateEdit(map, lof, String.valueOf(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::successEditCallback, new RxErrorHandler());
    }

    @Override
    public void successEditCallback(BaseBean baseBean) {
        releaseView.successCallBack();
    }

    @Override
    public void delete(int id) {
        releaseApi = RetrofitProvider.getRetrofit().create(ReleaseApi.class);
        releaseApi.delete(String.valueOf(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::deleteSuccessCallBack, new RxErrorHandler());
    }

    @Override
    public void deleteSuccessCallBack(BaseBean baseBean) {
        releaseView.deleteSuccessCallBack();
    }


}
