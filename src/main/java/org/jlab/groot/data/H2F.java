package org.jlab.groot.data;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.jlab.groot.base.AttributeType;
import org.jlab.groot.base.Attributes;
import org.jlab.groot.base.DatasetAttributes;
import org.jlab.groot.math.Axis;
import org.jlab.groot.math.MultiIndex;
import org.jlab.groot.ui.PaveText;


/**
 * Specifies the methods to create a 2D Histogram and operations to fill it and
 * set values to its bins
 * 
 * @author Erin Kirby
 * @version 061714
 */
public class H2F implements IDataSet {

	private String hName = "basic2D";
	private Axis xAxis = new Axis();
	private Axis yAxis = new Axis();
	private float[] hBuffer;
	private MultiIndex offset;
        private DatasetAttributes attr = new DatasetAttributes(); 
        private Float     maximumBinValue = 0.0f;
        
	public H2F() {
		offset = new MultiIndex(xAxis.getNBins(), yAxis.getNBins());
		hBuffer = new float[offset.getArraySize()];

        }

        @Override
        public void setName(String name){ this.hName = name;}
	/**
	 * Creates an empty 2D Histogram with 1 bin x and y axes
	 * 
	 * @param name
	 *            the desired name of the 2D Histogram
	 */
	public H2F(String name) {
		hName = name;
		offset = new MultiIndex(xAxis.getNBins(), yAxis.getNBins());
		hBuffer = new float[offset.getArraySize()];

	}

	/**
	 * Creates a 2D Histogram with the specified parameters.
	 * 
	 * @param name
	 *            the name of the histogram
	 * @param bx
	 *            the number of x axis bins
	 * @param xmin
	 *            the minimum x axis value
	 * @param xmax
	 *            the maximum x axis value
	 * @param by
	 *            the number of y axis bins
	 * @param ymin
	 *            the minimum y axis value
	 * @param ymax
	 *            the maximum y axis value
	 */
	public H2F(String name, int bx, double xmin, double xmax, int by,
			double ymin, double ymax) {
		hName = name;
		this.set(bx, xmin, xmax, by, ymin, ymax);
		offset = new MultiIndex(bx, by);
		hBuffer = new float[offset.getArraySize()];                
	}

        public H2F(String name, String title, int bx, double xmin, double xmax, int by,
			double ymin, double ymax) {
                
		hName = name;
                this.setTitle(title);
		this.set(bx, xmin, xmax, by, ymin, ymax);
		offset = new MultiIndex(bx, by);
		hBuffer = new float[offset.getArraySize()];                
	}
	/**
	 * Sets the bins to the x and y axes and creates the buffer of the histogram
	 * 
	 * @param bx
	 *            number of bins on the x axis
	 * @param xmin
	 *            the minimum value on the x axis
	 * @param xmax
	 *            the maximum value on the x axis
	 * @param by
	 *            number of bins on the y axis
	 * @param ymin
	 *            the minimum value on the y axis
	 * @param ymax
	 *            the maximum value on the y axis
	 */
	public final void set(int bx, double xmin, double xmax, int by,
			double ymin, double ymax) {
		xAxis.set(bx, xmin, xmax);
		yAxis.set(by, ymin, ymax);
		offset = new MultiIndex(bx, by);
		int buff = offset.getArraySize();
		hBuffer = new float[buff];
	}

	/**
	 * 
	 * @return the name of the Histogram
	 */
	public String getName() {
		return hName;
	}

	/**
	 * 
	 * @return the x-axis of the 2D Histogram
	 */
	public Axis getXAxis() {
		return xAxis;
	}

	/**
	 * 
	 * @return the y-axis of the 2D Histogram
	 */
	public Axis getYAxis() {
		return yAxis;
	}

        public double getMaximum(){
            double maximum = 0.0;
            for(int loop = 0; loop < hBuffer.length; loop++)
                if(hBuffer[loop]>maximum) maximum = hBuffer[loop];
            return maximum;
        }

