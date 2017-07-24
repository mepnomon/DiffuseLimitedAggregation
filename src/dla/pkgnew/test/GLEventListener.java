package dla.pkgnew.test;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import java.util.Random;


/**
 * ICP-2036
 * Assessment 2: Diffused Limited Aggregation
 * Class: GLEventListener
 * implements GL Event Listener
 * Contains Logic for Diffused Limited Aggregation.
 * @author cgray, EEU436
 */
public class GLEventListener implements com.jogamp.opengl.GLEventListener {
    
    private int rows = 300;
    private int cols = 300;
    ParticleData particle;
    ParticleData particles[][] = new ParticleData[rows][cols];
    boolean lattice[][] = new boolean[rows][cols];
    Random rand = new Random();
    private int seedX = 149;
    private int seedY = 149;
    private int maxOffset = 260;
    private int minOffset = 10;
    private int dX;
    private int dY;
    private int pixelsToAggregate = 0;
    private int pixelsAggregated = 0;
    double killRadius = 0; //140 max
    
    private int distanceTraveled;
    private int dlaChoice = 0;
    private boolean resetTestBoolean = false;
    
    int stuff = 0;
    
    
    protected void setup(GL2 gl2, int width, int height) {
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();

        // coordinate system origin at lower left with width and height same as the window
        GLU glu = new GLU();
        glu.gluOrtho2D(0.0f, width, 0.0f, height);

        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();
        
        gl2.glViewport(0, 0, width, height);
        
    }

