package br.com.daciosoftware.ouvidoriabelfordroxo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.daciosoftware.ouvidoriabelfordroxo.db.Database;
import br.com.daciosoftware.ouvidoriabelfordroxo.util.Constantes;
import br.com.daciosoftware.ouvidoriabelfordroxo.util.DeviceInformation;
import br.com.daciosoftware.ouvidoriabelfordroxo.util.DialogBox;

public class FormularioProtocoloActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextCelular;
    private EditText editTextEndereco;
    private EditText editTextNumero;
    private EditText editTextBairro;
    private EditText editTextReclamacao;
    private ImageView imageViewRequeridNome;
    private ImageView imageViewRequeridCelular;
    private ImageView imageViewRequeridEndereco;
    private ImageView imageViewRequeridNumero;
    private ImageView imageViewRequeridBairro;
    private ImageView imageViewRequeridReclamacao;
    private Button btnEnviar;
    private ProgressBar progressBar;
    private Protocolo protocolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_protocolo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
        {
            toolbar.setLogo(R.mipmap.ic_launcher);
            toolbar.setTitle(getResources().getString(R.string.label_button_abrir_protocolo));
            setSupportActionBar(toolbar);
        }

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextCelular = (EditText) findViewById(R.id.editTextCelular);
        editTextEndereco = (EditText) findViewById(R.id.editTextEndereco);
        editTextNumero = (EditText) findViewById(R.id.editTextNumero);
        editTextBairro = (EditText) findViewById(R.id.editTextBairro);
        editTextReclamacao = (EditText) findViewById(R.id.editTextReclamacao);
        imageViewRequeridNome = (ImageView) findViewById(R.id.imageViewRequeridNome);
        imageViewRequeridCelular = (ImageView) findViewById(R.id.imageViewRequeridCelular);
        imageViewRequeridEndereco = (ImageView) findViewById(R.id.imageViewRequeridEndereco);
        imageViewRequeridNumero = (ImageView) findViewById(R.id.imageViewRequeridNumero);
        imageViewRequeridBairro = (ImageView) findViewById(R.id.imageViewRequeridBairro);
        imageViewRequeridReclamacao = (ImageView) findViewById(R.id.imageViewRequeridReclamacao);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageViewRequeridNome.setVisibility(View.INVISIBLE);
        imageViewRequeridCelular.setVisibility(View.INVISIBLE);
        imageViewRequeridEndereco.setVisibility(View.INVISIBLE);
        imageViewRequeridNumero.setVisibility(View.INVISIBLE);
        imageViewRequeridBairro.setVisibility(View.INVISIBLE);
        imageViewRequeridReclamacao.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);


        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar();
            }
        });


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Database.encerrarSessao();
    }

    private boolean validaForm(){
        boolean r = true;
        String nome = editTextNome.getText().toString();
        String celular = editTextCelular.getText().toString();
        String endereco = editTextEndereco.getText().toString();
        String numero = editTextNumero.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String reclamacao = editTextReclamacao.getText().toString();

        imageViewRequeridNome.setVisibility(View.INVISIBLE);
        imageViewRequeridCelular.setVisibility(View.INVISIBLE);
        imageViewRequeridEndereco.setVisibility(View.INVISIBLE);
        imageViewRequeridNumero.setVisibility(View.INVISIBLE);
        imageViewRequeridBairro.setVisibility(View.INVISIBLE);
        imageViewRequeridReclamacao.setVisibility(View.INVISIBLE);

        if(nome.trim().length() < 5){
            imageViewRequeridNome.setVisibility(View.VISIBLE);
            r = false;
        }

        if(celular.trim().length() < 8){
            imageViewRequeridCelular.setVisibility(View.VISIBLE);
            r = false;
        }

        if(endereco.trim().length() < 5){
            imageViewRequeridEndereco.setVisibility(View.VISIBLE);
            r = false;
        }
        if(numero.trim().length() < 1){
            imageViewRequeridNumero.setVisibility(View.VISIBLE);
            r = false;
        }
        if(bairro.trim().length() < 5){
            imageViewRequeridBairro.setVisibility(View.VISIBLE);
            r = false;
        }
        if(reclamacao.trim().length() < 5){
            imageViewRequeridReclamacao.setVisibility(View.VISIBLE);
            r = false;
        }


        return r;

    }

    private void enviar() {

        if(!validaForm()) {
            new DialogBox(FormularioProtocoloActivity.this, DialogBox.DialogBoxType.INFORMATION, FormularioProtocoloActivity.this.getResources().getString(R.string.app_name), FormularioProtocoloActivity.this.getResources().getString(R.string.informe_campos_obrigatorios)).show();
            return;
        }
        if(!DeviceInformation.isNetwork(getApplicationContext())){
            new DialogBox(FormularioProtocoloActivity.this, DialogBox.DialogBoxType.INFORMATION, FormularioProtocoloActivity.this.getResources().getString(R.string.app_name), FormularioProtocoloActivity.this.getResources().getString(R.string.verifique_sua_conexao)).show();
            return;
        }
        String nome = editTextNome.getText().toString();
        String celular = editTextCelular.getText().toString();
        String endereco = editTextEndereco.getText().toString();
        String numero = editTextNumero.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String reclamacao = editTextReclamacao.getText().toString();


        this.protocolo = new Protocolo();
        this.protocolo.setNome(nome);
        this.protocolo.setCelular(celular);
        this.protocolo.setEndereco(endereco);
        this.protocolo.setNumeroEndereco(numero);
        this.protocolo.setBairro(bairro);
        this.protocolo.setReclamacao(reclamacao);

        new HttpAsyncTask().execute(Constantes.URL_WEB_SERVICE_DEFAULT+"?class=GravaProtocoloWS&method=gravarDados");
    }

    private String Post(String url, Protocolo protocolo){

        InputStream inputStream;
        String result;
        String json;

        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("nome", protocolo.getNome());
            jsonObject.accumulate("celular", protocolo.getCelular());
            jsonObject.accumulate("endereco", protocolo.getEndereco());
            jsonObject.accumulate("numero", protocolo.getNumeroEndereco());
            jsonObject.accumulate("bairro", protocolo.getBairro());
            jsonObject.accumulate("reclamacao", protocolo.getReclamacao());

            json = jsonObject.toString();

            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);

            httpPost.setHeader("Accept","application/json");
            httpPost.setHeader("Content-type","application/json");

            HttpResponse httpResponse = httpClient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null){
                result = convertInputStreamToString(inputStream);
            }else{
                result = "{'Status':'ERROR', 'Error':'Tente mais tarde'}";
            }
        }catch (Exception e){
            result = "{'Status':'ERROR', 'Error':'Tente mais tarde'}";
        }

        return result;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line;
        }
        inputStream.close();
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            return Post(urls[0], protocolo);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnEnviar.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String result){
            progressBar.setVisibility(View.INVISIBLE);
            finish();
            Intent intent = new Intent(FormularioProtocoloActivity.this, ResultActivity.class);
            intent.putExtra("JSON", result);
            startActivity(intent);
        }
    }
}