	/**
	 * Checks if that bin is valid (exists)
	 * 
	 * @param bx
	 *            The x coordinate of the bin
	 * @param by
	 *            The y coordinate of the bin
	 * @return The truth value of the validity of that bin
	 */
	private boolean isValidBins(int bx, int by) {
		if ((bx >= 0) && (bx <= xAxis.getNBins()) && (by >= 0)
				&& (by <= yAxis.getNBins())) {
			return true;
		}
		return false;
	}

	/**
	 * Finds the bin content at that bin
	 * 
	 * @param bx
	 *            The x coordinate of the bin
	 * @param by
	 *            The y coordinate of the bin
	 * @return The content at that bin
	 */
	public double getBinContent(int bx, int by) {
		if (this.isValidBins(bx, by)) {
			int buff = offset.getArrayIndex(bx, by);
                        if(buff>=0&&buff<hBuffer.length){
                            return hBuffer[buff];
                        } else {
                            System.out.println("[Index] error for binx = "+ bx +
                                    " biny = " + by);
                        }
		}
		return 0.0;
	}
        /**
         * Sets the x-axis title to the specified parameter
         * @param xTitle		The desired title of the x-axis
         */
        public final void setXTitle(String xTitle) {
            //this.getXaxis().setTitle(xTitle);
            attr.setXTitle(xTitle);

        }
        
        /**
         * Sets the y-axis title to the specified parameter
         * 
         * @param yTitle		The desired title of the y-axis
         */
        public final void setYTitle(String yTitle) {
            //this.getYaxis().setTitle(yTitle);
           attr.setYTitle(yTitle);
        }
        
        /**
         * The getter for the histogram title.
         * @return Title of the histogram.
         */
        public String getTitle(){
            //return this.histTitle;
            return attr.getTitle();//this.attr.getString(AttributeType.STRING_TITLE);
        }
        /**
         * The getter for the x-axis title.
         * 
         * @return		The title of the x-axis as a string
         */
        public String getXTitle() {
            return attr.getXTitle();//this.attr.getString(AttributeType.STRING_TITLE_X);
            //return this.getXaxis().getTitle();
        }
        
        /**
         * The getter for the y-axis title.
         * 
         * @return		The title of the y-axis as a string
         */
        public String getYTitle() {
            return attr.getYTitle();//this.attr.getString(AttributeType.STRING_TITLE_Y);
            //return this.getYaxis().getTitle();
        }
        
        /**
         * Sets the specified parameter as the title of the histogram
         * 
         * @param title		The desired title of the histogram
         */
        public final void setTitle(String title) {
            //histTitle = title;
            attr.setTitle(title);
        }
        
        /**
         * Sets the bin to that value
         * 
         * @param bx
         *            The x coordinate of the bin
         * @param by
	 *            The y coordinate of the bin
	 * @param w
	 *            The desired value to set the bin to
	 */
	public void setBinContent(int bx, int by, double w) {
		if (this.isValidBins(bx, by)) {
			int buff = offset.getArrayIndex(bx, by);
			hBuffer[buff] = (float) w;
		}
	}

	/**
	 * Adds 1.0 to the bin with that value
	 * 
	 * @param x
	 *            the x coordinate value
	 * @param y
	 *            the y coordinate value
	 */
	public void fill(double x, double y) {
		int bin = this.findBin(x, y);
		if (bin >= 0)
			this.addBinContent(bin);
	}

	public void fill(double x, double y, double w) {
		int bin = this.findBin(x, y);
		if (bin >= 0) {
			this.addBinContent(bin, w);
		}
	}

	/**
	 * Increments the current bin by 1.0
	 * 
	 * @param bin
	 *            the bin in array indexing format to increment
	 */
	private void addBinContent(int bin) {
		hBuffer[bin] = (float) (hBuffer[bin] + 1.0);
                if(hBuffer[bin]>this.maximumBinValue) 
                    this.maximumBinValue = hBuffer[bin];
	}

