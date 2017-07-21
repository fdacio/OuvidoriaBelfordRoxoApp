package br.com.daciosoftware.ouvidoriabelfordroxo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.daciosoftware.ouvidoriabelfordroxo.util.MyDateUtil;

/**
 * Created by fdacio on 14/10/16.
 */
public class OuvidoriaWebService {

    public OuvidoriaWebService() {

    }

    private Protocolo getProtocoloFromJSONObject(JSONObject jsonObject) throws JSONException, ParseException {

        int ano = jsonObject.getInt("Ano");
        int numero = jsonObject.getInt("Numero");
        Calendar data = MyDateUtil.dateTimeUSToCalendar(jsonObject.getString("Data"));
        Integer status = jsonObject.getInt("Situacao");

        String dataExecucaoTexto = jsonObject.getString("DataExecucao");
        Calendar dataExecucao = null;
        if(!dataExecucaoTexto.equals("")){
            dataExecucao = MyDateUtil.dateTimeUSToCalendar(dataExecucaoTexto);
        }

        String dataFinalizacaoTexto = jsonObject.getString("DataFinalizacao");
        Calendar dataFinalizacao = null;
        if(!dataFinalizacaoTexto.equals("")){
             dataFinalizacao = MyDateUtil.dateTimeUSToCalendar(dataFinalizacaoTexto);
        }

        String reclamacao = jsonObject.getString("Reclamacao");
        String resultado = jsonObject.getString("Resultado");

        Protocolo protocolo = new Protocolo();
        protocolo.setAno(ano);
        protocolo.setNumero(numero);
        protocolo.setData(data);
        protocolo.setDataFinalizacao(dataFinalizacao);
        protocolo.setDataExecucao(dataExecucao);
        protocolo.setStatus(status);
        protocolo.setReclamacao(reclamacao);
        protocolo.setResultado(resultado);

        return protocolo;

    }

    private Protocolo getProtcoloFromJson(String urlWebService) throws IOException, JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(HttpConnection.getContentJSON(urlWebService));
        if(jsonObject.getString("Status").equals("OK")) {
            return getProtocoloFromJSONObject(jsonObject);
        }else{
            return null;
        }
    }

    /**
     * @param ano ano do Protocolo
     * @param numero Número do Protocolo
     * @return Lista de Ordem de Serviço
     * @throws JSONException
     * @throws ParseException
     * @throws IOException
     */
    public Protocolo getProtocolo(int ano, int numero) throws JSONException, ParseException, IOException {

        String urlMetodo = String.format("/?class=ListaProtocoloWS&method=getProtocolos&ano=%1$d&numero=%2$d", ano, numero);

        String urlWebService = "http://www.ouvidoriabelfordroxo.com.br/ws" + urlMetodo;

        try {
            return getProtcoloFromJson(urlWebService);
        } catch (Exception e) {
            throw new RuntimeException("Error ao obter protocolos");
        }

    }
}