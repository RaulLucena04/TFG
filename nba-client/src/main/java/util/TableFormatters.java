package util;

import java.util.Locale;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

/**
 * Formato coherente de celdas en {@link javafx.scene.control.TableView}.
 */
public final class TableFormatters {

    private TableFormatters() {
    }

    /**
     * Muestra un decimal fijo, evitando artefactos de coma flotante (p. ej. 20.599999999999998).
     */
    public static <S> void oneDecimalCell(TableColumn<S, Double> column) {
        column.setCellFactory(col -> new TableCell<S, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.format(Locale.US, "%.1f", value));
                }
            }
        });
    }
}
