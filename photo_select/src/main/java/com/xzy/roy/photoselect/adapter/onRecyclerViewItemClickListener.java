package com.xzy.roy.photoselect.adapter;

import android.view.View;

/**
 * Created by roy on 31/10/2016.
 */

public interface onRecyclerViewItemClickListener {

    void onImagePickClick(View view, int position);

    void onImagePreviewClick(View view, int position);

    void onImageLongClick(View view, AddPhotoAdapter.MyViewHolder holder);

    void onCloseImageClick(View view, int position);

}
