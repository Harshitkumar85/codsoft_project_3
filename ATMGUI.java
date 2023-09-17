import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            account.deposit(amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= account.getBalance()) {
            account.withdraw(amount);
            return true;
        }
        return false;
    }

    public double checkBalance() {
        return account.getBalance();
    }
}

public class ATMGUI extends JFrame {
    private ATM atm;

    public ATMGUI() {
        BankAccount account = new BankAccount(1000.0); // Initial balance
        atm = new ATM(account);

        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton checkBalanceButton = new JButton("Check Balance");

        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(checkBalanceButton);

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog("Enter deposit amount:");
                try {
                    double amount = Double.parseDouble(amountStr);
                    atm.deposit(amount);
                    showMessage("Deposit successful. New balance: $" + atm.checkBalance());
                } catch (NumberFormatException ex) {
                    showMessage("Invalid input. Please enter a valid amount.");
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog("Enter withdrawal amount:");
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (atm.withdraw(amount)) {
                        showMessage("Withdrawal successful. New balance: $" + atm.checkBalance());
                    } else {
                        showMessage("Insufficient funds or invalid amount.");
                    }
                } catch (NumberFormatException ex) {
                    showMessage("Invalid input. Please enter a valid amount.");
                }
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMessage("Your account balance: $" + atm.checkBalance());
            }
        });

        add(panel);
        setVisible(true);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ATMGUI();
        });
    }
}
