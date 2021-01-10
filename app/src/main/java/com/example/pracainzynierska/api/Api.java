package com.example.pracainzynierska.api;

import com.example.pracainzynierska.model.CurrentUserResponse;
import com.example.pracainzynierska.model.EventRequest;
import com.example.pracainzynierska.model.EventResponse;
import com.example.pracainzynierska.model.HabitRequest;
import com.example.pracainzynierska.model.HabitResponse;
import com.example.pracainzynierska.model.LoginRequest;
import com.example.pracainzynierska.model.LoginResponse;
import com.example.pracainzynierska.model.TaskRequest;
import com.example.pracainzynierska.model.TaskResponse;
import com.example.pracainzynierska.model.User;
import com.google.android.gms.auth.api.accounttransfer.AuthenticatorTransferCompletionStatus;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    @POST("auth/signup")
    Call<ResponseBody> createUser(@Body User user);

    @POST("auth/signin")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("user/me")
    Call<CurrentUserResponse> getCurrentUser(@Header("Authorization") String token);

    @GET("task/all/{user_id}")
    Call<List<TaskResponse>> getTasks(@Path(value = "user_id", encoded = true) long userId, @Header("Authorization") String token);

    @POST("task/add")
    Call<ResponseBody> addTasks(@Body TaskRequest taskRequest,  @Header("Authorization") String token);

    @PATCH("task/done/{task_id}")
    Call<ResponseBody> doneTask(@Path(value = "task_id", encoded = true) long taskId, @Header("Authorization") String token);

    @DELETE("task/delete/{task_id}")
    Call<ResponseBody> deleteTasks(@Path(value = "task_id", encoded = true) long taskId, @Header("Authorization") String token);

    @GET("event/all/{user_id}")
    Call<List<EventResponse>> getEvents(@Path(value = "user_id", encoded = true) long userId, @Header("Authorization") String token);

    @POST("event/add")
    Call<ResponseBody> addEvents(@Body EventRequest eventRequest, @Header("Authorization") String token);

    @DELETE("event/delete/{event_id}")
    Call<ResponseBody> deleteEvents(@Path(value = "event_id", encoded = true) long userId, @Header("Authorization") String token);

    @GET("habit/all/{user_id}")
    Call<List<HabitResponse>> getHabits(@Path(value = "user_id",encoded = true) long userId, @Header("Authorization") String token );

    @POST("habit/add")
    Call<ResponseBody> addHabits(@Body HabitRequest habitRequest, @Header("Authorization") String token);

    @PATCH("habit/increment/{habit_id}")
    Call<ResponseBody> doneHabit(@Path(value = "habit_id", encoded = true) long habitId, @Header("Authorization") String token);

    @DELETE("habit/delete/{habit_id}")
    Call<ResponseBody> deleteHabits(@Path(value = "habit_id", encoded = true) long habitId, @Header("Authorization") String token);
}