	/**
	 * Increments the bin with that value by that weight
	 * 
	 * @param bin
	 *            the bin to add the content to, in array indexing format
	 * @param w
	 *            the value to add to the bin content
	 */
	private void addBinContent(int bin, double w) {
		hBuffer[bin] = (float) (hBuffer[bin] + w);
                if(hBuffer[bin]>this.maximumBinValue) 
                    this.maximumBinValue = hBuffer[bin];
	}
        
        public ArrayList<H1F>  getSlicesX(){
            ArrayList<H1F>  slices = new ArrayList<H1F>();
            for(int loop = 0; loop < this.getXAxis().getNBins(); loop++){
                H1F slice = this.sliceX(loop);
                slice.setName(this.getName()+"_"+loop);
                slices.add(slice);
            }
            return slices;
        }
        
        public ArrayList<H1F>  getSlicesY(){
            ArrayList<H1F>  slices = new ArrayList<H1F>();
            for(int loop = 0; loop < this.getYAxis().getNBins(); loop++){
                H1F slice = this.sliceY(loop);
                slice.setName(this.getName()+"_"+loop);
                slices.add(slice);
            }
            return slices;
        }
        
        public void add(H2F h){
            if(h.getXAxis().getNBins()==this.getXAxis().getNBins()&&
                    h.getYAxis().getNBins()==this.getYAxis().getNBins()){
                for(int loop = 0; loop < this.hBuffer.length; loop++){
                    this.hBuffer[loop] = this.hBuffer[loop] + h.hBuffer[loop];
                }
            } else {
                System.out.println("[warning] ---> error adding histograms " 
                        + this.getName() + "  " + h.getName()
                        + ". inconsistent bin numbers");
            }
        }
        
        
        public static H2F  divide(H2F h1, H2F h2){
            if((h1.getXAxis().getNBins()!=h2.getXAxis().getNBins())||
                    (h1.getYAxis().getNBins()!=h2.getYAxis().getNBins())
                    ){
                System.out.println("[H2D::divide] error : histograms have inconsistent bins");
                return null;
            }
            
            H2F h2div = new H2F(h1.getName()+"_DIV",
                    h1.getXAxis().getNBins(),h1.getXAxis().min(),h1.getXAxis().max(),
                    h1.getYAxis().getNBins(),h1.getYAxis().min(),h1.getYAxis().max()                    
            );
            for(int bx = 0; bx < h1.getXAxis().getNBins();bx++){
                for(int by = 0; by < h1.getYAxis().getNBins();by++){
                    double bc = 0;
                    if(h2.getBinContent(bx, by)!=0){
                        h2div.setBinContent(bx, by, h1.getBinContent(bx, by)/h2.getBinContent(bx, by));
                    }
                }    
            }
            return h2div;
        }
        
        public void divide(H2F h){
            if(h.getXAxis().getNBins()==this.getXAxis().getNBins()&&
                    h.getYAxis().getNBins()==this.getYAxis().getNBins()){
                for(int loop = 0; loop < this.hBuffer.length; loop++){
                    if(h.hBuffer[loop]==0){
                        this.hBuffer[loop] = 0.0f;
                    } else {
                        this.hBuffer[loop] = this.hBuffer[loop]/h.hBuffer[loop];
                    }
                }
            } else {
                System.err.println("[H2D::divide] error the bins in 2d histogram do not match");
            }
        }
	/**
	 * Finds which bin has that value.
	 * 
	 * @param x
	 *            The x value to search for
	 * @param y
	 *            The y value to search for
	 * @return The bin, in array indexing format, which holds that x-y value
	 */
	public int findBin(double x, double y) {
		int bx = xAxis.getBin(x);
		int by = yAxis.getBin(y);
		if (this.isValidBins(bx, by)) {
			return (offset.getArrayIndex(bx, by));
		}
		return -1;
	}

