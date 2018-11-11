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
      SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>(){
         @Override
         protected Void doInBackground() throws Exception {
            a.actionPerformed(new ActionEvent(ShowWaitAction.this.coloringStudio, ActionEvent.ACTION_PERFORMED, null) {});
            return null;
         }
      };

      final JDialog dialog = new JDialog(this.coloringStudio.frame, title, ModalityType.APPLICATION_MODAL);

      mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {

         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("state")) {
               if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                  dialog.dispose();
               }
            }
         }
      });
      mySwingWorker.execute();
      
      progressBar.setIndeterminate(true);
      messagePanel = new JPanel(new BorderLayout());
      messagePanel.add(progressBar, BorderLayout.CENTER);
      messageLabel = new JLabel(message);
      messagePanel.add(messageLabel, BorderLayout.PAGE_START);
      messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      dialog.add(messagePanel);
      dialog.pack();
      dialog.setLocationRelativeTo(this.coloringStudio.frame);
      dialog.setVisible(true);
   }
}