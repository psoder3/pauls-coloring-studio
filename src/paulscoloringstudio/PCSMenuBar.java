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
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

/**
 *
 * @author paulsoderquist
 */
public class PCSMenuBar extends JMenuBar {
    
    PaulsColoringStudio coloringStudio;
    JMenu fileMenu;
    JMenu editMenu;
    JMenu viewMenu;
    JMenu helpMenu;
    JMenuItem undoItem;
    JMenuItem redoItem;
    
    JMenuItem selectNextObjectItem;
    JMenuItem selectPreviousObjectItem;
    
    JMenuItem saveItem;
    JMenuItem deselectItem;
    JMenuItem vertexModeItem;
    JMenuItem polygonModeItem;
    JMenuItem dragVerticesItem;
    JMenuItem dragObjectsItem;
    JMenuItem drawItem;
    JMenuItem applyToAllFramesItem;
    JMenuItem findEdgesItem;
    JMenuItem trackMotionItem;
    JMenuItem autoBorderItem;
    JMenuItem reverseBorderDirectionItem;
    
    JMenuItem translateUp;
    JMenuItem translateLeft;
    JMenuItem translateDown;
    JMenuItem translateRight;
    
    JMenuItem backSpace;
    
    
    JMenuItem newImageProjectItem;
    JMenuItem newVideoProjectItem;
    JMenuItem openImageProjectItem;
    JMenuItem openVideoProjectItem;
    public JMenuItem saveProjectItem;
    JMenuItem saveProjectAsItem;
    JMenuItem exportImageItem;
    JMenuItem exportVideoItem;
    JMenuItem closeItem;
    
    JMenuItem zoomIn;
    JMenuItem zoomOut;
    
    JMenuItem viewUp;
    JMenuItem viewLeft;
    JMenuItem viewDown;
    JMenuItem viewRight;
    
    
    JMenu openRecent;
    
    JMenuItem basicTutorial;
    JMenuItem aboutItem;
    
    
    PCSMenuBar(PaulsColoringStudio pColoringStudio)
    {
        super();
        this.coloringStudio = pColoringStudio;
        
        //Build the first menu.
        fileMenu = new JMenu("File");
        //menu.setMnemonic(KeyEvent.VK_A);
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        this.add(fileMenu);
        
        //Build the first menu.
        editMenu = new JMenu("Edit");
        //menu.setMnemonic(KeyEvent.VK_A);
        editMenu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        this.add(editMenu);
        
        //Build the first menu.
        viewMenu = new JMenu("View");
        //menu.setMnemonic(KeyEvent.VK_A);
        viewMenu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        this.add(viewMenu);
        
        
        //Build the second menu.
        helpMenu = new JMenu("Help");
        //menu.setMnemonic(KeyEvent.VK_A);
        helpMenu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        this.add(helpMenu);
        
        
        
        createFileMenuItems();
        createEditMenuItems();
        createViewMenuItems();
        createHelpMenuItems();
        
        setEnabledViewItems(false);
        setEnabledOpenItems(false);
        setEnabledManipulateVertexItems(false);
        saveProjectAsItem.setEnabled(false);
        this.applyToAllFramesItem.setEnabled(false);
        this.saveProjectItem.setEnabled(false);
        this.coloringStudio.saveButton.setEnabled(false);
        this.undoItem.setEnabled(false);
        this.redoItem.setEnabled(false);
        
    }
    
    void setEnabledViewItems(boolean enabled)
    {
        zoomIn.setEnabled(enabled);
        zoomOut.setEnabled(enabled);
        viewUp.setEnabled(enabled);
        viewDown.setEnabled(enabled);
        viewRight.setEnabled(enabled);
        viewLeft.setEnabled(enabled);
    }
    
    void setEnabledManipulateVertexItems(boolean enabled)
    {
        translateUp.setEnabled(enabled);
        translateDown.setEnabled(enabled);
        translateRight.setEnabled(enabled);
        translateLeft.setEnabled(enabled);
        backSpace.setEnabled(enabled);
        findEdgesItem.setEnabled(enabled);
        if (this.coloringStudio.PROJECT_TYPE == PaulsColoringStudio.PROJECT_TYPE_IMAGE)
        {
            trackMotionItem.setEnabled(false);
        }
        else
        {
            trackMotionItem.setEnabled(enabled);
        }
    }
    
