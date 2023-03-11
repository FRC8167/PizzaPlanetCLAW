// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Grabber extends SubsystemBase {
    private final DoubleSolenoid grabberPiston;
    
    private boolean isOpen;
    
    /** Creates a new Grabber Subsystem. */
    public Grabber() {
        grabberPiston = new DoubleSolenoid(
            0, 
            PneumaticsModuleType.CTREPCM, 
            Constants.SOLENOID_ID1, 
            Constants.SOLENOID_ID2
        );
        
        isOpen = false;
    }
    
    // Methods for controlling the state of the double solenoid
    /** Opens the grabber (fairly self explanatory) */
    public void openGrabber() {
        grabberPiston.set(Value.kForward);
        isOpen = true;
    }
    
    /** Closes the grabber (also very self explanatory) */
    public void closeGrabber() {
        grabberPiston.set(Value.kReverse);
        isOpen = false;
    }
    
    /**
     * Toggles the state of the grabber. (i.e. if it's open, it gets closed, and vice versa)
     */
    public void toggleGrabber() {
        if (isOpen) {
            closeGrabber();
        } else {
            openGrabber();
        }
    }
    
    @Override
    public void periodic() {}
}
