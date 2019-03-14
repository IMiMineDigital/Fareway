package com.fareway.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fareway.R;
import com.fareway.model.Group;

import java.util.List;

public class CustomGroupAdapter extends RecyclerView.Adapter<CustomGroupAdapter.MyViewHolder> {

    private Context mContext;
    private List<Group> groupList;
    private CustomAdapterGroupItemsListener listener2;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Button btn_group;

        public MyViewHolder(View view) {
            super(view);
            btn_group=(Button) view.findViewById(R.id.btn_group);

            btn_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onGroupItemSelected(groupList.get(getAdapterPosition()));
                    // Log.i("test","test");
                }
            });


        }
    }


    public CustomGroupAdapter(Context mContext, List<Group> groupList,CustomAdapterGroupItemsListener listener2)
    {
        this.mContext = mContext;
        this.groupList = groupList;
        this.listener2 = listener2;
    }

    @Override
    public CustomGroupAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_layout, parent, false);

        return new CustomGroupAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomGroupAdapter.MyViewHolder holder, int position) {

        final Group groupItem = groupList.get(position);

       // holder.tv_unit.setText(relatedItem.getPackagingSize());
        holder.btn_group.setText("Group "+groupItem.getGroupname());
       /* holder.btn_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test",groupItem.getGroupname());
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public interface CustomAdapterGroupItemsListener {
        void onGroupItemSelected(Group groupItem);

    }


}


