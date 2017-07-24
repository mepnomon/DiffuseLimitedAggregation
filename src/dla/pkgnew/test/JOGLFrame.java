package dla.pkgnew.test;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * ICP-2036
 * Assessment 2: Diffused Limited Aggregation
 * Class: JOGLFrame
 * JOGLFrame subclasses JFrame
 * Is a Frame and contains Graphic User Interface with ActionListeners.
 * Displays JOGLFrame
 * Only relevant areas commented.
 * @author cgray, dbdressler
 */
public class JOGLFrame extends JFrame {
    public static GLEventListener canvasListener = new GLEventListener();
    public static final GLProfile glprofile = GLProfile.getDefault();
    public static final GLCapabilities glcapabilities = new GLCapabilities( glprofile );
    public static final GLCanvas glcanvas = new GLCanvas( glcapabilities );
    
    private static JOGLFrame instance;
    private static JComboBox dlaChoice;
    private static JButton aggregateButton;
    private static JButton adder;
    private static JButton remover;
    private static JButton resetButton;
    private static JPanel buttonsPanel;
    private static JPanel informationPanel;
    private static JTextField returnStringField;
         
    private static JLabel particleCount;
    private static JLabel radiusSize;
    private static ButtonGroup group;
    private static JRadioButton particleCntSelector;
    private static JRadioButton radiusSizeSelector;
    private static int rows = 300;
    private static int cols = 300;
    ParticleData particle = new ParticleData();
    ParticleData[][] particles = new ParticleData[rows][cols];
    
    public static boolean particleSelect = false;
    public static boolean radiusSelect = false;   
    private static String[] choicesString = {"4-Connect", "8-Connect", "8-Growth"};
    
    private static int particleCounter = 0;
    private static String currentPartCount;
    private static int radius = 100;
    private static String radiusString;
    /**
     * Constructor for JOGLFrame
     * @throws HeadlessException 
     */
    public JOGLFrame() throws HeadlessException {
        
        setPreferredSize(new Dimension(450, 400));
        setResizable(false);
//        GLProfile glprofile = GLProfile.getDefault();
//        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
//        final GLCanvas glcanvas = new GLCanvas( glcapabilities );
        glcanvas.setPreferredSize(new Dimension(300,300));
        glcanvas.addGLEventListener(canvasListener);
        getContentPane().add(glcanvas);
        
        
        //getContentPane().add(dropDownAndAggregate()); //breaks it
        //pack();
    }   
    
