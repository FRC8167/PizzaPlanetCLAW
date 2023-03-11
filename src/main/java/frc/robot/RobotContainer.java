// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ChargingStationAutoBalance;
import frc.robot.commands.DriveForwardDistance;
import frc.robot.commands.ManualExtend;
//import frc.robot.commands.ManualExtend;
import frc.robot.commands.NestArmPivot;
import frc.robot.commands.Pole1Positioning;
import frc.robot.commands.Pole2Positioning;
// import frc.robot.commands.ManualPivot;
//import frc.robot.commands.DriveForwardDistance;
//import frc.robot.commands.NestArmPivot;
//import frc.robot.commands.Pole1Positioning;
//import frc.robot.commands.Pole2Positioning;
//import frc.robot.commands.FloorPickupPositioning;
import frc.robot.commands.RotateAngle;
// import frc.robot.commands.ScoreCubeAutonomous;
// import frc.robot.commands.SecondShelfPositioning;
import frc.robot.commands.SetArmDistance;
import frc.robot.commands.SetPivotAngle;
//import frc.robot.commands.Shelf1Parallel;
import frc.robot.commands.Shelf1Positioning;
//import frc.robot.commands.Shelf1Positioning;
import frc.robot.commands.Shelf2Positioning;
import frc.robot.commands.SubstationPositioning;
import frc.robot.commands.TurnToTrackedTarget;
//import frc.robot.commands.Shelf1Positioning;
//import frc.robot.commands.Shelf2Positioning;
//import frc.robot.commands.SubstationPositioning;
// import frc.robot.commands.ToggleGrabberNGo;
import frc.robot.subsystems.Arm;
//import frc.robot.commands.Autos;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Pivot;
//import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.subsystems.Vision;

// import java.lang.ModuleLayer.Controller;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
  private final Vision vision = new Vision();

  private final SendableChooser<Command> autoCommandSelector = new SendableChooser<>();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController driverController = new CommandXboxController(Constants.DRIVER_CONTROLLER);
  private final CommandXboxController operatorController = new CommandXboxController(Constants.OPERATOR_CONTROLLER);
  private final CommandXboxController playmakerController = new CommandXboxController(Constants.PLAYMAKER_CONTROLLER);
