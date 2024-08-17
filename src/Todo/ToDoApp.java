package Todo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ToDoApp extends JFrame {
    private final DefaultListModel<String> listModel;
    private final JList<String> taskList;
    private final JTextField taskField;
    private final JButton addButton, removeButton, markDoneButton;

    private static final String FILE_NAME = "tasks.txt";

    public ToDoApp() {
        setTitle("To-Do List");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        taskField = new JTextField(20);
        panel.add(taskField);

        addButton = new JButton("Add Task");
        addButton.addActionListener(new AddTaskAction());
        panel.add(addButton);

        removeButton = new JButton("Remove Task");
        removeButton.addActionListener(new RemoveTaskAction());
        panel.add(removeButton);

        markDoneButton = new JButton("Mark as done");
        markDoneButton.addActionListener(new MarkDoneAction());
        panel.add(markDoneButton);

        add(panel, BorderLayout.SOUTH);

        loadTasks();
    }

    private void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listModel.addElement(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < listModel.size(); i++) {
                writer.write(listModel.getElementAt(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class AddTaskAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String task = taskField.getText();
            if(!task.isEmpty()) {
                listModel.addElement(task);
                taskField.setText("");
                saveTasks();
            }
        }
    }

    public class RemoveTaskAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = taskList.getSelectedIndex();
            if(selectedIndex != -1) {
                listModel.remove(selectedIndex);
                saveTasks();
            }
        }
    }

    public class MarkDoneAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                String task = listModel.getElementAt(selectedIndex);
                listModel.set(selectedIndex, task + " (Done)");
                saveTasks();
            }
        }
    }
}
