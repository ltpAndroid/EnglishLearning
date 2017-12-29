package com.tarena.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.squareup.picasso.Picasso;
import com.tarena.app.R;
import com.tarena.app.entity.User;
import com.tarena.app.views.CircleImageView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class RequestAdapter extends MyAdapter<String> {
    public RequestAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.item_request, null);
            holder.ivHead = convertView.findViewById(R.id.iv_request_head);
            holder.tvame = convertView.findViewById(R.id.tv_request_name);
            holder.tvAgree = convertView.findViewById(R.id.tv_agree);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String name = getData().get(position);
        BmobQuery<User> bq = new BmobQuery<>();
        bq.addWhereEqualTo("username", name);
        bq.findObjects(new FindListener<User>() {
            @Override
            public void done(final List<User> list, BmobException e) {
                final User user = list.get(0);
                Picasso.with(getContext()).load(user.getHeadPath()).into(holder.ivHead);
                holder.tvame.setText(user.getNick());
                holder.tvAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            EMClient.getInstance().contactManager().acceptInvitation(user.getUsername());
                            ((Activity) getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.tvAgree.setText("已同意");
                                    holder.tvAgree.setEnabled(false);
                                    Toast.makeText(getContext(), "您同意了" + name + "的好友请求", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        return convertView;
    }

    class ViewHolder {
        CircleImageView ivHead;
        TextView tvame;
        TextView tvAgree;
    }
}
