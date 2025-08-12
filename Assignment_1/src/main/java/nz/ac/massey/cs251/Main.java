package nz.ac.massey.cs251;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;

import java.io.*;
import java.nio.file.Files;
import org.apache.tika.Tika;
import java.nio.charset.StandardCharsets;

import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.BadLocationException;


public class Main extends JFrame implements ActionListener {

    JMenuBar menuBar = new JMenuBar();
    RSyntaxTextArea textArea = new RSyntaxTextArea();


    //----------------------------------------JMenu------------------------------------------------
    JMenu fileMenu = new JMenu("File");
    JMenu searchMenu = new JMenu("Search");
    JMenu viewMenu = new JMenu("View");
    JMenu manageMenu = new JMenu("Manage");
    JMenu helpMenu = new JMenu("Help");

    //----------------------------------------JMenuItems-------------------------------------------

    JMenuItem newItem = new JMenuItem("New");
    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem exitItem = new JMenuItem("Exit");
    JMenuItem searchItem = new JMenuItem("Search");
    JMenuItem selectItem = new JMenuItem("Select");
    JMenuItem copyItem = new JMenuItem("Copy");
    JMenuItem pasteItem = new JMenuItem("Paste");
    JMenuItem cutItem = new JMenuItem("Cut");
    JMenuItem timeItem = new JMenuItem("Time");
    JMenuItem aboutItem = new JMenuItem("About");
    JMenuItem printItem = new JMenuItem("Print");


    //----------------------------------------main-------------------------------------------------
    public static void main(String[] args) { new Main(); }


    public Main() {
        super("Text Editor");
        initMenu();
        initText();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);
        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    //----------------------------------------init------------------------------------------------
    public void initMenu() {
        // Add menu items

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        menuBar.add(viewMenu);
        menuBar.add(manageMenu);
        menuBar.add(helpMenu);

        JMenuItem[] items = {
                newItem, openItem, saveItem, printItem, exitItem,
                searchItem, selectItem, copyItem, pasteItem,
                cutItem, timeItem, aboutItem
        };

        for (JMenuItem item : items) { item.addActionListener(this); }
        for (int i=0;i<5;i++){ fileMenu.add(items[i]); }
        for (int i=7;i<11;i++){ manageMenu.add(items[i]); }

        searchMenu.add(searchItem);
        viewMenu.add(selectItem);
        helpMenu.add(aboutItem);
    }

    public void initText() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textArea);
        textPanel.add(new JScrollPane(textArea));
        textPanel.setVisible(true);
        this.add(textPanel);
    }

    //-------------------------------------action listener----------------------------------------
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        switch (command) {
            case "New" -> new Main();
            case "Open" -> openItemAction();
            case "Save" -> saveItemAction();
            case "Print" -> printItemAction();
            case "Exit" -> System.exit(0);
            case "Search" -> searchItemAction();
            case "Select" -> textArea.selectAll();
            case "Copy" -> textArea.copy();
            case "Paste" -> textArea.paste();
            case "Cut" -> textArea.cut();
            case "About" -> aboutItemAction();
            case "Time" -> timeItemAction();
            default -> System.err.println("Error, that button doesn't exist. " + command);

        }
    }
    //----------------------------------------actions----------------------------------------------


    private void aboutItemAction(){
        JOptionPane.showMessageDialog( this, """
            This is our simple text editor powered by Java.
            optional extras include:
            
            Opening files (.txt, .odt, .rtf),
            Saving files (standard and PDF),
            Syntax highlighting for source code (.java, .py, etc.)
            
            Created by Junyeong and Willow.
            Version 0.1.1
            """);// lol@version
    }

    private void openItemAction(){
        JFileChooser file_chooser = new JFileChooser();
        if (file_chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = file_chooser.getSelectedFile();
        try {
            String content;
            String lower = file.getName().toLowerCase();
            if (lower.endsWith(".odt")) { content = new Tika().parseToString(file); }
            else { content = Files.readString(file.toPath()); } // read the whole .txt file
            textArea.setText(content);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "*File Open Error*" + e.getMessage());
        }
    }

    private void printItemAction(){
        try {
            boolean flag = textArea.print();
            if (!flag) {
                JOptionPane.showMessageDialog(this, "Printing cancelled.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Print Error: " + e.getMessage());
        }
    }

    private void saveItemAction() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File file = fileChooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".txt")) { // <- make sure it is saved as txt file !
            file = new File(file.getParentFile(), file.getName() + ".txt");
        }
        try { //add message dialog + file overwrite message! <- Junyeong's task.
            Files.writeString(
                    file.toPath(),
                    textArea.getText(),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "*File Save Error* " + e.getMessage());
        }
    }

    private void searchItemAction(){
        String word = JOptionPane.showInputDialog(this, "Enter word to find(highlight):");
        if (word == null || word.isBlank()) return;

        Highlighter highlighter = textArea.getHighlighter();
        highlighter.removeAllHighlights(); // remove previous highlighted
        Highlighter.HighlightPainter painter =  new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW); //

        String text = textArea.getText();
        int indx = text.indexOf(word);
        while (indx >= 0) {
            try {
                highlighter.addHighlight(indx, indx + word.length(), painter);
                indx = text.indexOf(word, indx + word.length());
            }
                catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    private void timeItemAction(){
        LocalDateTime TnD = LocalDateTime.now();// cant use &
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        textArea.append(TnD.format(timeFormat) + " ");
    }
}