	/**
	 * Generates a 2D array with the content in the histogram
	 * 
	 * @return a 2D array with each bin in its array index
	 */
	public double[][] getContentBuffer() {
		double[][] buff = new double[xAxis.getNBins()][yAxis.getNBins()];
		for (int xloop = 0; xloop < xAxis.getNBins(); xloop++) {
			for (int yloop = 0; yloop < yAxis.getNBins(); yloop++) {
				buff[xloop][yloop] = this.getBinContent(xloop, yloop);
			}
		}
		return buff;
	}

	/**
	 * Creates an error buffer with each element being 0.0
	 * 
	 * @return a double 2D array with a size of xAxis * yAxis with each element
	 *         being 0.0
	 */
	public double[][] getErrorBuffer() {
		double[][] buff = new double[xAxis.getNBins()][yAxis.getNBins()];
		for (int xloop = 0; xloop < xAxis.getNBins(); xloop++) {
			for (int yloop = 0; yloop < yAxis.getNBins(); yloop++) {
				buff[xloop][yloop] = 0.0;
			}
		}
		return buff;
	}

	/**
	 * Specifies the region in the 2D histogram with those attributes
	 * 
	 * @param name
	 *            The name of the histogram
	 * @param bx_start
	 *            The x coordinate beginning
	 * @param bx_end
	 *            The x coordinate end
	 * @param by_start
	 *            The y coordinate beginning
	 * @param by_end
	 *            The y coordinate end
	 * @return A 2D histogram with the entered specifications
	 */
	public H2F getRegion(String name, int bx_start, int bx_end,
			int by_start, int by_end) {
		double xBinWidth = xAxis.getBinWidth(bx_start);
		double newXMin = xAxis.min() + (xBinWidth * bx_start);
		double newXMax = xAxis.min() + (xBinWidth * bx_end);

		double yBinWidth = yAxis.getBinWidth(by_start);
		double newYMin = yAxis.min() + (yBinWidth * by_start);
		double newYMax = yAxis.min() + (yBinWidth * by_end);
		H2F regHist = new H2F(name, bx_end - bx_start, newXMin,
				newXMax, by_end - by_start, newYMin, newYMax);

		double content = 0.0;
		for (int y = by_start; y < by_end; y++) {
			for (int x = bx_start; x < bx_end; x++) {
				content = this.getBinContent(x, y);
				regHist.setBinContent(x, y, content);
			}
		}
		return regHist;
	}
        
        public H2F histClone(String name){
            H2F hclone = new H2F(name,
                    this.xAxis.getNBins(),this.xAxis.min(),this.xAxis.max(),
                    this.yAxis.getNBins(),this.yAxis.min(),this.yAxis.max()
            );
            for(int loop = 0; loop < this.hBuffer.length; loop++){                
                hclone.hBuffer[loop] = this.hBuffer[loop];
            }
            return hclone;
        }
        
        public GraphErrors  getProfileX(){
            GraphErrors graph = new GraphErrors();
            int nbinsX = this.getXAxis().getNBins();
            for(int loop = 0 ; loop < nbinsX; loop++){
                H1F h1 = this.sliceX(loop);
                double mean = h1.getMean();
                double rms  = h1.getRMS();
                //System.out.println("MEAN = " + mean + "  RMS = " + rms);
                double bincenter = this.getXAxis().getBinCenter(loop);
                if(h1.integral()>1.0){
                    graph.addPoint(bincenter, mean, this.getXAxis().getBinWidth(loop)/2.0, rms);
                }
            }
            return graph;
        }
        
        public GraphErrors  getProfileY(){
            GraphErrors graph = new GraphErrors();
            int nbinsY = this.getYAxis().getNBins();
            for(int loop = 0 ; loop < nbinsY; loop++){
                H1F h1 = this.sliceY(loop);
                double mean = h1.getMean();
                double rms  = h1.getRMS();
                //System.out.println("MEAN = " + mean + "  RMS = " + rms);
                double bincenter = this.getYAxis().getBinCenter(loop);
                graph.addPoint(bincenter, mean, 0.0, rms);
            }
            return graph;
        }
        
