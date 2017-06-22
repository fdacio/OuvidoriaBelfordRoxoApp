package br.com.daciosoftware.ouvidoriabelfordroxo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setLogo(R.mipmap.ic_launcher);
            toolbar.setTitle(getResources().getString(R.string.title_activity_result));
            setSupportActionBar(toolbar);
        }


        String json = getIntent().getStringExtra("JSON");
        ImageView imageViewResult = (ImageView) findViewById(R.id.imageViewResult);
        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        TextView textViewNumeroProtocolo = (TextView) findViewById(R.id.textViewNumeroProtocolo);

        try {
            JSONObject jsonObject = new JSONObject(json);
            if(jsonObject.getString("Status").equals("OK")){
                imageViewResult.setImageResource(R.mipmap.ic_result_ok);
                textViewResult.setText(R.string.label_result_ok);
                String numeroProtocolo = String.format(getResources().getString(R.string.label_result_numero_protocolo), jsonObject.getString("Numero"));
                textViewNumeroProtocolo.setText(numeroProtocolo);
            }else{
                imageViewResult.setImageResource(R.mipmap.ic_result_error);
                textViewResult.setText(R.string.label_result_error);
                textViewNumeroProtocolo.setText(jsonObject.getString("Error"));
            }


        }catch (JSONException e){
            e.printStackTrace();
            imageViewResult.setImageResource(R.mipmap.ic_result_error);
            textViewResult.setText(R.string.label_result_error);
            textViewNumeroProtocolo.setText(e.getLocalizedMessage());
        }

        Button buttonOK = (Button) findViewById(R.id.buttonResultOk);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
