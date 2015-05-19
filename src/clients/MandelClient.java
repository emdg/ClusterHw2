package clients;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import java.io.File;


import tasks.MandelJob;
import tasks.MandelResult;

/**
 *
 * @author Peter Cappello
 */
public class MandelClient extends Client<Integer[][], MandelResult>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double LOWER_LEFT_X = -2.0;
    private static final double LOWER_LEFT_Y = -2.0;
    private static final double EDGE_LENGTH = 10.0;
    private static final int N_PIXELS = 1024;
    private static final int ITERATION_LIMIT = 1024;
    private static final int numOfImgs = 1000;
    private static int currentImg = 0;
    private static double cel = EDGE_LENGTH;
    private static double clx = LOWER_LEFT_X;
    private static double cly = LOWER_LEFT_Y;
    private static double deltaEL = 0.4;


    public MandelClient(String domain) throws RemoteException, NotBoundException, MalformedURLException 
    { 
        super( "Mandelbrot Set Visualizer", domain, new MandelJob( clx, cly, cel, N_PIXELS, 
                                                       ITERATION_LIMIT, 1, "mandel") ); 
    }
    
    /**
     * Run the MandelbrotSet visualizer client.
     * @param args unused 
     * @throws java.rmi.RemoteException 
     */
    public static void main( String[] args ) throws Exception
    {  
        System.setSecurityManager( new SecurityManager() );
        
        String domain;
        if(args.length == 0){
        	domain = "localhost";
        }
        else {
        	domain = args[0];
        }
        
        for (int i = 0; i < numOfImgs; i++){
            final MandelClient client = new MandelClient(domain);
            //client.begin();
            Integer[][] value = client.processJob();
            System.out.println(value.length);
            client.getLabel( value );
            cel -= deltaEL;
            clx *= cel;
            cly *= cel;
            currentImg += 1;
        }
        //client.end();
    }
    
    public JLabel getLabel( Integer[][] counts )
    {
        final Image image = new BufferedImage( N_PIXELS, N_PIXELS, BufferedImage.TYPE_INT_ARGB );
        final Graphics graphics = image.getGraphics();
        
        
        
        for ( int i = 0; i < counts.length; i++ )
            for ( int j = 0; j < counts.length; j++ )
            {
                graphics.setColor( getColor( counts[i][j] ) );
                graphics.fillRect( i, N_PIXELS - j, 1, 1 );
            }
        final ImageIcon imageIcon = new ImageIcon( image );
        File f = new File("imgs/" + currentImg + ".png");
        BufferedImage img = toBufferedImage(image); 
        try {
            ImageIO.write(img, "png", f);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return new JLabel( imageIcon );
    }


    private BufferedImage toBufferedImage(Image src) {
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_RGB;  // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }
    
    private Color getColor( int iterationCount )
    {

        return new Color(iterationCount % 32 * 8, iterationCount % 8 * 32 , iterationCount % 16 * 16);
    }
}