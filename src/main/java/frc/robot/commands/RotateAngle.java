// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class RotateAngle extends CommandBase {
  public static final double motorTicksPerFoot = 13994.16;  //adjust this using wheelbase
  private final Drivetrain drivetrain;
  private final double distance;
  private double turnStartTime;
  /** Creates a new RotateAngle. */
  public RotateAngle(Drivetrain drivetrain, double turnAngle) {
    // Use addRequirements() here to declare subsystem dependencies.
    distance = ((turnAngle*22*Math.PI)/360*12) * motorTicksPerFoot;  //adjust this using wheelbase
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);

  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turnStartTime = Timer.getFPGATimestamp();
    drivetrain.setTurnMotionMagic(distance, 10000, 4000);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stopTurnMotionMagic();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() > turnStartTime + 5 || drivetrain.isTurnMagicMotionDone(distance);
  }
}
