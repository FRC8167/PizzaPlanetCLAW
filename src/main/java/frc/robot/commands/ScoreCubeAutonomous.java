// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//import java.time.Instant;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
//import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Pivot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreCubeAutonomous extends SequentialCommandGroup {
    //private Drivetrain drivetrain;  //uncomment if adding any movement
    private Grabber grabber;
    private Arm arm;
    private Pivot pivot;
    /** Creates a new ScoreCubeAutonomous. */
    public ScoreCubeAutonomous() {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(
            new SetPivotAngle(pivot, Constants.PIVOT_ANGLE_90),
            new SetArmDistance(arm, Constants.ARM_36_INCHES),
            new InstantCommand(() -> grabber.openGrabber()),
            new WaitCommand(1.0),
            new InstantCommand(() -> grabber.closeGrabber()),
            new SetArmDistance(arm, Constants.ARM_FULLY_RETRACTED),
            new SetPivotAngle(pivot, Constants.PIVOT_ANGLE_0)
        );
    }
}
