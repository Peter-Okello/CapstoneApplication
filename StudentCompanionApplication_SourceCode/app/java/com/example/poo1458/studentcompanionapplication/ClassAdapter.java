package com.example.poo1458.studentcompanionapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by poo1458 on 4/30/17.
 */
public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder>
{
    private List<ClassListItem> listItems;
    private Context context;



    public ClassAdapter(List<ClassListItem> listItems,Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list_item,parent,false);
        return new ViewHolder(v);



    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        ClassListItem classListItem = listItems.get(position);

        holder.classListHead.setText(classListItem.getHead());
        holder.classListName.setText(classListItem.getBody());



    }

    @Override
    public int getItemCount()
    {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView classListHead;
        public TextView classListName;
        public CheckBox checkedTextView;



        public ViewHolder(View itemView)
        {
            super(itemView);

            classListHead = (TextView) itemView.findViewById(R.id.classListHead);
            classListName = (TextView) itemView.findViewById(R.id.classListName);
            checkedTextView = (CheckBox) itemView.findViewById(R.id.checkedTextView1);



        }
    }

}
