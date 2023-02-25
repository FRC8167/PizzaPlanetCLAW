// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.SetArmDistance;
import frc.robot.commands.SetPivotAngle;
import frc.robot.subsystems.Arm;
//import frc.robot.commands.Autos;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Pivot;

// import java.lang.ModuleLayer.Controller;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain drivetrain = new Drivetrain();
  private final Arm arm = new Arm();
  private final Pivot pivot = new Pivot();
  private final Grabber grabber = new Grabber();
  //private final Vision vision = new Vision();

  private final SendableChooser<Command> autoCommandSelector = new SendableChooser<>();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController driverController = new CommandXboxController(Constants.DRIVER_CONTROLLER);
  private final CommandXboxController operatorController = new CommandXboxController(Constants.OPERATOR_CONTROLLER);
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    addAutoCommands();
    SmartDashboard.putData(autoCommandSelector);


    drivetrain.setDefaultCommand(new ArcadeDrive(
      drivetrain, 
      () -> driverController.getLeftY(),
      () -> driverController.getRightX()*0.5  //change as needed
    )
    );
    
    }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
        // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  
    //Driver Buttons
    operatorController.leftBumper().onTrue(new InstantCommand(() -> grabber.toggle()));
    //driverController.x().onTrue(new QuickTurn(drivetrain, 90));
    //driverController.y().onTrue(new QuickTurn(drivetrain, 180));
    //driverController.a().whileTrue(new TurnToTrackedTarget(drivetrain, vision, Constants.TRACK_TAG_ROTATION_KP));

    //Operator Buttons

    //EXTEND/RETRACT TELESCOPING ARM
    operatorController.a().onTrue(new SetArmDistance(arm, 5.0));  //extend
    operatorController.b().onTrue(new SetArmDistance(arm, 0));  //retract
    //MANUAL ZERO ARM SENSOR
    operatorController.x().onTrue(new InstantCommand(() -> arm.zeroArmSensor()));
    //MANUAL CONTROL OF TELESCOPING ARM
    operatorController.leftTrigger().whileTrue(new StartEndCommand(() -> arm.testArm(Constants.ARM_POWER), () -> arm.stop()));
    operatorController.rightTrigger().whileTrue(new StartEndCommand(() -> arm.testArm(-Constants.ARM_POWER), () -> arm.stop()));
    //MANUAL ZERO PIVOT SENSOR
    //add a button to do this
    //MANUAL CONTROL OF PIVOT ARM
    //add a button to move forward
    //add a button to move backward  use Constants,PIVOT_POWER for speed
    driverController.a().onTrue(new SetPivotAngle(pivot, 10.0));  //extend
    driverController.b().onTrue(new SetPivotAngle(pivot, 0));  //retract
    //MANUAL ZERO ARM SENSOR
    driverController.x().onTrue(new InstantCommand(() -> pivot.zeroPivotSensor()));
    //MANUAL CONTROL OF PIVOT MOTOR
    driverController.leftTrigger().whileTrue(new StartEndCommand(() -> pivot.testPivot(Constants.PIVOT_POWER), () -> pivot.stop()));
    driverController.rightTrigger().whileTrue(new StartEndCommand(() -> pivot.testPivot(-Constants.PIVOT_POWER), () -> pivot.stop()));



  }

  private void addAutoCommands() {
    autoCommandSelector.setDefaultOption(
    "Drive Forward 2 Sec",
      new frc.robot.commands.DriveForwardTimed(drivetrain, 2, 0.4));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //An example command will be run in autonomous
    return autoCommandSelector.getSelected();
}
}
