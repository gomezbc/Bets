package domain;

import domain.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserAdapter extends AbstractTableModel {
    private User user;
    private List<Bet> apuestas;

    public UserAdapter(User user) {
        this.user = user;
        this.apuestas=user.getBets();
    }

    @Override
    public int getRowCount() {
        return apuestas.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Bet bet = apuestas.get(rowIndex);
        switch (columnIndex){
            case 0: return bet.getForecast().getQuestion().getEvent().getDescription();
            case 1:return bet.getForecast().getQuestion().getQuestion();
            case 2:return bet.getForecast().getQuestion().getEvent().getEventDate();
            case 3:return bet.getBetMoney();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return super.getColumnName(column);
    }
}
