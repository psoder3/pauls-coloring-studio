/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paulscoloringstudio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

class AboutDialog extends JDialog implements ActionListener {
  public AboutDialog(JFrame parent, String title, String message) {
    super(parent, title, true);
    JPanel messagePane = new JPanel();
    messagePane.add(new JLabel(message));
    getContentPane().add(messagePane);
    JPanel buttonPane = new JPanel();
    JButton button = new JButton("OK"); 
    buttonPane.add(button); 
    button.addActionListener(this);
    getContentPane().add(buttonPane, BorderLayout.SOUTH);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.getRootPane().setDefaultButton(button);

    
    pack(); 
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double x = (screenSize.getWidth()/2) - (getWidth()/2);
    double y = (screenSize.getHeight()/2) - (getHeight()/2);
    this.setLocation((int)x, (int)y);
    setVisible(true);
    this.requestFocus();
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    setVisible(false); 
    dispose(); 
  }
}