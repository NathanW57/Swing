import javax.swing.table.AbstractTableModel;


public class DefaultTable extends AbstractTableModel {
    private Object[][] data;
    private String columnName [];

    public DefaultTable(String[][] data, String[] columns) {
        this.data = data;
        this.columnName = columns;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
    public String getColumnName(int columnIndex){
        return columnName[columnIndex];
    }
}
