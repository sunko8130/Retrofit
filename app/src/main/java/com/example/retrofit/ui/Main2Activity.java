package com.example.retrofit.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.R;
import com.example.retrofit.apiService.RetrofitClient;
import com.example.retrofit.model.Comment;
import com.example.retrofit.util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {
    TextView tvComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvComment = findViewById(R.id.tv_comment);
        //get comments
        getComments();
    }

    private void getComments() {
        if (Util.isNetworkAvailable(this)) {
            Call<List<Comment>> callComment = RetrofitClient.getInstance().getApi().getComments(1);
            callComment.enqueue(new Callback<List<Comment>>() {
                @Override
                public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                    if (response.isSuccessful()) {
                        List<Comment> comments = response.body();
                        for (Comment comment : comments) {
                            String content = "";
                            content += "ID:" + comment.getId() + "\n";
                            content += "Post ID: " + comment.getPostId() + "\n";
                            content += "Name: " + comment.getName() + "\n";
                            content += "Email: " + comment.getEmail() + "\n";
                            content += "Text: " + comment.getBody() + "\n\n";
                            tvComment.append(content);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Comment>> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "no internet connection", Toast.LENGTH_SHORT).show();
        }

    }
}
