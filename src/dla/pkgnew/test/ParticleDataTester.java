package dla.pkgnew.test;

/**
 *
 * @author Mepnomon
 */
public class ParticleDataTester {
    
    static ParticleData particle;
    static ParticleData particleArray[] = new ParticleData[100];
    /*
      
    */
    
    public static void main(String[] args){
    
        for(int i = 0; i < particleArray.length; i++){
            
            if(i < 50){
                particle = new ParticleData();
                particleArray[i] = particle;
            }else{
                //particle = new ParticleData(i, i, i, i, 0, 0, 1);
                particleArray[i] = particle;
            }
        }
        
        for (ParticleData particleArray1 : particleArray) {
            particle = particleArray1;
            System.out.println("ParticleID: " + particle.getParticleID());
            System.out.println("red: " + particle.getRedValue());
            System.out.println("green: " + particle.getGreenValue());
            System.out.println("blue: " + particle.getBlueValue());
        }
        particle = particleArray[99];
        String particleDataString = particle.retrieveParticleData();
        System.out.println("Particle data:" + particleDataString);
        
    }
    
}
