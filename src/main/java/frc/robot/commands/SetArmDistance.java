// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class SetArmDistance extends CommandBase {
    /** Creates a new SetArmDistance. */
    private final Arm arm;
    private final double armTarget;
    private double armStartTime;
    
    
    public SetArmDistance(Arm arm, double armTargetInches) {
        this.arm = arm;
        //this.armTarget = armTarget;
        this.armTarget = armTargetInches/(0.787*Math.PI);    //allows input of user units in inches
        addRequirements(arm);
        // Use addRequirements() here to declare subsystem dependencies.
    }
    
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        armStartTime = Timer.getFPGATimestamp();
        //arm.zeroArmSensor();
    }
    
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        arm.setArmMotionMagic(armTarget);
    }
    
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        // TODO: why is this commented out?
        // arm.stop();
    }
    
    private boolean isArmMotionMagicDone() {
        double armSensorDistance = arm.getArmPosition();
        double error = armSensorDistance - armTarget*20*2048;
        
        System.out.println("Sensor ="+armSensorDistance + ", armTarget = " + (armTarget * 20 * 2048));
        
        double percentErr = Math.abs(error)/Math.abs(armTarget*20*2048);
        if(percentErr < 0.05) {
            return true;
        }
        double timepassed = Timer.getFPGATimestamp() - armStartTime;
        if (timepassed > 3.5) { // TODO: add Constants.SET_ARM_DISTANCE_TIMEOUT
            return true;
        }
        
        System.out.println("percent error = "+percentErr +", time passed = "+timepassed);
        
        return false;
    }
    
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isArmMotionMagicDone();
    }
}