    public static void main(String[] args) {
        //ComboBoxListener
        class ButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent ae) {
                // + button
                if(ae.getActionCommand().equals("+")){
                    
                    if(particleSelect){
                        
                        if((particleCounter <= 30000) && (particleCounter >= 0)){
                            particleCounter = particleCounter + 500;
                            canvasListener.setPixelsToAggregate(particleCounter);
                            System.out.println("Particles: " + particleCounter);
                            currentPartCount = Integer.toString(particleCounter);
                            particleCount.setText("Particles:" + currentPartCount);
                            returnStringField.setText(radiusString);
                            
                        }
                    }
                    if(radiusSelect){
                        
                        if((radius >= 30) && (radius <=135)){
                            
                            radius = radius + 5;
                            canvasListener.setKillingCircleRadius(radius);
                            radiusString = Integer.toString(radius);
                            radiusSize.setText("Radius: " + radiusString);
                        }
                    }
                }
                // - button
                if(ae.getActionCommand().equals("-")){
                    
                    if(particleSelect){
                        
                        if((particleCounter <= 35000) && (particleCounter >= 500)){
                            
                            particleCounter = particleCounter - 500;
                            canvasListener.setPixelsToAggregate(particleCounter);
                            //System.out.println("Particles: " + particleCounter);
                            currentPartCount = Integer.toString(particleCounter);
                            particleCount.setText("Particles:" + currentPartCount);
                            
                        }
                    }
                    if(radiusSelect){
                        
                        if((radius >= 35) && (radius <=140)){
                            
                            radius = radius - 5;
                            canvasListener.setKillingCircleRadius(radius);
                            radiusString = Integer.toString(radius);
                            radiusSize.setText("Radius:" + radiusString);
                        }
                    }
                }
                //Add button
                if(ae.getActionCommand().equals("Add")){
                    
                    canvasListener.setPixelsToAggregate(particleCounter);
                    //try feeding dla method here
                    canvasListener.setKillingCircleRadius(radius);
                    instance.setSize(new Dimension(450,401));
                    instance.setSize(new Dimension(450,400));
                }
                //Re-draw button
                if(ae.getActionCommand().equals("Re-draw")){
                    
                    canvasListener.resetSimulation();
                    instance.setSize(new Dimension(450,401));
                    instance.setSize(new Dimension(450,400));
                    //glcanvas.destroy();
                }
            }
        }

        class RadioButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent ae) {
                
                if(ae.getActionCommand().equals("Particles")){
                    particleSelect = true;   
                    radiusSelect = false;
                }
                if(ae.getActionCommand().equals("Radius")){
                    
                    particleSelect = false;
                    radiusSelect = true;
                }   
            }
        }
        
        class MouseEventListener implements MouseListener{

            @Override
            public void mouseEntered(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                System.out.println("Mouse Entered");
                //mouseEntered = true;
                int x, y;
//                while(mouseEntered){
//                    failed attempt at having continuous MouseListener
//                    that triggers the JTextField 
//                    x=me.getX();
//                    y=me.getY();
//                    //if()
//                    //System.out.println("X" + x);
//                    //System.out.println("Y" + y);
//                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //mouseEntered = false;
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            /**
             * Listens for a particle to be clicked on and returns information about it.
             * @param me 
             */
            @Override
            public void mouseClicked(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 int x=me.getX();
                 int y=me.getY();
                 System.out.println("Clicked at:" + x + " " + y);
                 String testString = canvasListener.getParticleInfo(x, y);
                 returnStringField.setText(testString);
            }

            @Override
            public void mousePressed(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        }
        
        class ComboBoxListener implements ActionListener{
            /**
             * Allows user to choose DLA method.
             * @param ae 
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
             
                String s = (String)dlaChoice.getSelectedItem();
                
                switch(s){
                    case "4-Connect": canvasListener.selectDLAMethod(1); break;
                    case "8-Connect": canvasListener.selectDLAMethod(2); break;
                    case "8-Growth":  canvasListener.selectDLAMethod(3); break;
                    default: canvasListener.selectDLAMethod(1);break;
                }
            }
            
        }
        /**
         * GUI
         */
        //Attaching Mouse Listener to GLCanvas
        MouseListener mouseEventListener = new MouseEventListener();
        instance = new JOGLFrame();//attach mouselistener
        glcanvas.addMouseListener(mouseEventListener);
        instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ActionListener buttonListener = new ButtonListener();
        
        //south panel
        ActionListener comboListener = new ComboBoxListener();
        GridBagConstraints c1 = new GridBagConstraints();
        c1.anchor = GridBagConstraints.WEST;
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        
        dlaChoice = new JComboBox(choicesString);
        c1.fill = GridBagConstraints.VERTICAL;
        c1.gridx = 0;
        c1.gridy = 0;
        dlaChoice.addActionListener(comboListener);
        buttonsPanel.add(dlaChoice, c1);
        
        aggregateButton = new JButton("Add");
        aggregateButton.addActionListener(buttonListener);
        c1.gridy = 1;
        buttonsPanel.add(aggregateButton, c1);  
        
        resetButton = new JButton("Re-draw");
        resetButton.addActionListener(buttonListener);
        c1.gridy=2;
        buttonsPanel.add(resetButton, c1);

        //Radio buttons
        ActionListener radioButtonListener = new RadioButtonListener();
        particleCntSelector = new JRadioButton("Particles");
        radiusSizeSelector = new JRadioButton("Radius");
        particleCntSelector.addActionListener(radioButtonListener);
        radiusSizeSelector.addActionListener(radioButtonListener);
        c1.gridy = 1;
        c1.gridx = 1;
        group = new ButtonGroup();
        group.add(particleCntSelector);
        group.add(radiusSizeSelector);
        buttonsPanel.add(particleCntSelector);
        buttonsPanel.add(radiusSizeSelector, c1);
        
        adder = new JButton("+");
        adder.addActionListener(buttonListener);
        

        c1.gridx = 2;
        c1.gridy = 0;
        buttonsPanel.add(adder, c1);
        
        remover = new JButton("-");
        remover.addActionListener(buttonListener);
        c1.gridy = 1;
        buttonsPanel.add(remover, c1);       
        particleCount = new JLabel("Particles: " + particleCounter); //some var
        c1.gridx=3;
        c1.gridy=0;
        buttonsPanel.add(particleCount, c1);
        
        radiusSize = new JLabel("Radius: " + radius);
        c1.gridy = 1;
        buttonsPanel.add(radiusSize, c1);
       
        informationPanel = new JPanel(new BorderLayout());
        returnStringField = new JTextField();
        informationPanel.add(returnStringField);
        
        
        instance.add(informationPanel, BorderLayout.SOUTH);
        instance.add(buttonsPanel, BorderLayout.NORTH);
        instance.pack();
        instance.setVisible(true);
    }
}