//
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    addAutoCommands();
    SmartDashboard.putData(autoCommandSelector);
    SmartDashboard.putNumber("arm_pos", arm.getArmPosition());
    SmartDashboard.putNumber("pivot_pos", pivot.getPivotPosition());


    drivetrain.setDefaultCommand(new ArcadeDrive(
      drivetrain, 
      () -> driverController.getLeftY()*.80,
      () -> driverController.getRightX()*-0.6  //change as needed
    )
    );
    //pivot.setDefaultCommand(new ManualPivot(pivot));
    
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
  
    //CameraServer.startAutomaticCapture();
    

    //Operator Buttons
    //GRABBER TOGGLE
    operatorController.leftStick().onTrue(new InstantCommand(() -> grabber.toggle()));

    //EXTEND/RETRACT TELESCOPING ARM
    operatorController.a().onTrue(new SetArmDistance(arm, 12.5));  //extend
    operatorController.b().onTrue(new SetArmDistance(arm, 0));  //retract
    //MANUAL ZERO ARM SENSOR
    // operatorController.x().onTrue(new InstantCommand(() -> arm.zeroArmSensor()));
    // //MANUAL CONTROL OF TELESCOPING ARM
    operatorController.leftTrigger().whileTrue(new ManualExtend(arm, -Constants.ARM_POWER)); //retract
    operatorController.rightTrigger().whileTrue(new ManualExtend(arm, Constants.ARM_POWER)); //extend
    // operatorController.leftTrigger().whileTrue(new StartEndCommand(() -> arm.testArm(-Constants.ARM_POWER), () -> arm.stop()));
    // operatorController.rightTrigger().whileTrue(new StartEndCommand(() -> arm.testArm(Constants.ARM_POWER), () -> arm.stop()));
    // //AUTO ROTATE PIVOT MOTOR
    // operatorController.start().onTrue(new SetPivotAngle(pivot, 30.0));  //raise arm (degrees)
    // operatorController.back().onTrue(new SetPivotAngle(pivot, 0.0));  //return arm to base position
    //MANUAL ZERO PIVOT SENSOR
    // operatorController.y().onTrue(new InstantCommand(() -> pivot.zeroPivotSensor()));
    operatorController.leftBumper().whileTrue(new StartEndCommand(() -> pivot.testPivot(-Constants.PIVOT_POWER), () -> pivot.stop()));
    operatorController.rightBumper().whileTrue(new StartEndCommand(() -> pivot.testPivot(Constants.PIVOT_POWER), () -> pivot.stop()));
    //MANUAL CONTROL OF PIVOT MOTOR
    //operatorController.leftBumper().whileTrue(new ManualPivot(pivot, true) );
    //operatorController.rightBumper().whileTrue(new ManualPivot(pivot, false));


    //DRIVER CONTROLLER
    driverController.povRight().onTrue(new RotateAngle(drivetrain, 90));
    driverController.povLeft().onTrue(new RotateAngle(drivetrain, -90));
    driverController.povDown().onTrue(new RotateAngle(drivetrain, 180));
    driverController.a().whileTrue(new TurnToTrackedTarget(drivetrain, vision));
    driverController.leftBumper().whileTrue(new ChargingStationAutoBalance(drivetrain));
    driverController.x().onTrue(new InstantCommand(() -> drivetrain.invert_drivetrain()));
    driverController.rightTrigger().onTrue(new InstantCommand(()-> drivetrain.toggleSnailSpeed()));
    driverController.rightTrigger().onFalse(new InstantCommand(()-> drivetrain.toggleSnailSpeed()));
  

    //PLAYMAKER CONTROLLER
    playmakerController.povLeft().onTrue(new SetPivotAngle(pivot, 50.0));  //raise arm (degrees)
    playmakerController.povRight().onTrue(new SetPivotAngle(pivot, 0.0));  //return arm to base position
    playmakerController.a().onTrue(new Shelf1Positioning(arm, pivot));
    playmakerController.b().onTrue(new Shelf2Positioning(arm, pivot));
    playmakerController.x().onTrue(new Pole1Positioning(arm, pivot));
    playmakerController.y().onTrue(new Pole2Positioning(arm, pivot));
    playmakerController.leftBumper().onTrue(new SubstationPositioning(arm, pivot));
    playmakerController.rightBumper().onTrue(new NestArmPivot(pivot, arm));



  }

  private void addAutoCommands() {

    autoCommandSelector.setDefaultOption(
    "Auto Center",
      new SequentialCommandGroup(
      new SetPivotAngle(pivot, 85),
      new SetArmDistance(arm, Constants.ARM_SHELF1_21),
      new InstantCommand(()-> grabber.openGrabber()),
      new SetPivotAngle(pivot, 85),
      new SetArmDistance(arm, Constants.ARM_FULLY_RETRACTED_0),
      new InstantCommand(()-> grabber.closeGrabber()),
      
      new ParallelCommandGroup(
        new SetPivotAngle(pivot, Constants.PIVOT_ANGLE_START_0),
        new DriveForwardDistance(drivetrain, -7.5)
      ),
      // new DriveForwardDistance(drivetrain, -14.0),
      // new DriveForwardDistance(drivetrain, 7.5),
      new ChargingStationAutoBalance(drivetrain)

      ));


    autoCommandSelector.addOption(
       "Auto Right/Left",
       new SequentialCommandGroup(
        new SetPivotAngle(pivot, 80),
        new SetArmDistance(arm, Constants.ARM_SHELF1_21),
        new InstantCommand(()-> grabber.openGrabber()),
        new SetPivotAngle(pivot, 80),
        new SetArmDistance(arm, Constants.ARM_FULLY_RETRACTED_0),
        new InstantCommand(()-> grabber.closeGrabber()),
        new ParallelCommandGroup(
          new SetPivotAngle(pivot, 0),
          new DriveForwardDistance(drivetrain, -17)
        )
        ));
    autoCommandSelector.addOption(
      "Auto Right/Left Double",
      new SequentialCommandGroup(
        new SetPivotAngle(pivot, 80),
        new SetArmDistance(arm, Constants.ARM_SHELF1_21),
        new InstantCommand(()-> grabber.openGrabber()),
        new SetPivotAngle(pivot, 80),
        new SetArmDistance(arm, Constants.ARM_FULLY_RETRACTED_0),
        new InstantCommand(()-> grabber.closeGrabber()),
        new ParallelCommandGroup(
          new SetPivotAngle(pivot, 0),
          new DriveForwardDistance(drivetrain, -15)
        ),
        new RotateAngle(drivetrain, 180),
        new InstantCommand(()-> grabber.openGrabber()),
        // new DriveForwardDistance(drivetrain, 2.5),
        new SetPivotAngle(pivot, 45),
        new SetArmDistance(arm, 21),
        new InstantCommand(()-> grabber.closeGrabber())


        ));
  
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