    void setEnabledWASD(boolean enabled)
    {
        translateUp.setEnabled(enabled);
        translateDown.setEnabled(enabled);
        translateRight.setEnabled(enabled);
        translateLeft.setEnabled(enabled);
    }
    
    void setEnabledOpenItems(boolean enabled)
    {
        if (coloringStudio.currentProjectState.polygons.size() <= 0)
        {
            selectNextObjectItem.setEnabled(false);
            selectPreviousObjectItem.setEnabled(false);
            deselectItem.setEnabled(false);
        }
        else
        {
            selectNextObjectItem.setEnabled(enabled);
            selectPreviousObjectItem.setEnabled(enabled);
            deselectItem.setEnabled(enabled);
        }

        autoBorderItem.setEnabled(enabled);
        reverseBorderDirectionItem.setEnabled(enabled);
        vertexModeItem.setEnabled(enabled);
        polygonModeItem.setEnabled(enabled);
        dragVerticesItem.setEnabled(enabled);
        dragObjectsItem.setEnabled(enabled);
        drawItem.setEnabled(enabled);
        exportImageItem.setEnabled(enabled);
        coloringStudio.toolList.setEnabled(enabled);
        coloringStudio.ShowOutlinesCheckbox.setEnabled(enabled);
        coloringStudio.colorLayersButton.setEnabled(enabled);
        coloringStudio.recolorPolygonsButton.setEnabled(enabled);
        //coloringStudio.saveButton.setEnabled(enabled);
        
        if (this.coloringStudio.PROJECT_TYPE == PaulsColoringStudio.PROJECT_TYPE_IMAGE)
        {
            exportVideoItem.setEnabled(false);
        }
        else
        {
            exportVideoItem.setEnabled(enabled);
        }
        
    }
    
