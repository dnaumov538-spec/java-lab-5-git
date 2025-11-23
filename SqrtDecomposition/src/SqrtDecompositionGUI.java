import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SqrtDecompositionGUI {
    private static SqrtDecomposition<Double> sqrtDecomposition;

    public static void main(String[] args) {
        // Запросить размер массива
        String sizeInput = JOptionPane.showInputDialog(null, "Введите размер массива:");
        int arraySize = Integer.parseInt(sizeInput);
        Double[] inputArray = new Double[arraySize];

        // Запросить элементы массива
        String elementsInput = JOptionPane.showInputDialog(null, "Введите " + arraySize + " элементов массива (через пробел):");
        String[] elements = elementsInput.split(" ");
        for (int i = 0; i < arraySize; i++) {
            inputArray[i] = Double.parseDouble(elements[i]);
        }

        // Создание объекта sqrt-декомпозиции
        sqrtDecomposition = new SqrtDecomposition<>(inputArray);

        // Создание GUI
        JFrame frame = new JFrame("Sqrt Decomposition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);  // Увеличили размер окна
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Отступы между компонентами

        // Текущий массив
        JTextArea arrayTextArea = new JTextArea(6, 35); // Увеличиваем высоту
        arrayTextArea.setText(sqrtDecomposition.arrayToString());
        arrayTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(arrayTextArea);
        gbc.gridwidth = 3;  // Расширяем на 3 колонки
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(scrollPane, gbc);

        // Кнопка для вычисления суммы
        JButton sumButton = new JButton("Вычислить сумму на интервале");
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(sumButton, gbc);

        JButton sumButton2 = new JButton("Другая простая кнопка");
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(sumButton, gbc);
        
        // Поля ввода для интервала для суммы
        JTextField sumStartField = new JTextField(5);
        JTextField sumEndField = new JTextField(5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(new JLabel("Начало интервала:"), gbc);
        gbc.gridx = 2;
        frame.add(sumStartField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(new JLabel("Конец интервала:"), gbc);
        gbc.gridx = 2;
        frame.add(sumEndField, gbc);

        // Поле для вывода суммы
        JTextArea sumResultArea = new JTextArea(1, 30);
        sumResultArea.setEditable(false);
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JScrollPane(sumResultArea), gbc);

        sumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int start = Integer.parseInt(sumStartField.getText());
                    int end = Integer.parseInt(sumEndField.getText());
                    Double sum = sqrtDecomposition.querySum(start, end);
                    sumResultArea.setText("Сумма на интервале: " + sum);
                } catch (Exception ex) {
                    sumResultArea.setText("Ошибка: " + ex.getMessage());
                }
            }
        });

        // Размещение кнопок "Изменить значение в позиции" и "Изменить значение на интервале"
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 10, 10));  // Добавляем отступы между кнопками
        JButton updateValueButton = new JButton("Изменить значение в позиции");
        JButton updateRangeButton = new JButton("Изменить значения на интервале");
        panel.add(updateValueButton);
        panel.add(updateRangeButton);

        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(panel, gbc);

        // Поля ввода для изменения значения в позиции
        JTextField valueIndexField = new JTextField(5);
        JTextField newValueField = new JTextField(5);
        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(new JLabel("Индекс для изменения:"), gbc);
        gbc.gridx = 2;
        frame.add(valueIndexField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        frame.add(new JLabel("Новое значение:"), gbc);
        gbc.gridx = 2;
        frame.add(newValueField, gbc);

        // Поля ввода для изменения значений на интервале
        JTextField rangeStartField = new JTextField(5);
        JTextField rangeEndField = new JTextField(5);
        JTextField incrementField = new JTextField(5);
        gbc.gridx = 0;
        gbc.gridy = 7;
        frame.add(new JLabel("Начало интервала:"), gbc);
        gbc.gridx = 2;
        frame.add(rangeStartField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        frame.add(new JLabel("Конец интервала:"), gbc);
        gbc.gridx = 2;
        frame.add(rangeEndField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 9;
        frame.add(new JLabel("Прибавить значение:"), gbc);
        gbc.gridx = 2;
        frame.add(incrementField, gbc);

        updateValueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = Integer.parseInt(valueIndexField.getText());
                    double newValue = Double.parseDouble(newValueField.getText());
                    sqrtDecomposition.updateValue(index, newValue);
                    arrayTextArea.setText(sqrtDecomposition.arrayToString());
                } catch (Exception ex) {
                    arrayTextArea.setText("Ошибка: " + ex.getMessage());
                }
            }
        });

        updateRangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int start = Integer.parseInt(rangeStartField.getText());
                    int end = Integer.parseInt(rangeEndField.getText());
                    double increment = Double.parseDouble(incrementField.getText());
                    sqrtDecomposition.updateRange(start, end, increment);
                    arrayTextArea.setText(sqrtDecomposition.arrayToString());
                } catch (Exception ex) {
                    arrayTextArea.setText("Ошибка: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
