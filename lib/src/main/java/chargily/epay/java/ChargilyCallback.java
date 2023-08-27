package chargily.epay.java;

import retrofit2.Call;

public interface ChargilyCallback<T> {

    void onResponse(Call<T> call, ChargilyResponse response);

    void onFailure(Call<T> call, Throwable t);
}
