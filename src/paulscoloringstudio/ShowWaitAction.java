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

   public ShowWaitAction(String name, String dialogTitle, String dialogMessage, PaulsColoringStudio pColoringStudio, ActionListener a) {
      super(name);
      this.coloringStudio = pColoringStudio;
      this.a = a;
      this.title = dialogTitle;
      this.message = dialogMessage;
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

      JProgressBar progressBar = new JProgressBar();
      
      progressBar.setIndeterminate(true);
      JPanel panel = new JPanel(new BorderLayout());
      panel.add(progressBar, BorderLayout.CENTER);
      panel.add(new JLabel(message), BorderLayout.PAGE_START);
      dialog.add(panel);
      dialog.pack();
      dialog.setLocationRelativeTo(this.coloringStudio.frame);
      dialog.setVisible(true);
   }
}