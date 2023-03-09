// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Pivot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Shelf1Positioning extends SequentialCommandGroup {
  /** Creates a new FirstShelfPositioning. */
  //Tested
  Arm arm;
  Pivot pivot;
  public Shelf1Positioning(Arm arm, Pivot pivot) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SetPivotAngle(pivot, Constants.PIVOT_SHELF1_45),
      new SetArmDistance(arm, Constants.ARM_SHELF1_21)
    );
  }
}
