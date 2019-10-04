package com.example.retrofit.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit.R;
import com.example.retrofit.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context mContext;
    private List<Post> posts;
    private ClickListener clicklistener;

    public PostAdapter(Context mContext, ClickListener clicklistener) {
        this.mContext = mContext;
        this.clicklistener = clicklistener;
    }

    public void setPosts(List<Post> postList){
        if (postList==null) return;
        if (posts==null){
            posts=new ArrayList<>();
            posts.addAll(postList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.onBind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        Log.e("adapter size",posts.size()+"");
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvPostId,tvPostUserId,tvPostTitle,tvPostBody;
        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPostId = itemView.findViewById(R.id.tv_post_id);
            tvPostUserId = itemView.findViewById(R.id.tv_post_user_id);
            tvPostTitle = itemView.findViewById(R.id.tv_post_title);
            tvPostBody = itemView.findViewById(R.id.tv_post_body);
        }

        void onBind(final Post post) {
   /*         String content = "";
            content += "ID: " + post.getId() + "\n";
            content += "User ID: " + post.getUserId() + "\n";
            content += "Title: " + post.getTitle() + "\n";
            content += "Text: " + post.getBody();*/
            tvPostId.setText(post.getId()+"");
            tvPostUserId.setText(post.getUserId()+"");
            tvPostTitle.setText(post.getTitle());
            tvPostBody.setText(post.getBody());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicklistener.onClick(getAdapterPosition(),post);
                }
            });
        }
    }

    public interface ClickListener {
        void onClick(int position,Post post);
    }
}
