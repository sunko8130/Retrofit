package com.example.retrofit.apiService;

import com.example.retrofit.model.Comment;
import com.example.retrofit.model.Post;
import com.example.retrofit.model.StateResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @PUT("posts/{id}")
    Call<Post> updatePost(@Path("id")int postId, @Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id")int postId);

    @GET("cities/cities_array.json")
    Call<List<StateResponse>> getStates();
}
