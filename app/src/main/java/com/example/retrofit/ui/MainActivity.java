package com.example.retrofit.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit.R;
import com.example.retrofit.adapter.PostAdapter;
import com.example.retrofit.apiService.RetrofitClient;
import com.example.retrofit.model.Post;
import com.example.retrofit.util.MyDividerItemDecoration;
import com.example.retrofit.util.RecyclerTouchListener;
import com.example.retrofit.util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PostAdapter.ClickListener {
    private PostAdapter postAdapter;
    private RecyclerView rvPost;
    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvPost = findViewById(R.id.rvPost);
        rvPost.setHasFixedSize(true);
        rvPost.setItemAnimator(new DefaultItemAnimator());
        rvPost.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        //use linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPost.setLayoutManager(layoutManager);
        //specify an adapter
        postAdapter = new PostAdapter(this,MainActivity.this);
        //get all posts
        getPosts();


    }

    @Override
    public void onClick(int position, Post post) {
        showActionsDialog(position,post);
    }

    private void showActionsDialog(final int position, final Post post) {
        CharSequence[] colors = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    updatePost(position,post);
                } else {
                    deleteNote(position,post);
                }
            }
        });
        builder.show();
    }

    private void updatePost(final int position, final Post post) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.update_post_layout, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText etPostId = view.findViewById(R.id.et_post_id);
        final EditText etPostUserId = view.findViewById(R.id.et_post_user_id);
        final EditText etPostTitle = view.findViewById(R.id.et_post_title);
        final EditText etPostBody = view.findViewById(R.id.et_post_body);

        //show data
        etPostId.setText(post.getId()+"");
        etPostUserId.setText(post.getUserId()+"");
        etPostTitle.setText(post.getTitle());
        etPostBody.setText(post.getBody());

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("update", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogBox, int id) {
                        int postId = Integer.parseInt(etPostId.getText().toString().trim());
                        final int postUserId = Integer.parseInt(etPostUserId.getText().toString().trim());
                        final String postTitle = etPostTitle.getText().toString().trim();
                        final String postBody = etPostBody.getText().toString().trim();
                        Post post1=new Post(postUserId,postTitle,postBody);
                        Call<Post> updateCall= RetrofitClient.getInstance().getApi().updatePost(postId,post1);
                        updateCall.enqueue(new Callback<Post>() {
                            @Override
                            public void onResponse(Call<Post> call, Response<Post> response) {
                                if (response.isSuccessful()){
                                    post.setUserId(postUserId);
                                    post.setTitle(postTitle);
                                    post.setBody(postBody);
                                    posts.set(position,post);
                                    postAdapter.notifyItemChanged(position);
                                    dialogBox.dismiss();
                                    Toast.makeText(MainActivity.this, "update success", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Post> call, Throwable t) {
                                dialogBox.dismiss();
                            }
                        });
                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

//        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
    }

    private void deleteNote(final int position,Post post) {
        Call<Void> deleteCall = RetrofitClient.getInstance().getApi().deletePost(post.getId());
        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    posts.remove(position);
                    postAdapter.notifyItemRemoved(position);
                    Toast.makeText(MainActivity.this, "delete successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void getPosts() {
        if (Util.isNetworkAvailable(this)) {
            Call<List<Post>> postCall = RetrofitClient.getInstance().getApi().getPosts();
            postCall.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    if (response.isSuccessful()) {
                        posts = response.body();
                        rvPost.setAdapter(postAdapter);
                        postAdapter.setPosts(posts);
                        Log.e("response", response.toString());
                        Log.e("post size", posts.size() + "");

                    } else {
                        Log.e("response code", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    Log.e("fail", t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "no internet connection", Toast.LENGTH_SHORT).show();
        }
    }
}
