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
                newItem, openItem, saveItem, exitItem,
                searchItem, selectItem, copyItem, pasteItem,
                cutItem, timeItem, aboutItem, printItem
        };

        for (JMenuItem item : items) {
            item.addActionListener(this);
        }

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        searchMenu.add(searchItem);

        viewMenu.add(selectItem);

        manageMenu.add(copyItem);
        manageMenu.add(pasteItem);
        manageMenu.add(cutItem);
        manageMenu.add(timeItem);

        helpMenu.add(aboutItem);
        helpMenu.add(printItem);

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

        JComponent source = (JComponent) event.getSource();

        if (source.equals(openItem)) {
            openItemAction();
        } else if (source.equals(newItem)) {
            new Main();
        } else if (source.equals(saveItem)) {
            saveItemAction();
        } else if (source.equals(exitItem)) {
            System.exit(0);
        } else if (source.equals(printItem)) {
            printItemAction();
        } else if (source.equals(aboutItem)) {
            aboutItemAction();
        } else if (source.equals(searchItem)) {
            searchItemAction();
        } else if (source.equals(selectItem)) {
            textArea.selectAll();
        } else if (source.equals(copyItem)) {
            textArea.copy();
        } else if (source.equals(pasteItem)) {
            textArea.paste();
        } else if (source.equals(cutItem)) {
            textArea.cut();
        } else if (source.equals(timeItem)) {
            timeItemAction();
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
    private void openItemAction(){}

    private void printItemAction(){}

    private void saveItemAction() {}

    private void searchItemAction(){}

    private void timeItemAction(){
        LocalDateTime TnD = LocalDateTime.now();// cant use &
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        textArea.append(TnD.format(timeFormat) + " ");
    }
}