package br.com.daciosoftware.ouvidoriabelfordroxo.db.contract;

/**
 * Created by Dácio Braga on 04/07/2016.
 */
public interface InterfaceContractDatabase {
    String getTableName();
    String[] getAllColumns();
    String getIdColumn();
}
