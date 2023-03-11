// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pivot;

public class SetPivotAngle extends CommandBase {
    private final Pivot pivot;
    private final double pivotTarget;
    private double pivotStartTime;
    /** Creates a new SetPivotAngle. */
    public SetPivotAngle(Pivot pivot, double pivotTargetDegrees) {
        this.pivot = pivot;
        this.pivotTarget = pivotTargetDegrees/360;    //allows user input units of degrees (zero for sensor is 45 in real build)
        addRequirements(pivot);
        // Use addRequirements() here to declare subsystem dependencies.
    }
    
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        pivotStartTime = Timer.getFPGATimestamp();
        // pivot.zeroPivotSensor();
    }
    
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      pivot.setPivotMotionMagic(pivotTarget);
    }
    
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        // TODO: again, why are all these commented out?
        // pivot.stop();
    }
    
    private boolean isPivotMotionMagicDone() {
        double pivotSensorAngle = pivot.getPivotPosition();
        // TODO: what is this `pivotTarget*80*2048` quantity? why does it keep showing up?
        double error = pivotSensorAngle - pivotTarget*80*2048;
        System.out.println("Pivot sensor = " + pivotSensorAngle + ", pivotTarget= " + (pivotTarget*80*2048));
        double percentErr = Math.abs(error)/Math.abs(pivotTarget*80*2048);
        if (percentErr < 0.05) { // TODO: maybe add a Constants.PIVOT_PERCENT_ERROR_THRESHOLD
            return true;
        }
        
        double timePassed = Timer.getFPGATimestamp() - pivotStartTime;
        if (timePassed > 5.0) { // TODO: add Constants.SET_PIVOT_ANGLE_TIMEOUT
            return true;
        }
        
        System.out.println("Pivot Percent Error= " + percentErr);// + ",time passed= " + timePassed);
        
        return false;
    }
    
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isPivotMotionMagicDone();
    }
}