    void initOpenRecent()
    {
        openRecent = new JMenu("Open Recent");
        for (String path : coloringStudio.recentFiles)
        {
            JMenuItem item = new JMenuItem(path);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    coloringStudio.askSaveBeforeClosing();
                    coloringStudio.resetUndoHistory();
                    String filepath = item.getText();
                    File selected_file = new File(filepath);
                    
                    String file_name = selected_file.getName();
                    coloringStudio.ProjectName = file_name.substring(0,file_name.length()-5);
                    int pathLength = selected_file.getAbsolutePath().length();
                    coloringStudio.ProjectDirectory = selected_file.getAbsolutePath().substring(0, pathLength - file_name.length());
                    
                    if (filepath.substring(filepath.length()-4).equals("vmoc") ||
                            filepath.substring(filepath.length()-4).equals("VMOC"))
                    {
                        coloringStudio.PROJECT_TYPE = PaulsColoringStudio.PROJECT_TYPE_VIDEO;
                        File vmocFile = new File(filepath);
                        coloringStudio.loadVideoProject(vmocFile);
                        
                        coloringStudio.editorPanel.updateFrame();
                        coloringStudio.editorPanel.setVideoNavigationEnabled(true);
                        repaint();
                    }
                    else
                    {
                        coloringStudio.PROJECT_TYPE = PaulsColoringStudio.PROJECT_TYPE_IMAGE;
                        File selected_pmoc_file = new File(filepath);

                        coloringStudio.loadPMOC(selected_pmoc_file);
                        coloringStudio.loadPMOCImage(selected_pmoc_file);
                        coloringStudio.addToRecentFiles("pmoc");
                        coloringStudio.saveButton.setText("Save");
                        saveProjectItem.setText("Save");
                        coloringStudio.frame.setTitle(coloringStudio.ProjectName + " - Paul's Coloring Studio");
                        coloringStudio.editorPanel.setVideoNavigationEnabled(false);
                        saveProjectAsItem.setEnabled(true);
                        PCSMenuBar.this.applyToAllFramesItem.setEnabled(false);
                        coloringStudio.setEnabledSaveButtons(false);
                    }
                    
                    coloringStudio.loadedImage();
                    coloringStudio.setEnabledSaveButtons(false);

                    repaint();
                }
            });
            openRecent.add(item);
        }
    }
    
    void updateOpenRecent()
    {
        for (int i = 0 ; i <  openRecent.getItemCount(); i++) {
            JMenuItem item = openRecent.getItem(i);
            item.setText(coloringStudio.recentFiles.get(i));
        }
    }
    
    void createFileMenuItems()
    {
        newImageProjectItem = new JMenuItem("New Image Project");
        newImageProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.askSaveBeforeClosing();
                coloringStudio.newImageProject();
            }
        });
        fileMenu.add(newImageProjectItem);
        
        
        
        newVideoProjectItem = new JMenuItem("New Video Project");
        newVideoProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.askSaveBeforeClosing();
                coloringStudio.newVideoProject();
            }
        });
        fileMenu.add(newVideoProjectItem);
        
        
        fileMenu.addSeparator();

        
        openImageProjectItem = new JMenuItem("Open Image Project");
        openImageProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.askSaveBeforeClosing();
                coloringStudio.openImageProject();
                coloringStudio.loadedImage();
                coloringStudio.setEnabledSaveButtons(false);
                saveProjectAsItem.setEnabled(true);
                PCSMenuBar.this.applyToAllFramesItem.setEnabled(false);
            }
        });
        fileMenu.add(openImageProjectItem);
        
        
        
        openVideoProjectItem = new JMenuItem("Open Video Project");
        openVideoProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.askSaveBeforeClosing();
                coloringStudio.openVideoProject();
                coloringStudio.editorPanel.updateFrame();
                coloringStudio.loadedImage();
                coloringStudio.setEnabledSaveButtons(false);
            }
        });
        fileMenu.add(openVideoProjectItem);
        
        
        fileMenu.addSeparator();

        initOpenRecent();
        
        fileMenu.add(openRecent);
        
        fileMenu.addSeparator();
        
        
        saveProjectItem = new JMenuItem("Save");
        saveProjectItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.saveProject();
            }
        });
        fileMenu.add(saveProjectItem);
        
        saveProjectAsItem = new JMenuItem("Save Project As");
        saveProjectAsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.saveProjectAs();
            }
        });
        fileMenu.add(saveProjectAsItem);
        
        fileMenu.addSeparator();        
        
        exportImageItem = new JMenuItem("Export Image");
        exportImageItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.exportImage();
            }
        });
        fileMenu.add(exportImageItem);
        
        
        
        exportVideoItem = new JMenuItem("Export Video");
        exportVideoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.exportVideo();
            }
        });
        fileMenu.add(exportVideoItem);
        
        fileMenu.addSeparator();
        
        closeItem = new JMenuItem("Close");
        closeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.askSaveBeforeClosing();
                coloringStudio.close();
            }
        });
        fileMenu.add(closeItem);
        
    }

    void createEditMenuItems()
    {
        //a group of JMenuItems
        undoItem = new JMenuItem("Undo");
        undoItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        undoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.undo();
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    repaint();
                }            
            }
        });
        editMenu.add(undoItem);
        
        redoItem = new JMenuItem("Redo");
        redoItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        redoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.redo();
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    repaint();
                }
            }
        });
        editMenu.add(redoItem);

        editMenu.addSeparator();        
        
        selectPreviousObjectItem = new JMenuItem("Select Previous Object");
        selectPreviousObjectItem.setAccelerator(KeyStroke.getKeyStroke(
                '1'));
        selectPreviousObjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int objectId = coloringStudio.getIndexOfSelected() - 1;
                coloringStudio.selectObjectById(objectId);
                PCSMenuBar.this.setEnabledManipulateVertexItems(true);
            }
        });
        editMenu.add(selectPreviousObjectItem);
        
        selectNextObjectItem = new JMenuItem("Select Next Object");
        selectNextObjectItem.setAccelerator(KeyStroke.getKeyStroke(
                '2'));
        selectNextObjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int objectId = coloringStudio.getIndexOfSelected() + 1;
                coloringStudio.selectObjectById(objectId);
                PCSMenuBar.this.setEnabledManipulateVertexItems(true);
            }
        });
        editMenu.add(selectNextObjectItem);
                
        deselectItem = new JMenuItem("Deselect");
        deselectItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_U, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        deselectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.deselect();
            }
        });
        editMenu.add(deselectItem);
        
        editMenu.addSeparator();

        
        translateUp = new JMenuItem("Move up");
        translateUp.setAccelerator(KeyStroke.getKeyStroke('w'));
        translateUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.translateUp();
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    coloringStudio.repaint();
                }
            }
        });
        editMenu.add(translateUp);
        
        translateLeft = new JMenuItem("Move left");
        translateLeft.setAccelerator(KeyStroke.getKeyStroke('a'));
        translateLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.translateLeft();
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    coloringStudio.repaint();
                }
            }
        });
        editMenu.add(translateLeft);
        
        translateDown = new JMenuItem("Move down");
        translateDown.setAccelerator(KeyStroke.getKeyStroke('s'));
        translateDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.translateDown();
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    coloringStudio.repaint();
                }
            }
        });
        editMenu.add(translateDown);
        
        translateRight = new JMenuItem("Move right");
        translateRight.setAccelerator(KeyStroke.getKeyStroke('d'));
        translateRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.translateRight();
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    coloringStudio.repaint();
                }
            }
        });
        editMenu.add(translateRight);
        
        
        
        
        editMenu.addSeparator();
        
                
        backSpace = new JMenuItem("Delete point");
        backSpace.setAccelerator(KeyStroke.getKeyStroke("BACK_SPACE"));
        backSpace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.delete();
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    coloringStudio.repaint();
                }
            }
        });
        editMenu.add(backSpace);
        
        editMenu.addSeparator();
        
        /*
        saveItem = new JMenuItem("Save Video Frame");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.saveVideoFrame();
            }
        });*/
        //editMenu.add(saveItem);
        
        
        //editMenu.addSeparator();
        
        vertexModeItem = new JMenuItem("Vertex Mode");
        vertexModeItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        vertexModeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.setVertexMode();
            }
        });
        editMenu.add(vertexModeItem);
        
        polygonModeItem = new JMenuItem("Select Polygon Mode");
        polygonModeItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_B, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        polygonModeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.setSelectMode();
            }
        });
        editMenu.add(polygonModeItem);
        
        dragVerticesItem = new JMenuItem("Drag Select Vertices");
        dragVerticesItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        dragVerticesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.setDragVerticesMode();
            }
        });
        editMenu.add(dragVerticesItem);
        
        dragObjectsItem = new JMenuItem("Drag Select Objects");
        dragObjectsItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        dragObjectsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.setDragObjectsMode();
            }
        });
        editMenu.add(dragObjectsItem);
        
        drawItem = new JMenuItem("Pen Tool");
        drawItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        drawItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.setDrawingMode();
            }
        });
        editMenu.add(drawItem);
        
        editMenu.addSeparator();
        
        
        applyToAllFramesItem = new JMenuItem("Apply To All Frames");
        applyToAllFramesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.ApplyFrameToAllFrames();
            }
        
        });
        editMenu.add(applyToAllFramesItem);
        
        
        findEdgesItem= new JMenuItem("Find Edges");
        findEdgesItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        findEdgesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.findEdges();
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    repaint();
                }
            }
        });
        editMenu.add(findEdgesItem);
        
        trackMotionItem = new JMenuItem("Track Motion");
        trackMotionItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        trackMotionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.trackMotionFromPreviousFrame();
                
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    repaint();
                }
        
            }
        });
        editMenu.add(trackMotionItem);
        
        autoBorderItem = new JMenuItem("Turn Auto-Complete Border On");
        autoBorderItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_M, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        autoBorderItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (coloringStudio.AutoCompleteBorder)
                {
                    coloringStudio.AutoCompleteBorder = false;
                    autoBorderItem.setText("Turn Auto-Complete Border On");
                }
                else
                {
                    coloringStudio.AutoCompleteBorder = true;
                    autoBorderItem.setText("Turn Auto-Complete Border Off");
                }
            }
        });
        editMenu.add(autoBorderItem);
        
        reverseBorderDirectionItem = new JMenuItem("Reverse Auto-Complete Direction");
        reverseBorderDirectionItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        reverseBorderDirectionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.BorderReversed = !coloringStudio.BorderReversed;
                repaint();
            }
        });
        editMenu.add(reverseBorderDirectionItem);
    }
    
    void createViewMenuItems()
    {
        //a group of JMenuItems
        zoomIn = new JMenuItem("Zoom in");
        zoomIn.setAccelerator(KeyStroke.getKeyStroke(
                '='));
        zoomIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.zoomIn();
            }
        });
        viewMenu.add(zoomIn);
        
        zoomOut = new JMenuItem("Zoom out");
        zoomOut.setAccelerator(KeyStroke.getKeyStroke(
                '-'));
        zoomOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.zoomOut();
            }
        });
        viewMenu.add(zoomOut);
        
        
        viewMenu.addSeparator();
        
        viewUp = new JMenuItem("View up");
        //viewUp.setAccelerator(KeyStroke.getKeyStroke("UP"));
        viewUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.viewUp();
            }
        });
        viewMenu.add(viewUp);
        
        viewLeft = new JMenuItem("View left");
        //viewLeft.setAccelerator(KeyStroke.getKeyStroke("LEFT"));
        viewLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.viewLeft();
            }
        });
        viewMenu.add(viewLeft);
        
        viewDown = new JMenuItem("View down");
        //viewDown.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        viewDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.viewDown();
            }
        });
        viewMenu.add(viewDown);
        
        viewRight = new JMenuItem("View right");
        //viewRight.setAccelerator(KeyStroke.getKeyStroke("RIGHT"));
        viewRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.viewRight();
            }
        });
        viewMenu.add(viewRight);
    }
    
    void createHelpMenuItems()
    {
        basicTutorial = new JMenuItem("Basic \"How-to's\"");
        basicTutorial.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_H, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        basicTutorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInstructions();
            }
        });
        helpMenu.add(basicTutorial);
        
        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout();
            }
        });
        helpMenu.add(aboutItem);
    }
    
    
    void showInstructions()
    {
        try {
            JEditorPane editorPane= new JEditorPane();
            editorPane.setEditable(false);
            JScrollPane editorScrollPane = new JScrollPane(editorPane);
            editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            File file = new File("Resources" + File.separator + "help.html");
            editorPane.setPage(file.toURI().toURL());
            
            JDialog helpDialog = new JDialog();
            helpDialog.add(editorScrollPane, BorderLayout.CENTER);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int help_width = (int)(screenSize.getWidth()*.6);
            int help_height = (int)(screenSize.getHeight()*.8);

            helpDialog.setSize(help_width,help_height);
            
            double x = (screenSize.getWidth()/2) - (helpDialog.getWidth()/2);
            double y = (screenSize.getHeight()/2) - (helpDialog.getHeight()/2);
            helpDialog.setLocation((int)x, (int)y);
            
            helpDialog.setVisible(true);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(PCSMenuBar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PCSMenuBar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    String getAboutHTML()
    {
        String aboutHTML = 
                "<html>"+
                "<center>" +
                "<h1>About Paul's Coloring Studio</h1>" +
                "</center>" +
                "<p>" +
                "This project was started by me, Paul Soderquist, in January 2018, driven by both a passion for classic "+""+
                "<br>" +
                "cinema and a curiosity for what can be done to improve film colorization techniques. It is entirely written "+
                "<br>" +
                "and maintained by me as a nonprofit, open-source project so feel free to offer suggestions or edits but "+
                "<br>" +
                "please realize I am limited in the amount of time I can continue to dedicate to this. If you use this software "+
                "<br>" +
                "to create something cool, I’d appreciate a shoutout. :)" +
                "</p>" +
                "<br>" +
                "<br>" +
                "<p>" +
                "The file types created in this project can only be interpreted by this program. They include:" +
                "<br><ul><li>.pmoc (Paul’s Masked Object Coloring)</li>" +
                "<li>.vmoc (paul’s Video Masked Object Coloring)</li><ul>" +
                "</p>" +
                "<br>" +
                "<br>" +
                "Github: https://github.com/psoder3/pauls-coloring-studio" +
                "<br>" +
                "Contact: paulsoderquist3@gmail.com" +
                "</html>";

        return aboutHTML;
    }
    
    void showAbout()
    {
        new AboutDialog(coloringStudio.frame,"About Paul's Coloring Studio",getAboutHTML());
        /*try {
            JEditorPane editorPane= new JEditorPane();
            editorPane.setEditable(false);
            JScrollPane editorScrollPane = new JScrollPane(editorPane);
            editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            File file = new File("Resources" + File.separator + "about.html");
            editorPane.setPage(file.toURI().toURL());
            
            JDialog helpDialog = new JDialog();
            helpDialog.add(editorScrollPane, BorderLayout.CENTER);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int help_width = (int)(screenSize.getWidth()*.5);
            int help_height = (int)(screenSize.getHeight()*.3);

            helpDialog.setSize(help_width,help_height);
            
            double x = (screenSize.getWidth()/2) - (helpDialog.getWidth()/2);
            double y = (screenSize.getHeight()/2) - (helpDialog.getHeight()/2);
            helpDialog.setLocation((int)x, (int)y);
            
            helpDialog.setVisible(true);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(PCSMenuBar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PCSMenuBar.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
    }
    
}
