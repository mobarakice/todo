/*
package com.mobarak.todo.data.remote;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponseParser {

    private static final String TAG = ResponseParser.class.getSimpleName();

    private IRetroResponseCallback callback;

    public ResponseParser(IRetroResponseCallback callback) {
        this.callback = callback;
    }

    public synchronized <T> void getResponse(Call<T> call) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {

                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: Successful");
                        callback.onSuccessResponse(response.body());
                    } else {
                        Log.i(TAG, "onResponse: Error");
                        callback.onErrorResponse(ApiErrorUtil.parseError(response));
                    }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.i(TAG, "onFailure: Failed" + t.getMessage());
                t.printStackTrace();
                callback.onFailedResponse(t);
            }
        });
    }
}*/
