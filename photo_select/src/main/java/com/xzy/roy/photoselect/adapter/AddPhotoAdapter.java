package com.xzy.roy.photoselect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xzy.roy.photoselect.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by roy on 26/10/2016.
 */

public class AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.MyViewHolder> {

    private List<PhotoInfo> data = new ArrayList<>();
    private LayoutInflater mInflater;
    public final static int DEFAULT_PIC_ID = 0x2B67;
    private int maxNum;
    private Context mContext;

    private onRecyclerViewItemClickListener mRecyclerViewItemClickListener;

    public void setRecyclerViewItemClickListener(onRecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.mRecyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    public AddPhotoAdapter(Context context, int max) {
        this.maxNum = max;
        mInflater = LayoutInflater.from(context);
        mContext = context;
        PhotoInfo photo = addDefaultItem();
        data.add(photo);
    }

    @NonNull
       public PhotoInfo addDefaultItem() {
        PhotoInfo photo = new PhotoInfo();
        photo.setPhotoPath(R.drawable.icon_add_photo_new + "");
        photo.setPhotoId(DEFAULT_PIC_ID);
        return photo;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_add_photo_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (isAddIcon(position)) {
            holder.im_add_photo_item.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.im_add_photo_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mRecyclerViewItemClickListener.onImagePickClick(view, pos);
                }
            });
            holder.im_close_photo_item.setVisibility(View.GONE);
        } else {
            holder.im_add_photo_item.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.im_add_photo_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mRecyclerViewItemClickListener.onImagePreviewClick(holder.itemView, pos);
                }
            });

            holder.im_add_photo_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mRecyclerViewItemClickListener.onImageLongClick(view, holder);
                    return true;
                }
            });
            holder.im_close_photo_item.setVisibility(View.VISIBLE);
        }
        if (getInfo(position).getPhotoPath().equals(R.drawable.icon_add_photo_new + ""))
            Glide.with(mContext).load(R.drawable.icon_add_photo_new).into(holder.im_add_photo_item);
        else
            Glide.with(mContext).load(getInfo(position).getPhotoPath()).into(holder.im_add_photo_item);

        holder.im_close_photo_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                if (pos < data.size())
                    mRecyclerViewItemClickListener.onCloseImageClick(view, pos);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private PhotoInfo getInfo(int position) {
        return data.get(position);
    }

    private boolean isAddIcon(int position) {
        return data.get(position).getPhotoId() == DEFAULT_PIC_ID;
    }

    public void addData(List<PhotoInfo> list) {
        for (PhotoInfo photo : list) {
            data.add(data.size() - 1, photo);
            notifyItemInserted(data.size() - 2);
        }
        if (data.size() == maxNum + 1) {
            data.remove(data.size() - 1);
            notifyItemRemoved(data.size());
        }
    }

    public List<PhotoInfo> getData() {
        return data;
    }

    public List<String> getAllPath() {
        List<String> list = new ArrayList<>();
        for (PhotoInfo info : data) {
            if (info.getPhotoId() != DEFAULT_PIC_ID)
                list.add(info.getPhotoPath().trim());
        }
        return list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView im_add_photo_item;
        private ImageView im_close_photo_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            im_add_photo_item = (ImageView) itemView.findViewById(R.id.im_add_photo_item);
            im_close_photo_item = (ImageView) itemView.findViewById(R.id.im_close_photo_item);
        }
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }
}
