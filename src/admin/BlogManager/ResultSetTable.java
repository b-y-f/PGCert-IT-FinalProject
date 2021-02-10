package admin.BlogManager;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ResultSetTable extends JTable{

    private final DefaultTableModel dataModel;

    public ResultSetTable(ResultSet rs)
            throws SQLException{

        super();
        dataModel = new DefaultTableModel() {
            @Override
            public Class getColumnClass(int column) {
                // first 5 columns will be represented as an checkbox
                if (column ==6) {
                    return Boolean.class;
                }

                // rest of them as a text
                return String.class;
            }

        };

        setModel(dataModel);

        try (rs) {
            //create an array of column names
            ResultSetMetaData mdata = rs.getMetaData();

            int colCount = mdata.getColumnCount();
            String[] colNames = new String[colCount];
            for (int i = 1; i <= colCount; i++) {
                colNames[i - 1] = mdata.getColumnName(i);
            }
            dataModel.setColumnIdentifiers(colNames);



            //now populate the data
            while (rs.next()) {
//                String[] rowData = new String[colCount];
                Object[] rowData = new Object[colCount];
                for (int i = 1; i <= colCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                dataModel.addRow(rowData);
            }


//            ArrayList<Boolean> visible = new ArrayList<>();
//            while (rs.next()) {
//                String[] rowData = new String[colCount];
//                for (int i = 1; i <= colCount-1; i++) {
//                    if (i == colCount){
//                        visible.add(rs.getBoolean(i));
//                    }
//                    rowData[i - 1] = rs.getString(i);
//                }
//                dataModel.addRow(rowData);
//            }
//            dataModel.c(visible);


        }
    }

}
