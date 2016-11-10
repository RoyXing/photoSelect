package com.xzy.roy.photoselect.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.xzy.roy.photoselect.adapter.AddPhotoAdapter;

import java.util.Collections;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by roy on 2016/11/7.
 */

public class MyItemTouchHelper extends ItemTouchHelper.Callback {

    private AddPhotoAdapter mAddPhotoAdapter;
    private int maxNum;

    public MyItemTouchHelper(AddPhotoAdapter adapter, int count) {
        this.mAddPhotoAdapter = adapter;
        this.maxNum = count;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mAddPhotoAdapter.getData(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mAddPhotoAdapter.getData(), i, i - 1);
            }
        }
        mAddPhotoAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (mAddPhotoAdapter.getData().size() < maxNum) {
            PhotoInfo photo = mAddPhotoAdapter.addDefaultItem();
            mAddPhotoAdapter.getData().add(photo);
            mAddPhotoAdapter.notifyItemInserted(mAddPhotoAdapter.getData().size() - 1);
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
