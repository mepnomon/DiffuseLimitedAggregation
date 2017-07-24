package dla.pkgnew.test;
import java.util.Calendar;

/**
 * ICP-2036
 * Assessment 2: Diffused Limited Aggregation
 * Class: Particle Data
 * Contains Data for pixels.
 * @author EEU436
 */
public class ParticleData {
   
    Calendar cal = Calendar.getInstance();
    //would have been used to determine aggregation time
    //float currenTime =System.currentTimeMillis();
    
    private float localRed;
    private float localGreen;
    private float localBlue;
    private int x;
    private int y;
    static private int particleIDIncrementor;
    private int particleID;
    private int cycleOrigin;
    private int distanceTraveled;
    private String particleString;
    private int pixelsToAggregate;
    private int pixelsSoFarAggregated;
    private int allPixels;
    
    /**
     * Zero parameter constructor;
     */
    ParticleData(){
        
        particleString = "Null";
        particleID = 0;
    }
    
    /**
     * Constructor for ParticleData Object
     * @param x coordinate
     * @param y coordinate
     * @param cycleOrigin cycle in which it was created
     */
    ParticleData(int x, int y, int distanceTraveled, int pixelsSoFarAggregated, int pixelsToAggregate){
        
        particleID = particleIDIncrementor++;
        this.pixelsToAggregate = pixelsToAggregate;
        this.cycleOrigin = cycleOrigin;
        this.distanceTraveled = distanceTraveled;
        this.x = x;
        this.y = y;
        
        setParticleColor(pixelsSoFarAggregated);
    }
    
    /**
     * Determines RGB values based on number of pixels so far aggregated.
     * @param pixelsSoFarAggregated 
     */
    private void setParticleColor(int pixelsSoFarAggregated){
        localRed = 0; 
        localGreen = 0;
        localBlue = 0;
        int pixelColorRatio = pixelsToAggregate/3;
        if(pixelsSoFarAggregated < pixelColorRatio){
            localRed = 1f;
            localGreen = 0f;
            localBlue = 0f;
        }
        if(pixelsSoFarAggregated > pixelColorRatio){
            localRed = 0f;
            localGreen = 1f;
            localBlue = 0f;
        }
        if(pixelsSoFarAggregated > pixelColorRatio*2){
            localRed = 0f;
            localGreen = 0f;
            localBlue = 1f;
            
        }
    }
    /**
     * 
     * @return particleID
     */
    public int getParticleID(){
        
        return particleID;
    }
    /**
     * 
     * @return red value inherent to the ParticleData Object.
     */
    public float getRedValue(){
        
        if(particleID == 0){
            return 0f;
        }else{
            return localRed;
        }
    }
    /**
     * 
     * @return green value inherent to the ParticleData Object.
     */
    public float getGreenValue(){
        
        if(particleID == 0){
            return 0f;
        }else{
            return localGreen;
        }
    }
    /**
     * 
     * @return blue value inherent to the ParticleData Object.
     */
    public float getBlueValue(){
        if(particleID == 0){
            return 0f;
        }else{
            return localBlue;
        }
    }
    /**
     * 
     * @return a String containing information displayed in the GUI.
     */
    public String retrieveParticleData(){
        String returnString = Integer.toString(particleID);
        return String.format("Particle ID:" + particleID + " Distance:" + distanceTraveled);
    }
    
    /**
    *   Resets particleIDIncrementor when Reset is called
    *   and a new image is drawn.
    */
    public void resetParticleID(){
       //call this in eventlistener 
        particleIDIncrementor = 0;
    }   
}
