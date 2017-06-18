package com.example.poo1458.studentcompanionapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by poo1458 on 5/5/17.
 */
public class TranscriptAdapter extends RecyclerView.Adapter<TranscriptAdapter.ViewHolder>
{
    private List<ClassListItem> listItems;
    private Context context;



    public TranscriptAdapter(List<ClassListItem> listItems,Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transcript_layout,parent,false);
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




        public ViewHolder(View itemView)
        {
            super(itemView);

            classListHead = (TextView) itemView.findViewById(R.id.classListHead);
            classListName = (TextView) itemView.findViewById(R.id.classListName);
        }
    }
}
