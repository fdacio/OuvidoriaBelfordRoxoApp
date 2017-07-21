package br.com.daciosoftware.ouvidoriabelfordroxo.db.contract;

/**
 * Created by Dácio Braga on 19/07/2016.
 */
public class ProtocoloContract implements InterfaceContractDatabase {

    @Override
    public String getTableName() {
        return ContractDatabase.Protocolo.NOME_TABELA;
    }

    @Override
    public String[] getAllColumns() {
        return ContractDatabase.Protocolo.COLUNAS;
    }

    @Override
    public String getIdColumn() {
        return ContractDatabase.Protocolo._ID;
    }
}