    protected void render(GL2 gl2, int width, int height) {
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);
        if(resetTestBoolean){
            gl2.glClear(gl2.GL_COLOR_BUFFER_BIT | gl2.GL_DEPTH_BUFFER_BIT);
        }
        if(dlaChoice == 1){
            generate4ConnectDLA();
            drawKillingCircle(gl2, seedX, seedY);
        }
        if(dlaChoice == 2){
            generate8ConnectDLA();
            drawKillingCircle(gl2, seedX, seedY);
        }
        if(dlaChoice == 3){
            generate8ConnectGroundDiffusion();
        }
        drawPixels(gl2); //event handler
        gl2.glLoadIdentity();
        gl2.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
        setup(glautodrawable.getGL().getGL2(), width, height);
    }

    @Override
    public void init(GLAutoDrawable glautodrawable) {
    }

    @Override
    public void dispose(GLAutoDrawable glautodrawable) {
    }

    @Override
    public void display(GLAutoDrawable glautodrawable) {
        render(glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight());
    }
    /**
     * Sets pixel to Aggregate in Program
     * @param particleCount 
     */
    public void setPixelsToAggregate(int particleCount){
        
        pixelsToAggregate = particleCount;
    }
    /**
     * Sets killing radius based on user input.
     * @param radius 
     */
    public void setKillingCircleRadius(int radius){
        
        killRadius = radius;
    }
    /**
     * Allows user to choose type of Aggregation.
     * @param choice 
     */
    public void selectDLAMethod(int choice){
        
        dlaChoice = choice;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return a String containing praticle information displayed in the gui.
     */
    public String getParticleInfo(int x, int y){
        try{
        particle = particles[x][y];
            String returnString = particle.retrieveParticleData();
            System.out.println(returnString);
            return returnString;
        }catch(Exception e){
            
            return "N/A";
        }
    }
    /**
     * returns number of Aggregated Pixels.
     * @return 
     */
    public int getAggregatedPixels(){
        return pixelsAggregated;
    }
    /**
     * Resets boolean lattice.
     */
    public void resetSimulation(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
               lattice[i][j] = false; 
            }
        }
         resetTestBoolean = true;
         //particle.resetParticleID();
    }
    /**
     * Method contains algorithm for 4-connect DLA
     * with Brownian Motion in 4 directions.
     * Calls aggregatePixel if successful.
     */
    void generate4ConnectDLA(){
        resetTestBoolean=false;
        System.out.println("4ConnectDLA");
        int brownianMotor = 0;
        aggregatePixel(seedX, seedY, 0, 0);
        for(int i = 1; i < pixelsToAggregate; i++){
            
            System.out.println(maxOffset);
            System.out.println(minOffset);
            
            dX = rand.nextInt(maxOffset - minOffset) + minOffset;
            dY = rand.nextInt(maxOffset - minOffset) + minOffset;
                    
            distanceTraveled = 0;
            while(!adjacencyDetectedFour(dX, dY)){
                brownianMotor = rand.nextInt(4);
                switch(brownianMotor){
                    case 0: dX++; break;
                    case 1: dX--; break;
                    case 2: dY++; break;
                    case 3: dY--; break;
                }
                if(!insideKillCircle(dX, dY, seedX, seedY)){
                    distanceTraveled=0;
                    killPixel();
                }else{
                    distanceTraveled++;
                }  
            }
            aggregatePixel(dX, dY,distanceTraveled, i);
        }
    }
    /**
     * Method contains algorithm for 8-connect DLA
     * with Brownian Motion in 8 directions.
     * Calls aggregatePixel if successful.
     */
    void generate8ConnectDLA(){
        System.out.println("8ConnectDLA");
        resetTestBoolean = false;
        int brownianMotor = 0;
        aggregatePixel(seedX, seedY, 0, 0); //put into run through boolean
        for(int i = 0; i < pixelsToAggregate; i++){
            
            dX = rand.nextInt(290 - 10) + 10;
            dY = rand.nextInt(290 - 10) + 10;
            distanceTraveled = 0;
            while(!adjacencyDetecetedEight(dX, dY)){
                brownianMotor = rand.nextInt(8);
                switch(brownianMotor){
                    case 0: dX++; break;
                    case 1: dX--; break;
                    case 2: dY++; break;
                    case 3: dY--; break;
                    case 4: dX++; dY++; break;
                    case 5: dX++; dY--; break;
                    case 6: dX--; dY++; break;
                    case 7: dX--; dY--; break;
                        
                        //finish
                }
                if(!insideKillCircle(dX, dY, seedX, seedY)){
                    distanceTraveled=0;
                    killPixel();
                }else{
                    distanceTraveled++;
                }
            }  
            aggregatePixel(dX, dY, distanceTraveled, pixelsAggregated++);
        }
    }
    /**
     * Creates 8 Connect Diffusion along a horizontal line at Y 10.
     * Brownian Motion in 8 directions.
     */
    private void generate8ConnectGroundDiffusion(){
        int groundX = 0;
        int groundY = 10;
        int brownianMotor = 0;
        for(int i = groundY; i < cols; i++){
            
            aggregatePixel(i, groundY, 0, 0);
        }
        for(int i = 0; i < pixelsToAggregate; i++){
            dX = rand.nextInt(maxOffset - 2*minOffset) + minOffset;
            dY = rand.nextInt(maxOffset - 2*minOffset) + minOffset;
            distanceTraveled = 0;
            while(!adjacencyDetecetedEight(dX, dY)){
                brownianMotor = rand.nextInt(8);
                switch(brownianMotor){
                    case 0: dX++; break;
                    case 1: dX--; break;
                    case 2: dY++; break;
                    case 3: dY--; break;
                    case 4: dX++; dY++; break;
                    case 5: dX++; dY--; break;
                    case 6: dX--; dY++; break;
                    case 7: dX--; dY--; break;
                        
                        //finish
                }if(dX<10 || dX > 290 || dY < 10 || dY> 290){
                    distanceTraveled=0;
                    killPixel();
                }else{
                    distanceTraveled++;
                }
                //check if out of bounds and kill if so
            }
            aggregatePixel(dX, dY, distanceTraveled, pixelsAggregated++);
        }       
    }
    /**
     * Checks if adjacency is true in 4 directions.
     * Used in generateFourConnectDLA
     * @param x
     * @param y
     * @return 
     */
    private  boolean adjacencyDetectedFour(int x, int y){
        /*
            stuff++;
            if(stuff % 500 == 0){
                return true;
            }else{
                return false;
            }
            the above was just for viewing the motion itself
            left it in for entertainment.
        */
        return lattice[x][y+1] ||
               lattice[x+1][y] ||
               lattice[x-1][y] ||
               lattice[x][y-1];
    }
    /**
     * Checks if adjacency is true in 8 directions.
     * Used in generateFourConnectDLA.
     * @param x
     * @param y
     * @return 
     */
    private boolean adjacencyDetecetedEight(int x, int y){
        //System.out.println(x + " " + y);
        return lattice[x][y+1]   ||
               lattice[x+1][y] ||
               lattice[x-1][y]   ||
               lattice[x][y-1] ||
               lattice[x+1][y+1]   ||
               lattice[x+1][y-1] ||
               lattice[x-1][y-1]   ||
               lattice[x-1][y+1];              
    }
    /**
     * Checks if pixel traverses outside the radius of the killCircle.
     * @param x
     * @param y
     * @param seedX
     * @param seedY
     * @return 
     */
    private boolean insideKillCircle(int x, int y, int seedX, int seedY){
        double x2;
        double y2;
        double result;
        double sqrtResult;
        
        x2 = Math.pow((seedX - x),2);
        y2 = Math.pow((seedY - y),2);
        result = x2 + y2;
        result = Math.sqrt(result);
        return result < killRadius;
    }
    /**
     * Generates a new set of random values.
     * "Kills" previous values
     * Also prevents pixels from traversing out of the screen.
     */
    private void killPixel(){
        //if killPixel == true, drop out
        dX = rand.nextInt(maxOffset - minOffset) + minOffset; //kill magic numbers
        dY = rand.nextInt(maxOffset - minOffset) + minOffset;
    }
    /**
     * Places a pixel when neighbor is true.
     * Assigns value to ParticleData object.
     * sets Boolean lattice location true if added.
     * @param x
     * @param y
     * @param distanceTraveled
     * @param pixelsSoFarAggregated 
     */
    private void aggregatePixel(int x, int y, int distanceTraveled, int pixelsSoFarAggregated){
        
        
        particle = new ParticleData(x, y,distanceTraveled, pixelsSoFarAggregated,pixelsToAggregate);
        particles[x][y] = particle;
        lattice[x][y] = true;
    }
  
    /**
     * Iterates through the boolean array.
     * Draws pixel at x,y if boolean is true.
     * Retrieves RGB values for pixel from ParticleData.
     * @param gl2 
     */
    private void drawPixels(GL2 gl2){
        
        //pixelsAggregated = 0;
        float color = 1;
        float color2 = 0;
        gl2.glBegin(GL2.GL_POINTS);
        //change color in relation to iteration cycles
        for(int i = 0; i < 300; i++){
            if(i % 50 == 0){
                color -= 0.1;
                color2 +=0.1;
            }
            for(int j = 0; j <300; j++){
                
                if(lattice[i][j]){
                    particle = particles[i][j];
                    gl2.glColor3f(particle.getRedValue(), particle.getGreenValue(), particle.getBlueValue()); //get color depending on particle
                    gl2.glVertex2d(i, j);
                    pixelsAggregated++;
                }
            }
        }
        gl2.glEnd();
        System.out.println("No. of pixels:" + pixelsAggregated);
    }
    /**
     * Draws the killing circle.
     * @param gl2
     * @param seedX
     * @param seedY 
     */
    private void drawKillingCircle(GL2 gl2,int seedX,int seedY){
        gl2.glBegin(GL2.GL_POINTS);
        gl2.glColor3f(1, 0, 0);
        gl2.glPointSize(2);
        double x, y;
        double rad = killRadius;
        for(int j = 0; j < 360; j++){
            
            x = Math.cos(Math.toRadians(j))*rad;
            y = Math.sin(Math.toRadians(j))*rad; 
            
            gl2.glVertex2d(seedX + x, seedY + y);
        }
    }
}
