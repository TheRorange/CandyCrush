import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class DragPanel extends JPanel implements structure{
    Map<String, ImageIcon> pics = new HashMap<>();
    private static ImageIcon scr = new ImageIcon("src/resources/SCR.png");
    private static ImageIcon scg = new ImageIcon("src/resources/SCG.png");
    private static ImageIcon scb = new ImageIcon("src/resources/SCB.png");
    private static ImageIcon scy = new ImageIcon("src/resources/SCY.png");
    private static ImageIcon lrr = new ImageIcon("src/resources/LRR.png");
    private static ImageIcon lrg = new ImageIcon("src/resources/LRG.png");
    private static ImageIcon lrb = new ImageIcon("src/resources/LRB.png");
    private static ImageIcon lry = new ImageIcon("src/resources/LRY.png");
    private static ImageIcon lcr = new ImageIcon("src/resources/LCR.png");
    private static ImageIcon lcg = new ImageIcon("src/resources/LCG.png");
    private static ImageIcon lcb = new ImageIcon("src/resources/LCB.png");
    private static ImageIcon lcy = new ImageIcon("src/resources/LCY.png");
    private static ImageIcon rcr = new ImageIcon("src/resources/RCR.png");
    private static ImageIcon rcg = new ImageIcon("src/resources/RCG.png");
    private static ImageIcon rcb = new ImageIcon("src/resources/RCB.png");
    private static ImageIcon rcy = new ImageIcon("src/resources/RCY.png");
    final int width = lrg.getIconWidth();
    final int height = lrg.getIconHeight();
    static int chosenX, chosenY, chosen2X, chosen2Y, score = 0;
    static String pName;
    Point[][] imageCorner = new Point[10][10];
    private static String[][] mainTable = new String[10][10];
    static Game game;
    JLabel jlabel;
    static JFrame jFrame;
    Point prevPt, currentPt;
    boolean cntnue = false;

    DragPanel(String[][] mainTable) throws Exception {
        personName();
        this.setBackground(new Color(246, 246, 246));
        picsSetter(pics);
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                imageCorner[i][j] = new Point(i * width, j * height);
            }
        }
        game = new Game(mainTable);
        this.mainTable = game.getMainTable();
        jlabel = new JLabel("Score: " + game.score);
        jlabel.setBounds(730, 10, 150, 20);
        jlabel.setFont(new Font("Verdana", Font.BOLD, 16));
        this.add(jlabel);
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);
        this.setLayout(null);
    }

    DragPanel(String[][] mainTable, int score) throws Exception {
        personName();
        this.setBackground(new Color(246, 246, 246));
        picsSetter(pics);
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                imageCorner[i][j] = new Point(i * width, j * height);
            }
        }
        game = new Game(mainTable);
        // setting score to the one in the file
        game.score = score;
        this.score = score;
        this.mainTable = game.getMainTable();
        jlabel = new JLabel("Score: " + game.score);
        jlabel.setBounds(730, 10, 150, 20);
        jlabel.setFont(new Font("Verdana", Font.BOLD, 16));
        this.add(jlabel);
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);
        this.setLayout(null);
    }

    public String[][] getMainTable() throws Exception{
        return mainTable;
    }

    // saving username using JTextField to use in score table
    public void personName() throws Exception{
        JFrame loginFrame = new JFrame();
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setPreferredSize(new Dimension(300, 120));

        Container pane = loginFrame.getContentPane();

        JTextField usernameTF = new JTextField();

        JLabel usernameLbl = new JLabel("Username: ");

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pName = usernameTF.getText();
                loginFrame.setVisible(false);
                try {
                    frameCall();
                } catch (Exception ex) {

                }
            }
        });

        pane.add(usernameLbl);
        pane.add(usernameTF);
        pane.add(loginBtn);

        /*View Setup with layout*/
        SpringLayout layout = new SpringLayout();
        pane.setLayout(layout);

        layout.putConstraint(SpringLayout.NORTH, usernameLbl, 10, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, usernameLbl, 5, SpringLayout.WEST, pane);

        layout.putConstraint(SpringLayout.NORTH, usernameTF, 10, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, usernameTF, 15, SpringLayout.EAST, usernameLbl);
        layout.putConstraint(SpringLayout.EAST, usernameTF, -5, SpringLayout.EAST, pane);

        layout.putConstraint(SpringLayout.VERTICAL_CENTER, usernameLbl, 0, SpringLayout.VERTICAL_CENTER, usernameTF);
        layout.putConstraint(SpringLayout.EAST, loginBtn, -25, SpringLayout.EAST, pane);
        layout.putConstraint(SpringLayout.WEST, loginBtn, 25, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.SOUTH, loginBtn, -10, SpringLayout.SOUTH, pane);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginBtn, 0, SpringLayout.HORIZONTAL_CENTER, pane);

        loginFrame.setResizable(false);

        loginFrame.pack();
        loginFrame.setVisible(true);
    }

    public void frameCall() throws Exception {
        frame(this);
    }

    // designing main panel of the game, which consists of: game board, score, hint, return
    public static void frame(JPanel jPanel) throws Exception{
        jFrame = new JFrame();
        Container pane = jFrame.getContentPane();
        jFrame.setTitle("Hope U Enjoy!");
        jFrame.setSize(900, 760);
        JButton rtrn = new JButton("return");
        JButton hint = new JButton("hint");
        pane.add(rtrn);
        pane.add(hint);
        pane.add(jPanel);
        SpringLayout layout = new SpringLayout();
        pane.setLayout(layout);

        layout.putConstraint(SpringLayout.NORTH, jPanel, 2, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.SOUTH, jPanel, 5, SpringLayout.SOUTH, pane);
        layout.putConstraint(SpringLayout.EAST, jPanel, -20, SpringLayout.EAST, pane);
        layout.putConstraint(SpringLayout.WEST, jPanel, 5, SpringLayout.WEST, pane);

        layout.putConstraint(SpringLayout.SOUTH, rtrn, -20, SpringLayout.SOUTH, pane);
        layout.putConstraint(SpringLayout.EAST, rtrn, -60, SpringLayout.EAST, pane);

        layout.putConstraint(SpringLayout.SOUTH, hint, -50, SpringLayout.SOUTH, pane);
        layout.putConstraint(SpringLayout.EAST, hint, -60, SpringLayout.EAST, pane);
        layout.putConstraint(SpringLayout.WEST, hint, 757, SpringLayout.WEST, pane);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                try {
                    saveScore();
                } catch (Exception ex) {

                }
                e.getWindow().dispose();
            }
        });
        jFrame.setVisible(true);
        rtrn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveScore();
                } catch (Exception ex) {

                }
                jFrame.setVisible(false);
                jFrame.dispose();
                try {
                    reset();
                } catch (Exception ex) {

                }
                score = 0;
                try {
                    menu();
                } catch (Exception ex) {

                }
            }
        });

        hint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean helped = false;
                // checking 4 directions of each candy til we find a swap option
                for (int i1 = 0; !helped && i1 < 10; i1++)
                    for (int j1 = 0; !helped && j1 < 10; j1++) {
                        if (!helped && i1 + 1 < 10) {
                            try {
                                helped = hintSwap(i1, 1, j1, 0);
                            } catch (Exception exception) {

                            }
                        }
                        if (!helped && j1 + 1 < 10) {
                            try {
                                helped = hintSwap(i1, 0, j1, 1);
                            } catch (Exception exception) {

                            }
                        }
                        if (!helped && i1 - 1 > 0) {
                            try {
                                helped = hintSwap(i1, -1, j1, 0);
                            } catch (Exception exception) {

                            }
                        }
                        if (!helped && j1 - 1 > 0) {
                            try {
                                helped = hintSwap(i1, 0, j1, -1);
                            } catch (Exception exception) {

                            }
                        }
                    }
            }
        });
    }

    // checking whether is it possible for 2 candies to be swapped or not. if it wasn't possible, and we reached the last candy, the loser message will appear
    public static boolean hintSwap(int x, int changeX, int y, int changeY) throws Exception{
        String temp = mainTable[x][y];
        mainTable[x][y] = mainTable[x + changeX][y + changeY];
        mainTable[x + changeX][y + changeY] = temp;
        Game test = null;
        try {
            test = new Game(mainTable, 1);
        } catch (FileNotFoundException ex) {

        }
        if (test.hintFlag == 1) {
            temp = mainTable[x][y];
            mainTable[x][y] = mainTable[x + changeX][y + changeY];
            mainTable[x + changeX][y + changeY] = temp;
            hintMessage(x, y, x + changeX, y + changeY);
            return true;
        }
        else if (x == 9 && y == 9 && changeY == -1)
            loserMessage();
        // if it's not possible, the swap will be back to the previous form
        temp = mainTable[x][y];
        mainTable[x][y] = mainTable[x + changeX][y + changeY];
        mainTable[x + changeX][y + changeY] = temp;
        return false;
    }

    // telling the locations of candies that can be swapped in a hint message
    public static void hintMessage(int i1, int j1, int i2, int j2) throws Exception{
        JFrame frame = new JFrame("hint");
        JLabel label1 = new JLabel("You Can Swap Candy at (" + (j1 + 1) + ", " + (i1 + 1) + ") with Candy at (" + (j2 + 1) + ", " + (i2 + 1) + ").");
        label1.setBounds(18, 15, 320, 15);
        frame.setSize(330, 130);
        JButton okBtn = new JButton("ok");
        okBtn.setBounds(100,50,100,25);
        frame.add(okBtn);
        frame.add(label1);
        frame.setLayout(null);
        frame.setVisible(true);

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    // showing loser message if there is no candy to be swapped
    public static void loserMessage() throws Exception{
        JFrame frame = new JFrame("Loser!");
        JLabel label1 = new JLabel("Game Over!");
        label1.setBounds(91, 15, 100, 15);
        label1.setFont(new Font("Verdana", Font.BOLD, 14));
        frame.setSize(295, 130);
        JButton menuBtn = new JButton("menu");
        menuBtn.setBounds(89,50,100,30);
        frame.add(menuBtn);
        frame.add(label1);
        frame.setLayout(null);
        frame.setVisible(true);

        menuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveScore();
                } catch (Exception ex) {

                }
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                jFrame.setVisible(false);
                jFrame.dispose();
                try {
                    reset();
                } catch (Exception ex) {

                }
                score = 0;
                try {
                    menu();
                } catch (Exception ex) {

                }
            }
        });
    }

    // reset the board after clicking the return button
    public static void reset() throws Exception{
        game.chosenX = -1;
        game.chosenY = -1;
        game.chosen2X = -1;
        game.chosen2Y = -1;
    }

    //showing menu after clicking the return button
    public static void menu() throws Exception{
        (new Main()).menu();
    }

    // mapping pictures to icons at addresses
    public void picsSetter (Map<String, ImageIcon> pics) throws Exception{
        pics.put("SCR", scr);
        pics.put("SCG", scg);
        pics.put("SCB", scb);
        pics.put("SCY", scy);
        pics.put("LRR", lrr);
        pics.put("LRG", lrg);
        pics.put("LRB", lrb);
        pics.put("LRY", lry);
        pics.put("LCR", lcr);
        pics.put("LCG", lcg);
        pics.put("LCB", lcb);
        pics.put("LCY", lcy);
        pics.put("RCR", rcr);
        pics.put("RCG", rcg);
        pics.put("RCB", rcb);
        pics.put("RCY", rcy);
    }

    // painting each candy in its place
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++)
                pics.get(mainTable[i][j]).paintIcon(this, g, (int) imageCorner[i][j].getX(), (int) imageCorner[i][j].getY());
    }

    // swapping two candies at the table after mouse pressed and released
    public void swap() throws Exception {
        if (((Math.abs(chosen2X - chosenX) == 1) && (chosen2Y == chosenY)) ||
                ((Math.abs(chosen2Y - chosenY) == 1) && (chosen2X == chosenX))){
            String temp = mainTable[chosenX][chosenY];
            mainTable[chosenX][chosenY] = mainTable[chosen2X][chosen2Y];
            mainTable[chosen2X][chosen2Y] = temp;
            Game game = new Game(mainTable, chosenX, chosenY, chosen2X, chosen2Y);
            if (game.flag == 1) {
                // if 2 candies can be swapped, the panel will be repainted
                jFrame.repaint();
                score += game.score;
                jlabel.setText(" Score: " + score);
                jlabel.setFont(new Font("Verdana", Font.BOLD, 16));
                this.add(jlabel, BorderLayout.EAST);
                if (score >= 1500 && !cntnue){
                    cntnue = true;
                    winnerMessage();
                }
            }
            else {
                // if it's not possible, the swap will be back to the previous form
                temp = mainTable[chosenX][chosenY];
                mainTable[chosenX][chosenY] = mainTable[chosen2X][chosen2Y];
                mainTable[chosen2X][chosen2Y] = temp;
            }
        }
    }

    // saving score to use in score table
    public static void saveScore() throws Exception {
        File path = new File("src/scores.csv");
        Scanner scnr = new Scanner(path);
        String[] names = new String[5];
        int[] scores = new int[5];
        int i = 0;
        while (scnr.hasNextLine()){
            String line = scnr.nextLine();
            String name = line.split(",")[0];
            int hiScore = Integer.parseInt(line.split(",")[1]);
            scores[i] = hiScore;
            names[i] = name;
            i++;
        }
        scnr.close();
        boolean check = false;
        for (int j = 0; j < scores.length; j++)
            if (!check && scores[j] < score){
                for (int k = 4; k > j; k--) {
                    scores[k] = scores[k - 1];
                    names[k] = names[k - 1];
                }
                scores[j] = score;
                names[j] = pName;
                check = true;
            }
        PrintStream out = new PrintStream(path);
        for (int j = 0; j < scores.length; j++)
            if (scores[j] > 0)
                out.println(names[j] + "," + scores[j]);
        out.close();
    }

    // showing winner message after reaching 1500 score and showing options: continue, menu
    public void winnerMessage() throws Exception{
        JFrame frame = new JFrame("Winner!");
        JLabel label1 = new JLabel("Well Done! Now which do U choose?");
        label1.setBounds(22, 15, 230, 10);
        frame.setSize(260, 130);
        JButton cntnuBtn = new JButton("continue");
        cntnuBtn.setBounds(13,40,100,30);
        JButton menuBtn = new JButton("menu");
        menuBtn.setBounds(135,40,100,30);
        frame.add(cntnuBtn);
        frame.add(menuBtn);
        frame.add(label1);
        frame.setLayout(null);
        frame.setVisible(true);

        cntnuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        menuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveScore();
                } catch (Exception ex) {

                }
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                jFrame.setVisible(false);
                jFrame.dispose();
                try {
                    reset();
                } catch (Exception ex) {

                }
                score = 0;
                try {
                    menu();
                } catch (Exception ex) {

                }
            }
        });
    }

    private class ClickListener extends MouseAdapter{

        public void mousePressed(MouseEvent e){
            prevPt = e.getPoint();
            // checking if mouse pressed and released are 2 candies side by side
            for(int i = 0; i < 10; i++)
                for(int j = 0; j < 10; j++)
                    if(e.getPoint().getX() > imageCorner[i][j].getX() && e.getPoint().getY() > imageCorner[i][j].getY() &&
                       e.getPoint().getX() < (imageCorner[i][j].getX() + width) &&
                       e.getPoint().getY() < (imageCorner[i][j].getY() + height)){
                        chosenX = i;
                        chosenY = j;
                    }
        }

        public void mouseReleased(MouseEvent e){
            currentPt = e.getPoint();
            for(int i = 0; i < 10; i++)
                for(int j = 0; j < 10; j++)
                    if(e.getPoint().getX() > imageCorner[i][j].getX() && e.getPoint().getY() > imageCorner[i][j].getY() &&
                       e.getPoint().getX() < (imageCorner[i][j].getX() + width) &&
                       e.getPoint().getY() < (imageCorner[i][j].getY() + height)){
                        chosen2X = i;
                        chosen2Y = j;
                        try {
                            swap();
                        } catch (Exception ex) {

                        }
                    }
        }
    }
}