/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.groot.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import org.jlab.groot.base.PadMargins;
import org.jlab.groot.math.Dimension2D;

/**
 *
 * @author gavalian
 */
public class GraphicsAxisFrame {
    
    private Dimension2D          axisFrameDimension     = new Dimension2D();
    private PadMargins           axisFrameMargins       = new PadMargins();
    private List<GraphicsAxis>      axisFrameAxis       = new ArrayList<GraphicsAxis>();
    private boolean                 drawAxisZ           = false;
    
    private int                     colorAxisOffset     = 4;
    private int                     colorAxisSize       = 8;
    
    public GraphicsAxisFrame(){
        axisFrameAxis.add(new GraphicsAxis());
        axisFrameAxis.add(new GraphicsAxis());
        axisFrameAxis.add(new GraphicsAxis());
        axisFrameAxis.get(1).setVertical(true);
        axisFrameAxis.get(2).setAxisType(GraphicsAxis.AXISTYPE_COLOR);
        axisFrameAxis.get(2).setAxisFontSize(10);
    }
    
    public Dimension2D getFrameDimensions(){
        return this.axisFrameDimension;
    }
    
    public void setFrameDimensions(double xmin, double xmax, double ymin, double ymax){
        this.axisFrameDimension.getDimension(0).setMinMax(xmin, xmax);
        this.axisFrameDimension.getDimension(1).setMinMax(ymin, ymax);
        this.axisFrameAxis.get(0).setDimension((int) xmin, (int) xmax);
        this.axisFrameAxis.get(1).setDimension((int) ymax, (int) ymin);
    }
    
    public void updateMargins(Graphics2D g2d){
        double  xoffset = axisFrameAxis.get(1).getAxisBounds(g2d);
        double  yoffset = axisFrameAxis.get(0).getAxisBounds(g2d);
        axisFrameMargins.setLeftMargin( (int) xoffset);
        axisFrameMargins.setBottomMargin((int) yoffset);
        axisFrameMargins.setTopMargin(10);
        axisFrameMargins.setRightMargin(15);
        if(this.drawAxisZ==true){
            double zoffset = axisFrameAxis.get(2).getAxisBounds(g2d);
            double length  = 15 + zoffset + this.colorAxisOffset + this.colorAxisSize;
            axisFrameMargins.setRightMargin((int) length);
        }
    }
    
    public PadMargins  getFrameMargins(){
        return this.axisFrameMargins;
    }
    
    public void setAxisMargins(PadMargins margins){
        double xcorner = axisFrameDimension.getDimension(0).getMin() + margins.getLeftMargin();
        double ycorner = axisFrameDimension.getDimension(1).getMax() - margins.getBottomMargin();
        
        axisFrameAxis.get(0).setDimension((int) xcorner, 
                (int) (axisFrameDimension.getDimension(0).getMax() 
                        - margins.getRightMargin()));
        axisFrameAxis.get(1).setDimension((int) ycorner,
                (int) (axisFrameDimension.getDimension(1).getMin()
                        + margins.getTopMargin())
                );
        axisFrameAxis.get(2).setDimension((int) ycorner,
                (int) (axisFrameDimension.getDimension(1).getMin()
                        + margins.getTopMargin()));
    }
    
    public void drawAxis(Graphics2D g2d, PadMargins margins){
        double xcorner = axisFrameDimension.getDimension(0).getMin() + margins.getLeftMargin();
        double ycorner = axisFrameDimension.getDimension(1).getMax() - margins.getBottomMargin();
        /*
        axisFrameAxis.get(0).setDimension((int) xcorner, 
                (int) (axisFrameDimension.getDimension(0).getMax() 
                        - margins.getRightMargin()));
        axisFrameAxis.get(1).setDimension((int) ycorner,
                (int) (axisFrameDimension.getDimension(1).getMin()
                        + margins.getTopMargin())
                );
        */
        /*
        System.out.println(" GRAPHICS AXIS CORNERS = " + (int) xcorner
                + " - " + (int) ycorner);
        System.out.println("----> X axis " + axisFrameAxis.get(0).getDimension());
        System.out.println("----> Y axis " + axisFrameAxis.get(1).getDimension());
        */
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(
                (int) getAxisX().getDimension().getMin(),
                (int) getAxisY().getDimension().getMax(),
                (int) getAxisX().getDimension().getLength(),
                (int) Math.abs(getAxisY().getDimension().getLength())
                );
        axisFrameAxis.get(0).drawAxis(g2d, (int) xcorner, (int) ycorner);
        axisFrameAxis.get(1).drawAxis(g2d, (int) xcorner, (int) ycorner);
        
        if(this.drawAxisZ==true){
            int xc = (int) this.axisFrameDimension.getDimension(0).getMax() 
                    - this.axisFrameMargins.getRightMargin();
            axisFrameAxis.get(2).drawAxis(g2d, (int) xc, (int) ycorner);
        }
    }
    
    public void setDrawAxisZ(boolean flag){
        this.drawAxisZ = flag;
    }
    
    
    public GraphicsAxis  getAxisX(){
        return this.axisFrameAxis.get(0);
    }
    
    public GraphicsAxis  getAxisY(){
        return this.axisFrameAxis.get(1);
    }
    
    public GraphicsAxis  getAxisZ(){
        return this.axisFrameAxis.get(2);
    }
    
    public int getAxisPointX(double value){
        return (int) axisFrameAxis.get(0).getAxisPosition(value);
    }
    
    public int getAxisPointY(double value){
        return (int) axisFrameAxis.get(1).getAxisPosition(value);
    }
    
    public int getAxisPointZ(double value){
        return (int) axisFrameAxis.get(2).getAxisPosition(value);
    }
    
}
