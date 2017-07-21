package br.com.daciosoftware.ouvidoriabelfordroxo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.daciosoftware.ouvidoriabelfordroxo.util.MyDateUtil;

/**
 * Created by fdacio on 19/07/17.
 */
public class ProtocoloListAdapter extends BaseAdapter {

    private Context context;
    private List<Protocolo> listProtocolo;

    public ProtocoloListAdapter(Context context, List<Protocolo> listProtocolo){
        this.context = context;
        this.listProtocolo = listProtocolo;
    }

    @Override
    public int getCount() {
        return listProtocolo.size();
    }

    @Override
    public Protocolo getItem(int position) {
        return listProtocolo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_protocolos_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewNumero = (TextView) view.findViewById(R.id.textViewNumero);
            viewHolder.textViewData = (TextView) view.findViewById(R.id.textViewData);
            viewHolder.textViewStatus = (TextView) view.findViewById(R.id.textViewSituacao);
            viewHolder.textViewReclamacao = (TextView) view.findViewById(R.id.textViewReclamacao);
            viewHolder.textViewResolucao = (TextView) view.findViewById(R.id.textViewResolucao);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Protocolo protocolo = getItem(position);

        String numero = String.format("%06d",protocolo.getNumero());
        String ano = String.valueOf(protocolo.getAno());

        String[] statusPossiveis = {"Aberto","Posto em Execução","Finalizado","Excluído"};
        Integer status = protocolo.getStatus();
        String dataStatus = "";
        if(status == 2){
            dataStatus = " em " + MyDateUtil.calendarToDateTimeBr(protocolo.getDataExecucao());
        }else if (status == 3){
            dataStatus = " em " + MyDateUtil.calendarToDateTimeBr(protocolo.getDataFinalizacao());
        }
        viewHolder.textViewNumero.setText(numero+"/"+ano);
        viewHolder.textViewData.setText(MyDateUtil.calendarToDateTimeBr(protocolo.getData()));
        viewHolder.textViewStatus.setText(statusPossiveis[status-1]+dataStatus);
        viewHolder.textViewReclamacao.setText(protocolo.getReclamacao());
        viewHolder.textViewResolucao.setText(protocolo.getResultado());
        return view;

    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    private static class ViewHolder {
        TextView textViewAno;
        TextView textViewNumero;
        TextView textViewData;
        TextView textViewStatus;
        TextView textViewReclamacao;
        TextView textViewResolucao;
    }

}
