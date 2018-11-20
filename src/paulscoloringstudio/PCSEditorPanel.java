/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paulscoloringstudio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author paulsoderquist
 */
public class PCSEditorPanel extends JPanel {
    
    
    String[] edgeBlendStrings = { "No Edge Blend", "Edge Blend 1 Pixel"};
    JComboBox edgeBlendList = new JComboBox(edgeBlendStrings);
    String[] whichColorStrings = { "Primary Color", "Secondary Color"};
    JComboBox whichColorList = new JComboBox(whichColorStrings);
    
    JPanel colorPanel;
    JPanel frameNavigatorPanel;
    JPanel grabFramesPanel;
    
    JLabel hue_label;
    JSlider hue_slider;
    int hue_min = 0;
    int hue_max = 360;
    int hue_initial = 0;
    
    JLabel sat_label;
    JSlider sat_slider;
    int sat_min = 0;
    int sat_max = 100;
    int sat_initial = 0;
    
    JLabel val_label;
    JSlider val_slider;
    int val_min = 0;
    int val_max = 100;
    int val_initial = 100;
    
    JLabel hue_variation_label;
    JSlider hue_var_slider;
    int hue_var_min = 0;
    int hue_var_max = 360;
    int hue_var_initial = 0;
    
    JLabel sat_variation_label;
    JSlider sat_var_slider;
    int sat_var_min = 0;
    int sat_var_max = 100;
    int sat_var_initial = 0;
    
    JLabel complement_label;
    JSlider complement_slider;
    int comp_min = 0;
    int comp_max = 256;
    int comp_initial = 0;
    
    JSpinner hue_spinner;
    JSpinner sat_spinner;
    JSpinner val_spinner;
    
    JSpinner hue_variation_spinner;
    JSpinner sat_variation_spinner;
    
    JSpinner complement_spinner;
    
    JSpinner video_frame_spinner;
    
    JSlider video_frame_slider;
    int video_frame_min = 0;
    int video_frame_max = 1;
    int video_frame_initial = 0;
    int video_current_value = 0;
    
    JLabel numFramesLabel;
    
    JButton beginFramesButton = new JButton("Grab Frames");
    //JTextField numFramesField = new JTextField(15);
    
    
    
    JLabel idLbl = new JLabel("   Object ID");
    JTextField idField = new JTextField(3);
    
    JLabel depthLbl = new JLabel("   Object Depth");
    JTextField depthField = new JTextField(3);
    
    JLabel frameLbl;
    
    boolean lastPressedWasBackward = false;
    ChangeListener videoSpinnerCL;
    ChangeListener framesSpinnerCL;
    ChangeListener videoSliderCL;
    ChangeListener frameSliderCL;
    
    JSpinner number_frames_spinner;
    
    
    Rectangle rectangle;
    
    /*
    JSlider red_slider;
    int red_min = 0;
    int red_max = 255;
    int red_initial = 255;
    
    JSlider green_slider;
    int green_min = 0;
    int green_max = 255;
    int green_initial = 255;
    
    JSlider blue_slider;
    int blue_min = 0;
    int blue_max = 255;
    int blue_initial = 255;
    
    JSpinner red_spinner;
    JSpinner green_spinner;
    JSpinner blue_spinner;
    */
    
    
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    double depthSnapshot = 0;
    
    private PaulsColoringStudio coloringStudio;
    
