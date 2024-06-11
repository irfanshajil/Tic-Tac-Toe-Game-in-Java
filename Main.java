import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JPanel reset_panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton reset_button = new JButton();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;
    int count = 0;

    TicTacToe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 1000);
        frame.getContentPane().setBackground(new Color(60, 60, 60));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfield.setBackground(new Color(60, 60, 60));
        textfield.setForeground(new Color(200, 200, 200));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        reset_panel.setLayout(new BorderLayout());
        reset_panel.setBounds(0, 800, 800, 100);

        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(new Color(80, 80, 80));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].setBackground(new Color(100, 100, 100));
            buttons[i].setBorder(BorderFactory.createBevelBorder(1, new Color(120, 120, 120), new Color(100, 100, 100),
                    new Color(80, 80, 80), new Color(60, 60, 60)));
            buttons[i].addActionListener(this);
        }

        reset_button.setBackground(new Color(60, 60, 60));
        reset_button.setForeground(new Color(200, 200, 200));
        reset_button.setFont(new Font("Ink Free", Font.BOLD, 75));
        reset_button.setHorizontalAlignment(JLabel.CENTER);
        reset_button.setText("New Game");
        reset_button.addActionListener(this);
        reset_button.setFocusable(false);

        title_panel.add(textfield);
        reset_panel.add(reset_button);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(reset_panel, BorderLayout.SOUTH);
        frame.add(button_panel);

        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reset_button) {
            reset();
        } else {
            for (int i = 0; i < 9; i++) {
                if (e.getSource() == buttons[i]) {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setText(player1_turn ? "X" : "O");
                        buttons[i].setForeground(player1_turn ? new Color(200, 200, 200) : new Color(150, 150, 150));

                        player1_turn = !player1_turn;
                        textfield.setText(player1_turn ? "X turn" : "O turn");
                        count++;
                        check();
                    }
                }
            }
        }
    }

    public void firstTurn() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        player1_turn = random.nextInt(2) == 0;
        textfield.setText(player1_turn ? "X turn" : "O turn");
    }

    public void check() {
        // Check X win conditions
        if (checkWinCondition("X"))
            return;
        // Check O win conditions
        if (checkWinCondition("O"))
            return;
        // Check draw condition
        if (count == 9) {
            drawMatch();
        }
    }

    private boolean checkWinCondition(String player) {
        int[][] winConditions = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // rows
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // columns
                { 0, 4, 8 }, { 2, 4, 6 } // diagonals
        };

        for (int[] winCondition : winConditions) {
            if (buttons[winCondition[0]].getText().equals(player) &&
                    buttons[winCondition[1]].getText().equals(player) &&
                    buttons[winCondition[2]].getText().equals(player)) {
                if (player.equals("X")) {
                    xWins(winCondition[0], winCondition[1], winCondition[2]);
                } else {
                    oWins(winCondition[0], winCondition[1], winCondition[2]);
                }
                return true;
            }
        }
        return false;
    }

    public void xWins(int a, int b, int c) {
        buttons[a].setBackground(new Color(120, 120, 120));
        buttons[b].setBackground(new Color(120, 120, 120));
        buttons[c].setBackground(new Color(120, 120, 120));
        disableButtons();
        textfield.setText("X wins");
    }

    public void oWins(int a, int b, int c) {
        buttons[a].setBackground(new Color(120, 120, 120));
        buttons[b].setBackground(new Color(120, 120, 120));
        buttons[c].setBackground(new Color(120, 120, 120));
        disableButtons();
        textfield.setText("O wins");
    }

    public void drawMatch() {
        disableButtons();
        textfield.setText("Draw");
    }

    private void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    public void reset() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(new Color(100, 100, 100));
        }
        firstTurn();
        count = 0;
    }
}

public class Main {
    public static void main(String[] args) {
        new TicTacToe();
    }
}