	/**
	 * Creates a projection of the 2D histogram onto the X Axis, adding up all
	 * the y bins for each x bin
	 * 
	 * @return a H1F object that is a projection of the Histogram2D
	 *         object onto the x-axis
	 */
	public H1F projectionX() {
		String name = "X Projection";
		double xMin = xAxis.min();
		double xMax = xAxis.max();
		int xNum = xAxis.getNBins();
		H1F projX = new H1F(name, xNum, xMin, xMax);

		double height = 0.0;
		for (int x = 0; x < xAxis.getNBins(); x++) {
			height = 0.0;
			for (int y = 0; y < yAxis.getNBins(); y++) {
				height += this.getBinContent(x, y);
			}
			projX.setBinContent(x, height);
		}

		return projX;
	}

	/**
	 * Creates a projection of the 2D histogram onto the Y Axis, adding up all
	 * the x bins for each y bin
	 * 
	 * @return a H1F object that is a projection of the Histogram2D
	 *         object onto the y-axis
	 */
	public H1F projectionY() {
		String name = "Y Projection";
		double yMin = yAxis.min();
		double yMax = yAxis.max();
		int yNum = yAxis.getNBins() ;
		H1F projY = new H1F(name, yNum, yMin, yMax);

		double height = 0.0;
		for (int y = 0; y < yAxis.getNBins(); y++) {
			height = 0.0;
			for (int x = 0; x < xAxis.getNBins(); x++) {
				height += this.getBinContent(x, y);
			}
			projY.setBinContent(y, height);
		}

		return projY;
	}

	/**
	 * Creates a 1-D Histogram slice of the specified y Bin
	 * 
	 * @param xBin		the bin on the y axis to create a slice of
	 * @return 			a slice of the x bins on the specified y bin as a 1-D Histogram
	 */
	public H1F sliceX(int xBin) {
		String name = "Slice of " + xBin + " X Bin";
		double xMin = yAxis.min();
		double xMax = yAxis.max();
		int xNum    = yAxis.getNBins();
		H1F sliceX = new H1F(name, name, xNum, xMin, xMax);

		for (int x = 0; x < xNum; x++) {
			sliceX.setBinContent(x, this.getBinContent(xBin,x));
		}
		return sliceX;
	}

	/**
	 * Creates a 1-D Histogram slice of the specified x Bin
	 * 
	 * @param yBin			the bin on the x axis to create a slice of
	 * @return 				a slice of the y bins on the specified x bin as a 1-D Histogram
	 */
	public H1F sliceY(int yBin) {
		String name = "Slice of " + yBin + " Y Bin";
		double xMin = xAxis.min();
		double xMax = xAxis.max();
		int    xNum = xAxis.getNBins();
		H1F sliceY = new H1F(name, name, xNum, xMin, xMax);

		for (int y = 0; y < xNum; y++) {
			sliceY.setBinContent(y, this.getBinContent(y,yBin));
		}

		return sliceY;
	}

	public float[] offset() {
		return hBuffer;
	}
        /**
         * Resets the content of the histogram, sets all bin contents to 0
         */
        public void reset(){
            for(int bin = 0; bin < this.hBuffer.length; bin++){
                this.hBuffer[bin] = 0.0f;
            }
        }

    @Override
    public int getDataSize(int axis) {
        if(axis==0) {
            return this.xAxis.getNBins();
        } else {
            return this.yAxis.getNBins();
        }
    }

    @Override
    public DatasetAttributes getAttributes() {
        return this.attr;
    }

    @Override
    public double getDataX(int bin) {
        return this.xAxis.getBinCenter(bin);
    }

    @Override
    public double getDataY(int bin) {
        return this.yAxis.getBinCenter(bin);
    }

    @Override
    public double getDataEX(int bin) {
        return this.xAxis.getBinWidth(bin);
    }

    @Override
    public double getDataEY(int bin) {
        return this.yAxis.getBinWidth(bin);
    }

    @Override
    public double getData(int xbin, int ybin) {
        return this.getBinContent(xbin, ybin);
    }

    @Override
    public PaveText getStatBox() {
        return new PaveText(2);
    }

}
