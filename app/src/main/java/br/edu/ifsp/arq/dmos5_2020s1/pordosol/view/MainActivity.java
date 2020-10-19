package br.edu.ifsp.arq.dmos5_2020s1.pordosol.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.pordosol.R;
import br.edu.ifsp.arq.dmos5_2020s1.pordosol.dao.SolDao;
import br.edu.ifsp.arq.dmos5_2020s1.pordosol.model.Sol;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUESTCODE_SOL = 64;
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";

    private RecyclerView mRecyclerView;
    private SolAdapter solAdapter;

    private List<Sol> solList;
    private SolDao solDao;

    private EditText latEditText;
    private EditText longEditText;
    private Button buscarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recylerview_sol);
        latEditText = findViewById(R.id.edittext_lat);
        longEditText = findViewById(R.id.edittext_long);
        buscarButton = findViewById(R.id.button_buscar);
        buscarButton.setOnClickListener(this);

        solDao = new SolDao(this);
        solList = solDao.recuperaTodos();

        solAdapter = new SolAdapter(solList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(solAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view == buscarButton ){
            buscaPosicao();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void buscaPosicao(){
        String lat = latEditText.getText().toString();
        String lng = longEditText.getText().toString();

        if(!lat.isEmpty() && !lng.isEmpty()) {

            Bundle args = new Bundle();

            args.putString(KEY_LAT, lat);
            args.putString(KEY_LNG, lng);

            Intent intent = new Intent(this, BuscarActivity.class);
            intent.putExtras(args);
            startActivityForResult(intent, REQUESTCODE_SOL);
        }else{
            Toast.makeText(this, R.string.valores_invalidos, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUESTCODE_SOL:
                if(resultCode == RESULT_OK){
                    updateDataSet();
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI(){
        if(solList.size() == 0){
            mRecyclerView.setVisibility(View.GONE);
        }else{
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDataSet(){
        solList.clear();
        solList.addAll(solDao.recuperaTodos());
    }
}