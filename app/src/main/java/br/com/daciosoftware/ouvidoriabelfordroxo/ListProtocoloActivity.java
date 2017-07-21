package br.com.daciosoftware.ouvidoriabelfordroxo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.ouvidoriabelfordroxo.dao.ProtocoloDAO;
import br.com.daciosoftware.ouvidoriabelfordroxo.util.DialogBox;

public class ListProtocoloActivity extends AppCompatActivity {

    private ListView listViewProtocolo;
    private TextView textViewEmpty;
    private List<Protocolo> listaProtocoloWS;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_protocolo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setLogo(R.mipmap.ic_launcher);
            toolbar.setTitle(getResources().getString(R.string.title_activity_list_protocolo));
            setSupportActionBar(toolbar);
        }
        listViewProtocolo = (ListView) findViewById(R.id.listViewProtocolos);
        textViewEmpty = (TextView) findViewById(R.id.textViewEmpty);
        listViewProtocolo.setEmptyView(findViewById(R.id.textViewEmpty));
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        new ListaProtocoloTask().execute();
    }

     /*
    Classe interna responsável por carregar os ordens de serviços para activity
    */
    private class ListaProtocoloTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            ProtocoloDAO protocoloDAO = new ProtocoloDAO(ListProtocoloActivity.this);
            List<Protocolo> protocolosLocal = protocoloDAO.listAll();
            listaProtocoloWS = new ArrayList<>();

            for(Protocolo protocolo: protocolosLocal) {
                try {
                    Protocolo protocoloWS = new OuvidoriaWebService().getProtocolo(protocolo.getAno(), protocolo.getNumero());
                    if(protocoloWS != null){
                        listaProtocoloWS.add(protocoloWS);
                    }
                } catch (JSONException | ParseException | IOException | RuntimeException e) {
                    return e.getMessage();
                }
            }
            return "OK";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listViewProtocolo.setAdapter(null);
            progressBar.setVisibility(View.VISIBLE);
            textViewEmpty.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(String retorno) {

            progressBar.setVisibility(View.INVISIBLE);

            if (retorno.equals("OK")) {
                listViewProtocolo.setAdapter(new ProtocoloListAdapter(ListProtocoloActivity.this, listaProtocoloWS));
            } else {
                textViewEmpty.setVisibility(View.VISIBLE);
                new DialogBox(ListProtocoloActivity.this, DialogBox.DialogBoxType.INFORMATION, ListProtocoloActivity.this.getResources().getString(R.string.app_name), retorno).show();
            }
        }

    }

}
