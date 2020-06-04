package com.example.flashchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class theChatListAdapter extends RecyclerView.Adapter<theChatListAdapter.ViewHolder>{

    Context mContext;
    ArrayList<InstantMessage> mData;
    String mDisplayName;


    public theChatListAdapter(Context c , ArrayList<InstantMessage>p){
        mContext =c;
        mData =p;


    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.chat_msg_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Boolean Isme = mData.get(position).getAuthor().equals(mDisplayName);
        SetChatRowAppreance(Isme,holder);



        holder.mAuthorView.setText(mData.get(position).getAuthor());
        holder.mMessageView.setText(mData.get(position).getMessage());
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mAuthorView,mMessageView;

        public ViewHolder(final View itemView) {
            super(itemView);
           mAuthorView = (TextView) itemView.findViewById(R.id.author);
           mMessageView = (TextView) itemView.findViewById(R.id.message);
        }
    }

    public void SetChatRowAppreance(Boolean isItMe,ViewHolder holder){

        if(isItMe){
            holder.mAuthorView.setGravity(1);
            holder.mMessageView.setGravity(1);
        }else {
            holder.mAuthorView.setGravity(0);
            holder.mAuthorView.setGravity(0);
        }

    }

}

