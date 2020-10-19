package br.edu.ifsp.arq.dmos5_2020s1.pordosol.api;

import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.pordosol.model.Sol;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("{results}")
    Call<Sol> getDadosSol(@Path("results") String results);
}
