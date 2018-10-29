/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paulscoloringstudio;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
    JMenuItem findEdgesItem;
    JMenuItem trackMotionItem;
    JMenuItem autoBorderItem;
    JMenuItem reverseBorderDirectionItem;
    
    
    JMenuItem newImageProjectItem;
    JMenuItem newVideoProjectItem;
    JMenuItem openImageProjectItem;
    JMenuItem openVideoProjectItem;
    JMenuItem saveProjectItem;
    JMenuItem saveProjectAsItem;
    JMenuItem exportImageItem;
    JMenuItem exportVideoItem;
    JMenuItem closeItem;
    
    JMenu openRecent;
    
    
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
        
        
        
        
    }
    
    
    void createFileMenuItems()
    {
        newImageProjectItem = new JMenuItem("New Image Project");
        newImageProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.newImageProject();
            }
        });
        fileMenu.add(newImageProjectItem);
        
        
        
        newVideoProjectItem = new JMenuItem("New Video Project");
        newVideoProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.newVideoProject();
            }
        });
        fileMenu.add(newVideoProjectItem);
        
        
        openImageProjectItem = new JMenuItem("Open Image Project");
        openImageProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.openImageProject();
            }
        });
        fileMenu.add(openImageProjectItem);
        
        
        
        openVideoProjectItem = new JMenuItem("Open Video Project");
        openVideoProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.openVideoProject();
            }
        });
        fileMenu.add(openVideoProjectItem);
        
        
        openRecent = new JMenu("Open Recent");
        for (String filepath : coloringStudio.recentFiles)
        {
            JMenuItem item = new JMenuItem(filepath);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (filepath.substring(filepath.length()-4).equals("vmoc") ||
                            filepath.substring(filepath.length()-4).equals("VMOC"))
                    {
                        PaulsColoringStudio.PROJECT_TYPE = PaulsColoringStudio.PROJECT_TYPE_VIDEO;
                        File vmocFile = new File(filepath);
                        coloringStudio.loadVideoProject(vmocFile);
                    }
                    else
                    {
                        PaulsColoringStudio.PROJECT_TYPE = PaulsColoringStudio.PROJECT_TYPE_IMAGE;
                        File selected_pmoc_file = new File(filepath);
                        coloringStudio.loadPMOC(selected_pmoc_file);
                        coloringStudio.loadPMOCImage(selected_pmoc_file);
                    }
                    repaint();
                }
            });
            openRecent.add(item);
        }
        fileMenu.add(openRecent);
        
        saveProjectItem = new JMenuItem("Save Project");
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
                //coloringStudio.saveProjectAs();
            }
        });
        fileMenu.add(saveProjectAsItem);
        
        
        
        exportImageItem = new JMenuItem("Export Image");
        exportImageItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //coloringStudio.exportImage();
            }
        });
        fileMenu.add(exportImageItem);
        
        
        
        exportVideoItem = new JMenuItem("Export Video");
        exportVideoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //coloringStudio.exportVideo();
            }
        });
        fileMenu.add(exportVideoItem);
        
        
        closeItem = new JMenuItem("Close");
        closeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        editMenu.add(redoItem);
        
        
        selectPreviousObjectItem = new JMenuItem("Select Previous Object");
        selectPreviousObjectItem.setAccelerator(KeyStroke.getKeyStroke(
                '1'));
        selectPreviousObjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int objectId = coloringStudio.getIndexOfSelected() - 1;
                coloringStudio.selectObjectById(objectId);
                
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
            }
        });
        editMenu.add(selectNextObjectItem);
        
        
        saveItem = new JMenuItem("Save Video Frame");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.saveVideoFrame();
            }
        });
        editMenu.add(saveItem);
        
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
        
        findEdgesItem = new JMenuItem("Find Edges");
        findEdgesItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        findEdgesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloringStudio.findEdges();
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
        
    }
    
    void createHelpMenuItems()
    {
        
    }
    
}
