/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paulscoloringstudio;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author paulsoderquist
 */
public class Pixel {
        
    public int red;
    public int green;
    public int blue;
    final public int column;
    final public int row;
    private BufferedImage parentImage;

    Pixel (int r, int g, int b, int column, int row, BufferedImage parentImage)
    {
        this.parentImage = parentImage;
        red = r;
        green = g;
        blue = b;
        this.column = column;
        this.row = row;

    }

    public int getRedValue()
    {
        return red;
    }

    public int getGreenValue()
    {
        return green;
    }

    public int getBlueValue()
    {
        return blue;
    }

    public int getAverage()
    {
        return (red + green + blue)/3;
    }

    public java.awt.Point getPoint()
    {
        return new java.awt.Point(column,row);
    }

    public void setRGB(int r, int g, int b)
    {
        //System.out.println(r + " " + g + " " + b);
        try
        {
            Color c = new Color(r,g,b);
            parentImage.setRGB(column, row, c.getRGB());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setRGB(int r, int g, int b, BufferedImage bi)
    {
        //System.out.println(r + " " + g + " " + b);
        try
        {
            Color c = new Color(r,g,b);
            bi.setRGB(column, row, c.getRGB());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Pixel)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members 
        Pixel p = (Pixel) o;

        // Compare the data members and return accordingly 
        return p.red == this.red && p.green == this.green && p.blue == this.blue;
    }



    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.red;
        hash = 59 * hash + this.green;
        hash = 59 * hash + this.blue;
        return hash;
    }
}
