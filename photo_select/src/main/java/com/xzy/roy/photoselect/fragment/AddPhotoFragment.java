package com.xzy.roy.photoselect.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xzy.roy.photoselect.R;
import com.xzy.roy.photoselect.adapter.AddPhotoAdapter;
import com.xzy.roy.photoselect.adapter.onRecyclerViewItemClickListener;
import com.xzy.roy.photoselect.utils.MyItemTouchHelper;

import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

import static com.xzy.roy.photoselect.adapter.AddPhotoAdapter.DEFAULT_PIC_ID;

/**
 * Created by roy on 26/10/2016.
 */

public class AddPhotoFragment extends Fragment implements onRecyclerViewItemClickListener {

    private View view;
    private RecyclerView fragment_add_photo;
    private AddPhotoAdapter mAddPhotoAdapter;
    private int maxNum = 9;
    private int REQUEST_CODE_GALLERY = 0x12313;
    private ItemTouchHelper itemTouchHelper;

    private onPhotoNumChangedListener mNumCountListener;

    public void setNumCountListener(onPhotoNumChangedListener numCountListener) {
        mNumCountListener = numCountListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_photo, null);

        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view) {
        fragment_add_photo = (RecyclerView) view.findViewById(R.id.fragment_add_photo);
        mAddPhotoAdapter = new AddPhotoAdapter(getActivity(), maxNum);
    }

    private void initEvent() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setAutoMeasureEnabled(true);
        fragment_add_photo.setLayoutManager(layoutManager);
        fragment_add_photo.setAdapter(mAddPhotoAdapter);
        fragment_add_photo.setItemAnimator(new DefaultItemAnimator());
        itemTouchHelper = new ItemTouchHelper(new MyItemTouchHelper(mAddPhotoAdapter,maxNum));
        itemTouchHelper.attachToRecyclerView(fragment_add_photo);

        mAddPhotoAdapter.setRecyclerViewItemClickListener(this);

    }

    @Override
    public void onImagePickClick(View view, int position) {
        FunctionConfig config = new FunctionConfig.Builder().setMutiSelectMaxSize(maxNum - mAddPhotoAdapter.getData().size() + 1).build();
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                Log.e("roy", resultList.toString());
                mAddPhotoAdapter.addData(resultList);
                if (mNumCountListener != null)
                    mNumCountListener.getSelectedPhotoNum(mAddPhotoAdapter.getAllPath().size());
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    @Override
    public void onImagePreviewClick(View view, final int position) {
        FunctionConfig config = new FunctionConfig.Builder().setMutiSelectMaxSize(1).build();
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, config, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                mAddPhotoAdapter.getData().remove(position);
                mAddPhotoAdapter.getData().add(position, resultList.get(0));
                mAddPhotoAdapter.notifyItemChanged(position);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    @Override
    public void onImageLongClick(View view, AddPhotoAdapter.MyViewHolder holder) {
        boolean isContain = false;

        if (mAddPhotoAdapter.getData().size() == maxNum) {
            for (PhotoInfo info : mAddPhotoAdapter.getData()) {
                if (info.getPhotoId() == DEFAULT_PIC_ID) {
                    isContain = true;
                }
            }
        }

        if (mAddPhotoAdapter.getData().size() < maxNum || isContain) {
            mAddPhotoAdapter.getData().remove(mAddPhotoAdapter.getData().size() - 1);
            mAddPhotoAdapter.notifyItemRemoved(mAddPhotoAdapter.getData().size());
        }

        itemTouchHelper.startDrag(holder);
    }


    @Override
    public void onCloseImageClick(View view, int position) {
        /**
         * 该判断条件防止删除过快出现的crash
         */
        if (position <= mAddPhotoAdapter.getData().size() - 1) {
            mAddPhotoAdapter.getData().remove(position);
            mAddPhotoAdapter.notifyItemRemoved(position);
            boolean isContain = false;
            for (PhotoInfo photo : mAddPhotoAdapter.getData()) {
                if (photo.getPhotoId() == DEFAULT_PIC_ID) {
                    isContain = true;
                }
            }

            if (!isContain) {
                PhotoInfo photo = mAddPhotoAdapter.addDefaultItem();
                mAddPhotoAdapter.getData().add(photo);
                mAddPhotoAdapter.notifyItemInserted(mAddPhotoAdapter.getData().size() - 1);
            }

        }
        if (mNumCountListener != null)
            mNumCountListener.getSelectedPhotoNum(mAddPhotoAdapter.getAllPath().size());
    }

    /**
     * 设置可选择的最大照片数量
     *
     * @param maxNum
     */
    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
        mAddPhotoAdapter.setMaxNum(maxNum);
        mAddPhotoAdapter.notifyDataSetChanged();
    }

    /**
     * 获取选择照片的本地路径
     *
     * @return
     */
    public List<String> getAllSelectedPath() {
        return mAddPhotoAdapter.getAllPath();
    }
}
