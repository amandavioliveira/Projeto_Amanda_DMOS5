package br.edu.ifsp.arq.dmos5_2020s1.pordosol.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.pordosol.R;
import br.edu.ifsp.arq.dmos5_2020s1.pordosol.api.RetrofitService;
import br.edu.ifsp.arq.dmos5_2020s1.pordosol.dao.SolDao;
import br.edu.ifsp.arq.dmos5_2020s1.pordosol.model.Sol;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuscarActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 64;
    private static final String BASE_URL = "https://api.sunrise-sunset.org";

    private TextView sunriseTextView;
    private TextView sunsetTextView;

    private Retrofit mRetrofit;

    private String latString;
    private String lngString;

    private SolDao solDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        solDAO = new SolDao(this);

        sunriseTextView = findViewById(R.id.textview_sunrise);
        sunsetTextView = findViewById(R.id.textview_sunset);

        buscarPosicao();

        if(temPermissao()){
            buscarAPI();
        }else{
            solicitaPermissao();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean temPermissao(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void solicitaPermissao(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            final Activity activity = this;
            new AlertDialog.Builder(this)
                    .setMessage(R.string.explicacao_permissao)
                    .setPositiveButton(R.string.botao_permitir, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, REQUEST_PERMISSION);
                        }
                    })
                    .setNegativeButton(R.string.botao_nao_permitir, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.INTERNET
                    },
                    REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.INTERNET) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    buscarAPI();
                }
            }
        }
    }

    private void buscarPosicao(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            latString = bundle.getString(MainActivity.KEY_LAT);
            lngString = bundle.getString(MainActivity.KEY_LNG);
        }
    }

    private void buscarAPI(){
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitService mRetrofitService = mRetrofit.create(RetrofitService.class);

        String results = "/json?lat=" + latString + "&lng=-" + lngString;

        Call<Sol> sol = mRetrofitService.getDadosSol(results);

        sol.enqueue(new Callback<Sol>() {
            @Override
            public void onResponse(Call<Sol> call, Response<Sol> response) {
                if(response.isSuccessful()){
                    Sol sol = response.body();
                    solDAO.adicionar(sol);
                    setResult(Activity.RESULT_OK);
                    update(sol);
                }else{
                    Toast.makeText(BuscarActivity.this, R.string.posicao_nao_encontrado,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sol> call, Throwable t) {
                Toast.makeText(BuscarActivity.this, R.string.erro_api,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update(Sol sol){
        if(sol != null){
            sunriseTextView.setText(sol.getSunrise());
            sunsetTextView.setText(sol.getSunset());
        }
    }
}