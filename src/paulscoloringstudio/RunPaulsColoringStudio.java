/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paulscoloringstudio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.bytedeco.javacv.FrameGrabber;

/**
 *
 * @author paulsoderquist
 */
public class RunPaulsColoringStudio {
    
    public static void main(String[] args) {
        
        System.setProperty("apple.awt.application.name", "Paul\'s Coloring Studio");

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Paul\'s Coloring Studio");
        

        final PaulsColoringStudio graphic = new PaulsColoringStudio();
        graphic.currentProjectState.polygons = new ArrayList();
        graphic.ShowOutlinesCheckbox.setSelected(true);
        //graphic.assembleVideoFromFrames();
        graphic.numberMosaicColumnsBox.setText("30");
        graphic.addMouseListener(graphic);
        graphic.addMouseMotionListener(graphic);
        graphic.frame = new JFrame();
        graphic.filterList.setSelectedIndex(5);
        graphic.keyList.setSelectedIndex(0);
        graphic.addKeyListener(graphic);
        graphic.setFocusable(true);
        graphic.requestFocus();
        graphic.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                graphic.currentProjectState.adjacentPolygon = null;
                graphic.currentProjectState.adjacentPolygonVertex = -1;
                graphic.repaint();
            }
        });
        //JScrollPane scroll = new JScrollPane(graphic,
        //    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        //    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //graphic.frame.getContentPane().add(scroll);
        
        // I substituted above code with below code when I made my own scrolling functionality
        graphic.frame.getContentPane().add(graphic);

        graphic.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphic.frame.setSize(PaulsColoringStudio.WINDOW_WIDTH, PaulsColoringStudio.WINDOW_HEIGHT + 100);
        graphic.frame.setTitle("Paul's Coloring Studio");
        graphic.LoadImageButton.addActionListener((ActionEvent e) -> {
            graphic.LoadImage();
            
        });
        graphic.LoadTrainingImageButton.addActionListener((ActionEvent e) -> {
            graphic.LoadImage();
            
            graphic.repaint();
            
            graphic.count++;
            //graphic.trainColorizeArrayMode();
            //graphic.train24NeighborSumMode();
            graphic.train2NeighborKey();
            for (int i = 0; i < 256; i++)
            {
                if (graphic.neighborMeanMap[i] != null)
                {
                    Collections.sort(graphic.neighborMeanMap[i]);
                }
            }
            
        });
        graphic.LoadSetImagesButton.addActionListener((ActionEvent e) -> {
            graphic.LoadImageSet();
            
            graphic.repaint();
            
            graphic.count++;
            //graphic.trainColorizeArrayMode();
            //graphic.train24NeighborSumMode();
            //graphic.train4NeighborKey();
            
        });
        graphic.ResetButton.addActionListener((ActionEvent e) -> {
            graphic.filterPolygon = false;
            //graphic.selectedPolygon.reset();        
            graphic.ResetImage();
            graphic.repaint();
        });
        graphic.ResetTrainingButton.addActionListener((ActionEvent e) -> {
            graphic.filterPolygon = false;
            //graphic.selectedPolygon.reset();
            graphic.neighbor_key_dictionary.clear();
            graphic.neighbor_key_dictionary_1to1.clear();
            graphic.alreadyFoundModes = false;
            graphic.ResetImage();
            graphic.repaint();
        });
        graphic.InvertButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.InvertColors();
            graphic.repaint();
        });
        graphic.GrayScaleButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.ConvertToGrayScale();
            graphic.repaint();
        });
        graphic.StepColorsButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.StepColors();
            graphic.repaint();
        });
        graphic.EmbossButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.EmbossImage();
            graphic.repaint();
        });
        graphic.BlurButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.BlurImage();
            graphic.repaint();
        });
        graphic.RedButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.ColorizeRed();
            graphic.repaint();
        });
        graphic.GreenButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.ColorizeGreen();
            graphic.repaint();
        });
        graphic.BlueButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.ColorizeBlue();
            graphic.repaint();
        });
        
        graphic.ColorizeButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.Colorize();
            graphic.repaint();
        });
        
        graphic.randomValuesButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.RandomValues();
            graphic.repaint();
        });
        
        graphic.BlackWhiteButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            //graphic.image_copy = getImageCopy();
            graphic.BlackWhite(90);

            Timer timer=new Timer(50, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ev) {

                    if(graphic.bwcounter < 256){
                        graphic.BlackWhite(graphic.bwcounter++);
                        graphic.repaint();
                    }
                }
            });
            timer.start();
                
            graphic.repaint();
        });
        graphic.AlternateRGButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            //graphic.image_copy = getImageCopy();

            
            graphic.ResetButton.setEnabled(true);
            
            Timer timer=new Timer(3, (ActionEvent ev) -> {
                if (graphic.RGCounter % 3 == 0)
                {
                    graphic.RedGreen("green", graphic.image_copy);
                }
                else if (graphic.RGCounter % 3 == 1)
                {
                    graphic.RedGreen("red", graphic.image_copy);
                }
                else
                {
                    graphic.RedGreen("blue", graphic.image_copy);
                }
                graphic.RGCounter++;
                graphic.repaint();
            });
            timer.start();
                
            graphic.repaint();
        });
        
        graphic.SumBoundingBoxButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.SumBoundingBoxColorize();
            graphic.repaint();
        });
        
        graphic.TwoNeighboKeyButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.ResetButton.setEnabled(true);
            graphic.NeighborKeyColorize(graphic.image_pixels);
            graphic.repaint();
        });
        
        graphic.SplitVideoFramesButton.addActionListener((ActionEvent e) -> {
            graphic.splitVideoIntoFrames();
        });
        /*
        graphic.ColorizeFramesButton.addActionListener((ActionEvent e) -> {
            graphic.colorizeVideoFrames("Colorize");
        });
        graphic.CreateColorizedMovieButton.addActionListener((ActionEvent e) -> {
            graphic.colorizeVideoFrames("Colorize");
            graphic.assembleVideoFromFrames();
        });*/
        graphic.AssembleFramesButton.addActionListener((ActionEvent e) -> {
            graphic.assembleVideoFromFrames();
        });
        graphic.AsciiFilterButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            String textImage = graphic.AsciiFilter();
            graphic.textToImage(textImage, "testImage.png");

            graphic.repaint();
        });
       
        graphic.AsciiFilterVideoButton.addActionListener((ActionEvent e) -> {
            
            graphic.createAsciiVideo();
            graphic.assembleVideoFromFrames();
            graphic.repaint();
        });
        
        graphic.PixelateButton.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.pixelate();
            graphic.ResetButton.setEnabled(true);
            graphic.repaint();
            
        });
        graphic.Mosaic1Button.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.mosaic1();
            graphic.mosaicType = "Mosaic1";
            graphic.ResetButton.setEnabled(true);
            graphic.repaint();
        });
        graphic.Mosaic2Button.addActionListener((ActionEvent e) -> {
            graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
            graphic.mosaic2();
            graphic.mosaicType = "Mosaic2";
            graphic.ResetButton.setEnabled(true);
            graphic.repaint();
        });
        
        graphic.trackMotionButton.addActionListener((ActionEvent e) -> {
            if (graphic.currentProjectState.selectedPolygon != null)
            {
                graphic.trackMotionFromPreviousFrame();
            }
            else
            {
                for (MaskedObject p : graphic.currentProjectState.selectedObjects)
                {
                    graphic.currentProjectState.selectedPolygon = p;
                    graphic.currentProjectState.selectedVertexIndex = 0;
                    graphic.trackMotionFromPreviousFrame();
                }
            }
        });
        
        graphic.findEdgesButton.addActionListener((ActionEvent e) -> {
            graphic.findEdges();
        });
        
        graphic.colorLayersButton.addActionListener((ActionEvent e) -> {
            graphic.colorizeImageByLayers();
        });
        
        graphic.GenerateTrainingArrangements.addActionListener((ActionEvent e) -> {
            graphic.generateTrainingArrangements();
        });
        
        graphic.ApplyFilterButton.addActionListener((ActionEvent e) -> {
            graphic.applyFilter();

        }); 
        
        graphic.loadVideoButton.addActionListener((ActionEvent e) -> {
            graphic.chooseVideoFile();

        }); 
        
        graphic.CreateFilteredVideoButton.addActionListener((ActionEvent e) -> {
            try {
                //graphic.image_pixels = toBufferedImage(selected_image);
                String selected_filter = (String)graphic.filterList.getSelectedItem();
                if (!graphic.filterVideoFrames(selected_filter)) return;
                if (!selected_filter.equals("Create Training Data") 
                        && !selected_filter.equals("Count Number of 9px Patterns"))
                {
                    graphic.assembleVideoFromFrames();
                    graphic.addSoundToVideo();
                }
            } catch (IOException ex) {
                //Logger.getLogger(ImageFilters.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
        
        graphic.countColorsButton.addActionListener((ActionEvent e) -> {
            int roundFactor = 10;
            int currentIndex = 0;
            int totalPixels = graphic.image_pixels.getWidth() * graphic.image_pixels.getHeight();
            ArrayList<ColorFrequency> freqs = new ArrayList();
            try {
                graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
                
                for (int i = 0; i < graphic.image_pixels.getHeight(); i++)
                {
                    for (int j = 0; j < graphic.image_pixels.getWidth(); j++)
                    {
                        int currentCount = (i*graphic.image_pixels.getWidth() + j);
                        if (currentCount % 1000 == 0)
                        {
                            System.out.println(currentCount + " / " + totalPixels);
                        }
                        Pixel p = graphic.getPixel(j,i,graphic.image_pixels);
                        int r = ((p.red + (roundFactor/2)) / roundFactor) * roundFactor;
                        int g = ((p.green + (roundFactor/2)) / roundFactor) * roundFactor;
                        int b = ((p.blue + (roundFactor/2)) / roundFactor) * roundFactor;
                        if (r > 255) 
                        {
                            r = 255;
                        }
                        if (r < 0)
                        {
                            r = 0;
                        }
                        if (g > 255)
                        {
                            g = 255;
                        }
                        if (g < 0)
                        {
                            g = 0;
                        }
                        if (b > 255)
                        {
                            b = 255;
                        }
                        if (b < 0)
                        {
                            b = 0;
                        }
                        Color c = new Color(r,g,b);
                        boolean found = false;
                        for (ColorFrequency col : freqs)
                        {
                            if (col.color.equals(c))
                            {
                                col.frequency++;
                                found = true;
                            }
                        }
                        if (!found)
                        {
                            ColorFrequency freq = new ColorFrequency();
                            freq.color = c;
                            freq.frequency = 1;
                            freqs.add(freq);
                        }
                    }
                }
                Collections.sort(freqs);
                for (ColorFrequency col : freqs)
                {
                    //System.out.println(col.frequency + " " + col.color);
                } 
                int freqCounter = 0;
                for (int i = 0; i < graphic.image_pixels.getHeight(); i++)
                {
                    for (int j = 0; j < graphic.image_pixels.getWidth(); j++)
                    {
                        
                        Pixel p = graphic.getPixel(j,i,graphic.image_pixels);
                        ColorFrequency current = freqs.get(currentIndex);
                        int red = current.color.getRed();
                        int green = current.color.getGreen();
                        int blue = current.color.getBlue();
                        p.setRGB(red, green, blue);
                        freqCounter++;
                        if (freqCounter >= current.frequency)
                        {
                            freqCounter = 0;
                            currentIndex++;
                        }
                    }
                }
                graphic.repaint();
            } catch (Exception ex) {
                Logger.getLogger(PaulsColoringStudio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
        
        
        //graphic.buttons.add(graphic.LoadImageButton);
        //graphic.buttons.add(graphic.LoadTrainingImageButton);
        //graphic.buttons.add(graphic.countColorsButton);
        //graphic.buttons.add(graphic.loadVideoButton);
        graphic.buttons.add(graphic.saveVideoFrameButton);
        graphic.buttons.add(graphic.trackMotionButton);
        graphic.buttons.add(graphic.findEdgesButton);
        
        //graphic.buttons.add(graphic.AssembleFramesButton);
        //graphic.buttons.add(graphic.frameRangeLbl1);
        //graphic.buttons.add(graphic.firstFrameChooser);
        //graphic.buttons.add(graphic.frameRangeLbl2);
        //graphic.buttons.add(graphic.lastFrameChooser);
        //graphic.buttons.add(graphic.LoadSetImagesButton);
        //graphic.buttons.add(graphic.ResetButton);
        //graphic.buttons.add(graphic.ResetTrainingButton);
        
        //graphic.buttons.add(new JLabel("Filter"));
        //graphic.buttons.add(graphic.filterList);
        //graphic.buttons.add(graphic.keyList);
        //graphic.buttons.add(graphic.ApplyFilterButton);
        //graphic.buttons.add(graphic.InvertButton);
        //graphic.buttons.add(graphic.GrayScaleButton);
        //graphic.buttons.add(graphic.StepColorsButton);
        //graphic.buttons.add(graphic.EmbossButton);
        //graphic.buttons.add(graphic.BlurButton);
        //graphic.buttons.add(graphic.RedButton);
        //graphic.buttons.add(graphic.GreenButton);
        //graphic.buttons.add(graphic.BlueButton);                
        //graphic.buttons.add(graphic.ColorizeButton);
        //graphic.buttons.add(graphic.randomValuesButton);
        //graphic.buttons2.add(graphic.BlackWhiteButton);
        //graphic.buttons2.add(graphic.AlternateRGButton);
        //graphic.buttons.add(graphic.SumBoundingBoxButton);
        //graphic.buttons2.add(graphic.TwoNeighboKeyButton);
        //graphic.buttons2.add(graphic.InterpolateGapsCheckbox);
        //graphic.buttons2.add(graphic.FillInBlanksBox);
        
        graphic.saveVideoFrameButton.addActionListener((ActionEvent e) -> {
            graphic.saveVideoFrame();
            
            
        }); 
        
        
        graphic.saveButton.addActionListener((ActionEvent e) -> {
            String filename = graphic.filenameTextBox.getText();

            graphic.writeObjectsToPMOCFile(graphic.currentProjectState.polygons, "PMOCs"
                    + File.separator + filename+".pmoc"); 
            graphic.writeImageFile(graphic.image_pixels,filename,"png");
            
        }); 
        
        /*graphic.openButton.addActionListener((ActionEvent e) -> {
            
            graphic.openImageProject();
            
            
        });*/
        
        
        
        graphic.buttons2.add(graphic.filenameTextBox);
        graphic.buttons2.add(graphic.extensionLbl);
        graphic.buttons2.add(graphic.saveButton);
        //graphic.buttons2.add(graphic.openButton);
        
        
        //graphic.buttons2.add(graphic.FirstMappingOnly);    
        
        graphic.toolList.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                graphic.requestFocus();
                graphic.currentProjectState.adjacentPolygon = null;
                graphic.currentProjectState.adjacentPolygonVertex = -1;
                graphic.repaint();
            }
        });
        
        graphic.ApplySchemeAllFramesButton.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                graphic.ApplyFrameToAllFrames();
            }
        });
        
        graphic.recolorPolygonsButton.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                graphic.recolorSelectedPolygons();
                graphic.requestFocus();
            }
        });
        
        graphic.splitTwoColorsButton.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                graphic.splitTwoColors();
            }
        });
        //graphic.buttons2.add(graphic.splitTwoColorsButton);
        
        graphic.buttons2.add(graphic.toolList);
        //graphic.buttons.add(graphic.SplitVideoFramesButton);
        //graphic.buttons.add(graphic.ColorizeFramesButton);
        //graphic.buttons2.add(graphic.CreateColorizedMovieButton);
        //graphic.buttons2.add(graphic.AsciiFilterButton);
        //graphic.buttons2.add(graphic.AsciiFilterVideoButton);
        graphic.buttons2.add(graphic.ShowOutlinesCheckbox);
        /*graphic.colorChooser.addColorChangedListener(new ColorChangedListener() {
            @Override
            public void colorChanged(Color newColor) {
                    
                    if (graphic.selectedPolygon == null)
                    {
                        return;
                    }
                    
                    graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);
                    //graphic.colorChooser.setSelectedColor(newColor);
                    graphic.selectedPolygon.color = newColor;
                    // do something with newColor ...
                    String selected_filter = (String)graphic.filterList.getSelectedItem();

                    if (selected_filter.equals("Psychedelic"))
                    {
                        graphic.colorizePolygonPsychedelic(newColor);
                    }
                    else
                    {
                        graphic.colorizePolygon(newColor);
                    }
                    graphic.repaint();
            }
        });
        */
        //graphic.buttons2.add(graphic.colorChooser);
        graphic.buttons2.add(graphic.recolorPolygonsButton);
        
        
        graphic.buttons2.add(graphic.colorLayersButton);
        graphic.buttons2.add(graphic.ApplySchemeAllFramesButton);
        
        //graphic.buttons2.add(graphic.PixelateButton);
        //graphic.buttons2.add(graphic.Mosaic1Button);
        //graphic.buttons2.add(graphic.Mosaic2Button);
        //graphic.buttons2.add(graphic.columnsLabel);
        
        //graphic.buttons2.add(graphic.numberMosaicColumnsBox);

        //graphic.buttons2.add(graphic.GenerateTrainingArrangements);
        //graphic.buttons.add(graphic.CreateFilteredVideoButton);
        graphic.ResetButton.setEnabled(false);
                
        /*
        graphic.InvertButton.setEnabled(false);
        graphic.GrayScaleButton.setEnabled(false);
        graphic.StepColorsButton.setEnabled(false);
        graphic.EmbossButton.setEnabled(false);
        graphic.BlurButton.setEnabled(false);
        graphic.RedButton.setEnabled(false);
        graphic.GreenButton.setEnabled(false);        
        graphic.BlueButton.setEnabled(false);
        */
        if (graphic.selected_image != null)
        {
            try {
                graphic.selected_image = ImageIO.read(graphic.selected_file);
                graphic.image_pixels = graphic.toBufferedImage(graphic.selected_image);

            } catch (IOException ex) {
                Logger.getLogger(PaulsColoringStudio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        graphic.editorPanel = new PCSEditorPanel(graphic);
        JPanel buttonPanel = new JPanel();
        GridLayout grid = new GridLayout(2,1);
        buttonPanel.setLayout(grid);
        buttonPanel.add(graphic.buttons);
        buttonPanel.add(graphic.buttons2);
        graphic.frame.add(buttonPanel, BorderLayout.SOUTH);
        graphic.frame.add(graphic.editorPanel, BorderLayout.EAST);
        
        graphic.frame.setJMenuBar(graphic.menuBar);
        
        graphic.revalidate();
        graphic.repaint();
        graphic.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphic.frame.setExtendedState(graphic.frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        graphic.frame.setVisible(true);
        
        //graphic.setupShortcutKeys();
        
        /*
        try {
            //graphic.getScreenCapture();
            
            graphic.g.stop();
        } catch (FrameGrabber.Exception ex) {
            Logger.getLogger(PaulsColoringStudio.class.getName()).log(Level.SEVERE, null, ex);
        }
*/

    }
    
}
