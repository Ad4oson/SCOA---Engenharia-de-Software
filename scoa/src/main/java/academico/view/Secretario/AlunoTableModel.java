/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academico.view.Secretario;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Windows 11
 */
public class AlunoTableModel extends DefaultTableModel {

    public AlunoTableModel() {
        super(new String[]{"Disciplina"}, 0);
        addEmptyRow();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true; // todas as linhas podem ser editadas
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        super.setValueAt(value, row, col);

        // Se o usuário editou a última linha, cria mais uma vazia
        if (row == getRowCount() - 1 && value != null && !value.toString().isBlank()) {
            addEmptyRow();
        }
    }

    private void addEmptyRow() {
        super.addRow(new Object[]{""});
    }
}
