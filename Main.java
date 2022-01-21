import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        menu();
    }

    // designing menu; options: random start, start from file, score table
    public static void menu() throws Exception{
        JFrame frame = new JFrame("Menu");
        JButton randomStart = new JButton("Random Start");
        randomStart.setBounds(20,0,150,30);
        JButton startFromFile = new JButton("Start from File");
        startFromFile.setBounds(20,30,150,30);
        JButton scoreTable = new JButton("Score Table");
        scoreTable.setBounds(20,60,150,30);
        frame.add(randomStart);
        frame.add(startFromFile);
        frame.add(scoreTable);
        frame.setSize(200,130);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        randomStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                try {
                    randomStart();
                } catch (Exception ex) {

                }
            }
        });

        startFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                try {
                    startFromFile();
                } catch (Exception ex) {

                }
            }
        });

        scoreTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                try {
                    scoreTable();
                } catch (Exception ex) {

                }
            }
        });
    }

    // starting game using a randomly arranged board
    public static void randomStart() throws Exception{
        String[][] mainTable = new String[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                mainTable[i][j] = randomGenerator();
            }
        }
        DragPanel dragPanel = new DragPanel(mainTable);
    }

    // starting game using a given csv file
    public static void startFromFile() throws Exception {
        String[][] mainTable = new String[10][10];
        Scanner scnr = new Scanner(new File("src/test.csv"));
        int score = scnr.nextInt();
        scnr.nextLine();
        for (int i = 0; i < 10; i++){
            String s = scnr.nextLine();
            int j = 0;
            for (String str: s.split(",")){
                mainTable[j][i] = str;
                j++;
            }
        }
        scnr.close();
        DragPanel dragPanel = new DragPanel(mainTable, score);
    }

    // designing score table and saving and uploading from scores.csv
    public static void scoreTable() throws Exception {
        JFrame f;
        f = new JFrame();
        File path = new File("src/scores.csv");
        Scanner scnr = new Scanner(path);
        String[][] data = new String[5][2];
        int i = 0;
        while (scnr.hasNextLine()){
            String line = scnr.nextLine();
            String name = line.split(",")[0];
            String hiScore = line.split(",")[1];
            data[i][0] = name;
            data[i][1] = hiScore;
            i++;
        }
        scnr.close();
        String column[] = {"Username", "Score"};
        JTable jt = new JTable(data, column);
        JButton rtrn = new JButton("return");
        rtrn.setBounds(50, 100, 100, 30);
        Container contentPane = f.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(jt), BorderLayout.CENTER);
        contentPane.add(rtrn, BorderLayout.SOUTH);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jt.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jt.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        f.setSize(300, 166);
        f.setVisible(true);
        rtrn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    menu();
                } catch (Exception exception) {

                }
                f.setVisible(false);
                f.dispose();
            }
        });
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // generating one of SCR, SCG, SCB, SCY randomly
    public static String randomGenerator() throws Exception{
        Random rnd = new Random();
        int candyNum = rnd.nextInt(4);
        if(candyNum == 0)
            return "SCR";
        if(candyNum == 1)
            return "SCG";
        if(candyNum == 2)
            return "SCB";
        return "SCY";
    }
}