    public PCSEditorPanel(PaulsColoringStudio pColoringStudio)
    {
        super();
        
        SpinnerModel number_frames_model =
        new SpinnerNumberModel(1, //initial value
                               1, //min
                               10000, //max
                               1);                //step
        number_frames_spinner = new JSpinner(number_frames_model);
        
//        number_frames_spinner.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                
//            }
//        });
        
        colorPanel = new JPanel();
        frameNavigatorPanel = new JPanel();
        JPanel depthPanel = new JPanel();
        depthField.setText("0");
        idField.setText("NULL");
        idField.setEditable(false);
        depthPanel.add(this.idLbl);
        depthPanel.add(this.idField);
        depthPanel.add(this.depthLbl);
        depthPanel.add(this.depthField);
        depthField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                try
                {
                    depthSnapshot = Double.parseDouble(depthField.getText());
                }
                catch (NumberFormatException ex)
                {
                    Logger.getLogger(ex.getMessage());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                
                

                double number = coloringStudio.currentProjectState.selectedPolygon.depth;
                try
                {
                    number = Double.parseDouble(depthField.getText());
                }
                catch (NumberFormatException ex)
                {
                    depthField.setText(number+"");
                }
                if (number != depthSnapshot)
                {
                    coloringStudio.pushCurrentToUndoStack("change depth of object");
                }
                coloringStudio.currentProjectState.selectedPolygon.depth = number;
                //imageFilters.colorizeImageByLayers(); // this function takes forever
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    repaint();
                }
            }
        });
        
        this.coloringStudio = pColoringStudio;
        //this.setLayout(new GridLayout(2,1));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // -----------------------
        // H S V
        // -----------------------
        
        // -----------------------
        // Hue
        // -----------------------
        JPanel hue_panel = new JPanel();
        hue_label = new JLabel("H");
        hue_slider = new JSlider(JSlider.HORIZONTAL,hue_min,hue_max,hue_initial);
        SpinnerModel hue_model =
        new SpinnerNumberModel(hue_initial, //initial value
                               hue_min, //min
                               hue_max, //max
                               1);                //step
        hue_spinner = new JSpinner(hue_model);
        hue_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hue_spinner.setValue((int)(hue_slider.getValue()));
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    repaint();
                }
                //calculateRGB();
                repaint();
            }
        });
        hue_spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hue_slider.setValue((int)(hue_spinner.getValue()));
                if (coloringStudio.RecolorOnSelectCheckbox.isSelected() || hue_spinner.hasFocus() || hue_slider.hasFocus())
                {
                    calculateRGB();
                }
                repaint();
            }
        });
        
        hue_spinner.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                coloringStudio.pushCurrentToUndoStack("change hue");
            }

            @Override
            public void focusLost(FocusEvent e) {
              
            }
        });
        
        hue_slider.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                coloringStudio.pushCurrentToUndoStack("change hue");
            }

            @Override
            public void focusLost(FocusEvent e) {
              
            }
        });
        
        
        hue_panel.add(hue_label);
        hue_panel.add(hue_slider);
        hue_panel.add(hue_spinner);
        
        // -----------------------
        // Saturation
        // -----------------------
        JPanel sat_panel = new JPanel();
        sat_label = new JLabel("S");
        sat_slider = new JSlider(JSlider.HORIZONTAL,sat_min,sat_max,sat_initial);
        SpinnerModel sat_model =
        new SpinnerNumberModel(sat_initial, //initial value
                               sat_min, //min
                               sat_max, //max
                               1);                //step
        sat_spinner = new JSpinner(sat_model);
        sat_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sat_spinner.setValue((int)(sat_slider.getValue()));
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    repaint();
                }
                //calculateRGB();
                repaint();
            }
        });
        sat_spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sat_slider.setValue((int)(sat_spinner.getValue()));
                calculateRGB();
                repaint();
            }
        });
        
        
        
        sat_spinner.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                coloringStudio.pushCurrentToUndoStack("change saturation");
            }

            @Override
            public void focusLost(FocusEvent e) {
              
            }
        });
        
        sat_slider.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                coloringStudio.pushCurrentToUndoStack("change saturation");
            }

            @Override
            public void focusLost(FocusEvent e) {
              
            }
        });
        
        
        
        sat_panel.add(sat_label);
        sat_panel.add(sat_slider);
        sat_panel.add(sat_spinner);
        
        // -----------------------
        // Value
        // -----------------------
        JPanel val_panel = new JPanel();
        val_label = new JLabel("V");
        val_slider = new JSlider(JSlider.HORIZONTAL,val_min,val_max,val_initial);
        SpinnerModel val_model =
        new SpinnerNumberModel(val_initial, //initial value
                               val_min, //min
                               val_max, //max
                               1);                //step
        val_spinner = new JSpinner(val_model);
        val_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                val_spinner.setValue((int)(val_slider.getValue()));
                //calculateRGB();
                repaint();
            }
        });
        val_spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                val_slider.setValue((int)(val_spinner.getValue()));
                calculateRGB();
                repaint();
            }
        });
        val_panel.add(val_label);
        val_panel.add(val_slider);
        val_panel.add(val_spinner);
        
        
        // -----------------------
        // Hue Variation
        // -----------------------
        JPanel hue_variation_panel = new JPanel();
        hue_variation_label = new JLabel("hue variation");
        hue_var_slider = new JSlider(JSlider.HORIZONTAL,hue_var_min,hue_var_max,hue_var_initial);

        SpinnerModel hue_variation_model =
        new SpinnerNumberModel(hue_var_initial, //initial value
                               hue_var_min, //min
                               hue_var_max, //max
                               1);                //step
        hue_variation_spinner = new JSpinner(hue_variation_model);
        hue_var_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hue_variation_spinner.setValue((int)(hue_var_slider.getValue()));
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    repaint();
                }
                repaint();
            }
        });
        hue_variation_spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hue_var_slider.setValue((int)(hue_variation_spinner.getValue()));
                if (pColoringStudio.currentProjectState.selectedPolygon != null)
                {
                    if (whichColorList.getSelectedItem().equals("Primary Color"))
                    {
                        coloringStudio.currentProjectState.selectedPolygon.hue_variation = 
                        (int)hue_variation_spinner.getValue();
                    }
                    else if (whichColorList.getSelectedItem().equals("Secondary Color"))
                    {
                        coloringStudio.currentProjectState.selectedPolygon.secondary_hue_variation = 
                        (int)hue_variation_spinner.getValue();
                    }
                }
                calculateRGB();
                repaint();
            }
        });
        
        hue_variation_spinner.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                coloringStudio.pushCurrentToUndoStack("change hue variation");
            }

            @Override
            public void focusLost(FocusEvent e) {
              
            }
        });
        
        hue_var_slider.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                coloringStudio.pushCurrentToUndoStack("change hue variation");
            }

            @Override
            public void focusLost(FocusEvent e) {
              
            }
        });
        
        hue_variation_panel.add(hue_variation_label);
        hue_variation_panel.add(hue_var_slider);
        hue_variation_panel.add(hue_variation_spinner);
        
        // -----------------------
        // Saturation Variation
        // -----------------------
        JPanel sat_variation_panel = new JPanel();
        sat_variation_label = new JLabel("sat variation");
        sat_var_slider = new JSlider(JSlider.HORIZONTAL,sat_var_min,sat_var_max,sat_var_initial);
        SpinnerModel sat_variation_model =
        new SpinnerNumberModel(sat_var_initial, //initial value
                               sat_var_min, //min
                               sat_var_max, //max
                               1);                //step
        sat_variation_spinner = new JSpinner(sat_variation_model);
        sat_var_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sat_variation_spinner.setValue((int)(sat_var_slider.getValue()));
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    repaint();
                }
                repaint();
            }
        });
        sat_variation_spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sat_var_slider.setValue((int)(sat_variation_spinner.getValue()));
                if (pColoringStudio.currentProjectState.selectedPolygon != null)
                {
                    if (whichColorList.getSelectedItem().equals("Primary Color"))
                    {
                        coloringStudio.currentProjectState.selectedPolygon.saturation_variation = 
                        (int)sat_variation_spinner.getValue();
                    }
                    else if (whichColorList.getSelectedItem().equals("Secondary Color"))
                    {
                        coloringStudio.currentProjectState.selectedPolygon.secondary_sat_variation = 
                        (int)sat_variation_spinner.getValue();
                    }
                    
                }
                repaint();
                calculateRGB();
            }
        });
        
        
        sat_variation_spinner.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                coloringStudio.pushCurrentToUndoStack("change saturation variation");
            }

            @Override
            public void focusLost(FocusEvent e) {
              
            }
        });
        
        sat_var_slider.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                coloringStudio.pushCurrentToUndoStack("change saturation variation");
            }

            @Override
            public void focusLost(FocusEvent e) {
              
            }
        });
        
        
        sat_variation_panel.add(sat_variation_label);
        sat_variation_panel.add(sat_var_slider);
        sat_variation_panel.add(sat_variation_spinner);
        
        // -----------------------
        // SECONDARY COLOR THRESHOLD (FOR MULTIPLE COLORS IN SAME OBJECT)
        // Complementary Shadows
        // -----------------------
        JPanel complement_panel = new JPanel();
        complement_label = new JLabel("secondary");
        complement_slider = new JSlider(JSlider.HORIZONTAL,comp_min,comp_max,comp_initial);
        SpinnerModel complement_model =
        new SpinnerNumberModel(comp_initial, //initial value
                               comp_min, //min
                               comp_max, //max
                               1);                //step
        complement_spinner = new JSpinner(complement_model);
        complement_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                complement_spinner.setValue((int)(complement_slider.getValue()));
                if (!coloringStudio.saveButton.isEnabled())
                {
                    coloringStudio.setEnabledSaveButtons(true);
                    repaint();
                }
                repaint();
            }
        });
        complement_spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                complement_slider.setValue((int)(complement_spinner.getValue()));
                if (pColoringStudio.currentProjectState.selectedPolygon != null)
                {
                    pColoringStudio.currentProjectState.selectedPolygon.complement_threshold = 
                        (int)complement_spinner.getValue();
                }
                calculateRGB();
                repaint();
            }
        });
        
        
        complement_spinner.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                coloringStudio.pushCurrentToUndoStack("change secondary threshold");
            }

            @Override
            public void focusLost(FocusEvent e) {
              
            }
        });
        
        complement_slider.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                coloringStudio.pushCurrentToUndoStack("change secondary threshold");
            }

            @Override
            public void focusLost(FocusEvent e) {
              
            }
        });
        
        
        complement_panel.add(complement_label);
        complement_panel.add(complement_slider);
        complement_panel.add(complement_spinner);
        
        
        // -----------------------
        // Edge Blend JCombo Box
        // -----------------------
        
        edgeBlendList.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                int index = edgeBlendList.getSelectedIndex();
                pColoringStudio.currentProjectState.selectedPolygon.edgeBlendIndex = index;
                calculateRGB();
                repaint();
            }
        });
        
        // -----------------------
        // Which Color JCombo Box
        // -----------------------
        
        whichColorList.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String whichColorString = (String)(whichColorList.getSelectedItem());
                MaskedObject clickedObject = pColoringStudio.currentProjectState.selectedPolygon;
                if (whichColorString.equals("Primary Color"))
                {
                    setSelectedColor(clickedObject.color);
                    hue_variation_spinner.setValue(clickedObject.hue_variation);
                    sat_variation_spinner.setValue(clickedObject.saturation_variation);
                    hue_var_slider.setValue(clickedObject.hue_variation);
                    sat_var_slider.setValue(clickedObject.saturation_variation);
                }
                else if (whichColorString.equals("Secondary Color"))
                {
                    setSelectedColor(clickedObject.secondary_color);
                    hue_variation_spinner.setValue(clickedObject.secondary_hue_variation);
                    sat_variation_spinner.setValue(clickedObject.secondary_sat_variation);
                    hue_var_slider.setValue(clickedObject.secondary_hue_variation);
                    sat_var_slider.setValue(clickedObject.secondary_sat_variation);
                }
                repaint();
            }
        });
        
        // -----------------------
        // Video Frame
        // -----------------------
        //JLabel video_frame_label = new JLabel("Frame");
        video_frame_slider = new JSlider(JSlider.HORIZONTAL,video_frame_min,video_frame_max,video_frame_initial);
        SpinnerModel video_frame_model =
        new SpinnerNumberModel(video_frame_initial, //initial value
                               video_frame_min, //min
                               video_frame_max, //max
                               1);                //step
        video_frame_spinner = new JSpinner(video_frame_model);
        videoSliderCL = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                video_frame_spinner.setValue((int)(video_frame_slider.getValue()));
                repaint();
            }
        };
        
        frameSliderCL = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                coloringStudio.askSaveBeforeClosing();
                video_frame_spinner.setValue((int)(video_frame_slider.getValue()));
                coloringStudio.setEnabledSaveButtons(false);
                repaint();
            }
        };
        
        video_frame_slider.addChangeListener(videoSliderCL);
        
        videoSpinnerCL = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = (int)(video_frame_spinner.getValue());
                if (value == video_current_value+1)
                {
                    pColoringStudio.advanceFrame();
                    lastPressedWasBackward = false;
                }
                else if (value == video_current_value-1)
                {
                    pColoringStudio.goToPreviousFrame();
                    lastPressedWasBackward = true;
                }
                else
                {
                    video_frame_slider.setValue(value);
                    pColoringStudio.setCurrentFrame(value);
                }
                video_current_value = value;
                repaint();
            }
        };
        
        framesSpinnerCL = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                coloringStudio.askSaveBeforeClosing();
                updateFrame();
                coloringStudio.setEnabledSaveButtons(false);
                repaint();
            }
        };
        
        
        
        video_frame_spinner.addChangeListener(videoSpinnerCL);
        //video_frame_panel.add(video_frame_label);
        

        /*
        numFramesField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                try
                {
                    Integer.parseInt(numFramesField.getText());
                }
                catch (NumberFormatException ex)
                {
                    numFramesField.setText("5");
                }
            }
        });
        */
        //numFramesField.setText("5");
        beginFramesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //int numberFrames = Integer.parseInt(numFramesField.getText());
                int numberFrames = (int)(number_frames_spinner.getValue());
                                
                JFileChooser fileChooser = new JFileChooser();

		int userSelection = fileChooser.showSaveDialog(coloringStudio.frame);
                boolean fileExists = true;
		while (userSelection == JFileChooser.APPROVE_OPTION && fileExists) {
                    fileExists = false;
                    File fileToSave = fileChooser.getSelectedFile();
                    String absPath = fileToSave.getAbsolutePath();
                    if (Files.exists(Paths.get(absPath))) {
                        fileExists = true;
                        new AboutDialog(coloringStudio.frame,"Error","A project with that name already exists in that location");
                        userSelection = fileChooser.showSaveDialog(coloringStudio.frame);
                    }
                    else
                    {
                        System.out.println("BeginFramesButton Action Performed 1");
                        coloringStudio.findExactFrame();
                        System.out.println("BeginFramesButton Action Performed 2");
                        try 
                        {
                            System.out.println("BeginFramesButton Action Performed 3");
                            Files.createDirectories(Paths.get(absPath));
                            System.out.println("BeginFramesButton Action Performed 4");
                            coloringStudio.ProjectName = fileToSave.getName();
                            System.out.println("BeginFramesButton Action Performed 5");
                            coloringStudio.ProjectDirectory = absPath + File.separator;
                            System.out.println("BeginFramesButton Action Performed 6");
                            File vmocFile = new File(absPath + File.separator + fileToSave.getName() + ".vmoc");
                            System.out.println("BeginFramesButton Action Performed 7");
                            Files.createDirectories(Paths.get(absPath+File.separator+"Video Frame PMOCs"));
                            System.out.println("BeginFramesButton Action Performed 8");
                            FileWriter fw = new FileWriter(vmocFile);
                            System.out.println("BeginFramesButton Action Performed 9");


                            fw.append(video_current_value + " " + numberFrames + " " + coloringStudio.frameRate);
                            System.out.println("BeginFramesButton Action Performed 10");
                            fw.close();
                            System.out.println("BeginFramesButton Action Performed 11");

                            pColoringStudio.grabNextFrames(numberFrames,video_current_value);
                            System.out.println("BeginFramesButton Action Performed 12");
                            pColoringStudio.setVideoFrameAlreadySaved(video_current_value+1);
                            System.out.println("BeginFramesButton Action Performed 13");



                        } catch (IOException ex) 
                        {
                            Logger.getLogger(PCSEditorPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } 
            }
        });
        
        

        
        frameLbl = new JLabel("Frame Navigator");
        JPanel video_slider_panel = new JPanel();
        
        JPanel video_frame_panel = new JPanel();
        JPanel video_grab_panel = new JPanel();

        video_frame_panel.setLayout(new BoxLayout(video_frame_panel, BoxLayout.PAGE_AXIS));
        video_slider_panel.add(video_frame_slider);
        video_slider_panel.add(video_frame_spinner);
        video_frame_panel.add(frameLbl);
        video_frame_panel.add(video_slider_panel);

        numFramesLabel = new JLabel("Number of frames to grab:");
        grabFramesPanel = new JPanel();

        //video_grab_panel.setLayout(new BoxLayout(video_grab_panel, BoxLayout.PAGE_AXIS));
        video_grab_panel.setLayout(new GridLayout(2,1));
        //grabFramesPanel.add(numFramesField);
        //grabFramesPanel.add(beginFramesButton);
        grabFramesPanel.add(numFramesLabel);
        grabFramesPanel.add(number_frames_spinner);
        video_grab_panel.add(grabFramesPanel);
        video_grab_panel.add(beginFramesButton);

        
        frameNavigatorPanel.setLayout(new GridLayout(2,1));
        //frameNavigatorPanel.setLayout(new BoxLayout(frameNavigatorPanel, BoxLayout.PAGE_AXIS));

        
        frameNavigatorPanel.add(video_frame_panel);
        frameNavigatorPanel.add(video_grab_panel);

        /*
        // -----------------------
        // R G B
        // -----------------------
        
        // -----------------------
        // Red
        // -----------------------
        JPanel red_panel = new JPanel();
        JLabel red_label = new JLabel("R");
        red_slider = new JSlider(JSlider.HORIZONTAL,red_min,red_max,red_initial);
        SpinnerModel red_model =
        new SpinnerNumberModel(red_initial, //initial value
                               red_min, //min
                               red_max, //max
                               1);                //step
        red_spinner = new JSpinner(red_model);
        red_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                red_spinner.setValue((int)(red_slider.getValue()));
                calculateHSV();
                repaint();
            }
        });
        red_spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                red_slider.setValue((int)(red_spinner.getValue()));
                calculateHSV();
                repaint();
            }
        });
        red_panel.add(red_label);
        red_panel.add(red_slider);
        red_panel.add(red_spinner);
        
        // -----------------------
        // Green
        // -----------------------
        JPanel green_panel = new JPanel();
        JLabel green_label = new JLabel("G");
        green_slider = new JSlider(JSlider.HORIZONTAL,green_min,green_max,green_initial);
        SpinnerModel green_model =
        new SpinnerNumberModel(green_initial, //initial value
                               green_min, //min
                               green_max, //max
                               1);                //step
        green_spinner = new JSpinner(green_model);
        green_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                green_spinner.setValue((int)(green_slider.getValue()));
                calculateHSV();
                repaint();
            }
        });
        green_spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                green_slider.setValue((int)(green_spinner.getValue()));
                calculateHSV();
                repaint();
            }
        });
        green_panel.add(green_label);
        green_panel.add(green_slider);
        green_panel.add(green_spinner);
        
        // -----------------------
        // Blue
        // -----------------------
        JPanel blue_panel = new JPanel();
        JLabel blue_label = new JLabel("B");
        blue_slider = new JSlider(JSlider.HORIZONTAL,blue_min,blue_max,blue_initial);
        SpinnerModel blue_model =
        new SpinnerNumberModel(blue_initial, //initial value
                               blue_min, //min
                               blue_max, //max
                               1);                //step
        blue_spinner = new JSpinner(blue_model);
        blue_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                blue_spinner.setValue((int)(blue_slider.getValue()));
                calculateHSV();
                repaint();
            }
        });
        blue_spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                blue_slider.setValue((int)(blue_spinner.getValue()));
                calculateHSV();
                repaint();
            }
        });
        blue_panel.add(blue_label);
        blue_panel.add(blue_slider);
        blue_panel.add(blue_spinner);
        
        */
        rectangle = new Rectangle();
        colorPanel.setLayout(new GridLayout(9,1));
        colorPanel.add(whichColorList);
        colorPanel.add(hue_panel);
        colorPanel.add(sat_panel);
        colorPanel.add(val_panel);
        colorPanel.add(rectangle);
        
        colorPanel.add(hue_variation_panel);
        colorPanel.add(sat_variation_panel);
        
        colorPanel.add(complement_panel);
        //this.add(edgeBlendList);
        colorPanel.add(depthPanel);
        
        this.add(colorPanel);
        this.add(new JSeparator(SwingConstants.HORIZONTAL));
        this.add(frameNavigatorPanel);
        //this.add(red_panel);
        //this.add(green_panel);
        //this.add(blue_panel);
        this.setObjectEditorEnabled(false);
        this.setVideoNavigationEnabled(false);
        this.setFrameGrabEnabled(false);
    }
    
    
    public void updateFrame()
    {
        int value = (int)(video_frame_spinner.getValue());
        coloringStudio.setVideoFrameAlreadySaved(value);
        video_current_value = value;
        video_frame_slider.setValue(value);
    }
    
    void setObjectEditorEnabled(boolean enabled)
    {
        this.whichColorList.setEnabled(enabled);
        this.hue_label.setEnabled(enabled);
        this.hue_slider.setEnabled(enabled);
        this.hue_spinner.setEnabled(enabled);
        this.sat_label.setEnabled(enabled);
        this.sat_slider.setEnabled(enabled);
        this.sat_spinner.setEnabled(enabled);
        this.val_label.setEnabled(enabled);
        this.val_slider.setEnabled(enabled);
        this.val_spinner.setEnabled(enabled);
        this.hue_variation_label.setEnabled(enabled);
        this.hue_var_slider.setEnabled(enabled);
        this.hue_variation_spinner.setEnabled(enabled);
        this.sat_variation_label.setEnabled(enabled);
        this.sat_var_slider.setEnabled(enabled);
        this.sat_variation_spinner.setEnabled(enabled);
        this.complement_label.setEnabled(enabled);
        this.complement_slider.setEnabled(enabled);
        this.complement_spinner.setEnabled(enabled);
        this.depthLbl.setEnabled(enabled);
        this.depthField.setEnabled(enabled);
        this.idLbl.setEnabled(enabled);
        this.idField.setEnabled(enabled);
        this.rectangle.setEnabled(enabled);
    }
    
    void setVideoNavigationEnabled(boolean enabled)
    {
        this.frameLbl.setEnabled(enabled);
        this.video_frame_slider.setEnabled(enabled);
        this.video_frame_spinner.setEnabled(enabled);
    }
    
    void setFrameGrabEnabled(boolean enabled)
    {
        this.numFramesLabel.setEnabled(enabled);
        this.beginFramesButton.setEnabled(enabled);
        //this.numFramesField.setEnabled(enabled);
        this.number_frames_spinner.setEnabled(enabled);
    }
    
    void swapToFrameChangeListener() {
        video_frame_spinner.removeChangeListener(videoSpinnerCL);
        video_frame_spinner.addChangeListener(framesSpinnerCL);
        video_frame_slider.removeChangeListener(videoSliderCL);
        video_frame_slider.addChangeListener(frameSliderCL);

    }
    
    void swapToVideoChangeListener() {
        video_frame_spinner.removeChangeListener(framesSpinnerCL);
        video_frame_spinner.addChangeListener(videoSpinnerCL);
        video_frame_slider.removeChangeListener(frameSliderCL);
        video_frame_slider.addChangeListener(videoSliderCL);
    }
    
    public Color getColor()
    {
        return new Color(red,green,blue);
    }
    
    public void setSelectedColor(Color c)
    {
        if (c == null)
        {
            red = 255;
            green = 255;
            blue = 255;
        }
        else
        {
            red = c.getRed();
            green = c.getGreen();
            blue = c.getBlue();
        }
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        float hue = hsb[0]; 
        float saturation = hsb[1];
        float brightness = hsb[2];
        
        hue_slider.setValue((int)(hue*hue_max+.5));
        hue_spinner.setValue((int)(hue*hue_max+.5));
        sat_slider.setValue((int)(saturation*sat_max+.5));
        sat_spinner.setValue((int)(saturation*sat_max+.5));
        val_slider.setValue((int)(brightness*val_max+.5));
        val_spinner.setValue((int)(brightness*val_max+.5));
    }
    
    public void calculateRGB()
    {
        float h = hue_slider.getValue()/360.0f;
        float s = sat_slider.getValue()/100.0f;
        float v = val_slider.getValue()/100.0f;
        
        int rgb = Color.HSBtoRGB(h, s, v);
        red = (rgb >> 16) & 0xFF;
        green = (rgb >> 8) & 0xFF;
        blue = rgb & 0xFF;
        
        /*
        red_slider.setValue(red);
        red_spinner.setValue(red);
        green_slider.setValue(green);
        green_spinner.setValue(green);
        blue_slider.setValue(blue);
        blue_spinner.setValue(blue);
        */
        if (coloringStudio.currentProjectState.selectedPolygon != null)
        {
            Color color = getColor();
            String whichColor = (String)(whichColorList.getSelectedItem());
            if (whichColor.equals("Primary Color"))
            {
                coloringStudio.currentProjectState.selectedPolygon.color = color;
            }
            else if (whichColor.equals("Secondary Color"))
            {
                coloringStudio.currentProjectState.selectedPolygon.secondary_color = color;
            }
            MaskedObject polygon = coloringStudio.currentProjectState.selectedPolygon;
            if (coloringStudio.RecolorOnSelectCheckbox.isSelected() 
                    || hue_spinner.hasFocus() || hue_slider.hasFocus()
                    || sat_spinner.hasFocus() || sat_slider.hasFocus()
                    || val_spinner.hasFocus() || val_slider.hasFocus()
                    || hue_variation_spinner.hasFocus() || hue_var_slider.hasFocus()
                    || sat_variation_spinner.hasFocus() || sat_var_slider.hasFocus()
                    || complement_slider.hasFocus() || complement_spinner.hasFocus())
            {
                coloringStudio.colorizePolygon(polygon);
            }
            coloringStudio.repaint();
        }
    }
    
    /*
    public void calculateHSV()
    {
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        float hue = hsb[0]; 
        float saturation = hsb[1];
        float brightness = hsb[2];
        
        red = red_slider.getValue();
        green = green_slider.getValue();
        blue = blue_slider.getValue();
        
        hue_slider.setValue((int)(hue*hue_max+.5));
        hue_spinner.setValue((int)(hue*hue_max+.5));
        sat_slider.setValue((int)(saturation*sat_max+.5));
        sat_spinner.setValue((int)(saturation*sat_max+.5));
        val_slider.setValue((int)(brightness*val_max+.5));
        val_spinner.setValue((int)(brightness*val_max+.5));
        
    }
    */
    
    /*
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.add(new PCSEditorPanel(null));
        f.setVisible(true);
        f.pack();
    }
    */
    

    class Rectangle extends JPanel {

        public void paintComponent(Graphics g) {
          super.paintComponent(g);
          Graphics2D g2d = (Graphics2D) g;

          g2d.setColor(new Color(red, green, blue));
          g2d.fillRect(10, 5, 330, 25);
          g2d.setColor(new Color(0, 0, 0));
          g2d.drawRect(10, 5, 330, 25);

        }

  }
}


