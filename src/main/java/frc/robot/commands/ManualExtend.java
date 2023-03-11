// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ManualExtend extends CommandBase {
  /** Creates a new ManualExtend. */
  Arm arm;
  double power;
  double counts;

  public ManualExtend(Arm arm, double power) {
    this.arm = arm;
    this.power = power;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double counts = arm.getArmPosition();
    arm.testArm(power);
      System.out.println(counts);
        
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    // if (counts > 356000  || counts < -1)
    // {
    //   arm.testArm(0.0);
    //   return true;
    // }
    //     // {

    //     //   armMotor.set(ControlMode.PercentOutput, 0);
    //     // }
    //     // else if (power > 0 && armMotor.getSelectedSensorPosition() >= 356000)
    //     // {
    //     //   armMotor.set(ControlMode.PercentOutput, 0);
    //     // }
    //     // else
    //     else{
    // return false;
    //     }
  }
}
