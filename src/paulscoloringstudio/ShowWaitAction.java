/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paulscoloringstudio;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author paulsoderquist
 */
public class ShowWaitAction extends AbstractAction {
   PaulsColoringStudio coloringStudio;
   ActionListener a;
   String title;
   String message;
   public JPanel messagePanel;
   public JLabel messageLabel;
   public JProgressBar progressBar;
   
   public ShowWaitAction(String name, String dialogTitle, String dialogMessage, PaulsColoringStudio pColoringStudio, ActionListener a) {
      super(name);
      this.coloringStudio = pColoringStudio;
      this.a = a;
      this.title = dialogTitle;
      this.message = dialogMessage;
      progressBar = new JProgressBar();
   }
   
   public ShowWaitAction(String name, String dialogTitle, String dialogMessage, PaulsColoringStudio pColoringStudio) {
      super(name);
      this.coloringStudio = pColoringStudio;
      this.title = dialogTitle;
      this.message = dialogMessage;
      progressBar = new JProgressBar();
   }

   public void setWaitAction(ActionListener a)
   {
       this.a = a;
   }
   
   @Override
   public void actionPerformed(ActionEvent evt) {
       System.out.println("Wait Action actionPerformed 1");
      SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>(){
         @Override
         protected Void doInBackground() throws Exception {
                   System.out.println("Wait Action actionPerformed doInBackground 1");
            a.actionPerformed(new ActionEvent(ShowWaitAction.this.coloringStudio, ActionEvent.ACTION_PERFORMED, null) {});
                   System.out.println("Wait Action actionPerformed doInBackground 2");

            return null;
         }
      };
       System.out.println("Wait Action actionPerformed 2");

      final JDialog dialog = new JDialog(this.coloringStudio.frame, title, ModalityType.APPLICATION_MODAL);
       System.out.println("Wait Action actionPerformed 3");
      dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
       System.out.println("Wait Action actionPerformed 4");

      mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {

         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("state")) {
                System.out.println("Wait Action actionPerformed propertyChange 1");

                if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                   System.out.println("Wait Action actionPerformed propertyChange 2");
                   
                  dialog.dispose();
                    System.out.println("Wait Action actionPerformed propertyChange 3");
               }
            }
         }
      });
       System.out.println("Wait Action actionPerformed 5");
      mySwingWorker.execute();
       System.out.println("Wait Action actionPerformed 6");
      
      progressBar.setIndeterminate(true);
       System.out.println("Wait Action actionPerformed 7");
      messagePanel = new JPanel(new BorderLayout());
       System.out.println("Wait Action actionPerformed 8");
      messagePanel.add(progressBar, BorderLayout.CENTER);
       System.out.println("Wait Action actionPerformed 9");
      messageLabel = new JLabel(message);
       System.out.println("Wait Action actionPerformed 10");
      messagePanel.add(messageLabel, BorderLayout.PAGE_START);
       System.out.println("Wait Action actionPerformed 11");
      messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
       System.out.println("Wait Action actionPerformed 12");

      dialog.add(messagePanel);
       System.out.println("Wait Action actionPerformed 13");
      dialog.pack();
       System.out.println("Wait Action actionPerformed 14");
      dialog.setLocationRelativeTo(this.coloringStudio.frame);
       System.out.println("Wait Action actionPerformed 15");
      dialog.setVisible(true);
       System.out.println("Wait Action actionPerformed 16");
   }
}