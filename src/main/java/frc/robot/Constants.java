// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;



/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
  
  }

//set CAN IDs
public static final int LEFT_FRONT = 1;
public static final int LEFT_BACK = 2;
public static final int RIGHT_FRONT = 3;
public static final int RIGHT_BACK = 4;
public static final int ARM_MOTOR = 5;
public static final int PIGEON_CANID = 6;
public static final int PIVOT_MOTOR = 7;


//Drivetrain Motion Magic Constants
public static final double DRIVE_CRUISE_VELOCITY = 16209;  //check this
public static final int DRIVE_PID_TIMEOUT = 30;  //check this
public static final double DRIVE_ACCELERATION = 16209;  //check this


//
public static final int DRIVER_CONTROLLER = 0;
public static final int OPERATOR_CONTROLLER = 1;

//Arm Extender Constants
public static final int ARM_pidLoopTimeout = 30;
public static final double armCruiseVelocity = 4450;
public static final double armAcceleration = 4450;
public static final double TRACK_TAG_ROTATION_KP = 0.0175;
public static final double ARM_POWER = 0.2;  //used for MANUAL control of arm
public static final double ARM_FULLY_RETRACTED = 0.0;
public static final double ARM_12_INCHES = 12.0;   //inches  ****PLACEHOLDER
public static final double ARM_24_INCHES = 24.0;  // inches  ****PLACEHOLDER
public static final double ARM_36_INCHES = 36.0;  //inches    ****PLACEHOLDER


//Vision Constants
public static final String USB_CAMERA_NAME = "Microsoft_LifeCam_HD-3000";

public static final int SOLENOID_ID1 = 0;
public static final int SOLENOID_ID2 = 1;


//Arm Pivot Constants
public static final int PIVOT_pidLoopTimeout = 30;
public static final double pivotCruiseVelocity = 4450.0;
public static final double pivotAcceleration = 4450.0;
public static final double PIVOT_POWR = 0.2; //used for MANUAL control of pivot arm
public static final int PIVOT_kpIDLoopIDx = 0;
public static final double PIVOT_ANGLE_0 = 0.0;  //resting down position   ****PLACEHOLDER
public static final double PIVOT_ANGLE = 22.0;  
public static final double PIVOT_ANGLE_45 = 45.0;  //horizontal position    ****PLACEHOLDER
public static final double PIVOT_ANGLE_67 = 67.0;  
public static final double PIVOT_ANGLE_90 = 90.0;  //raised 45 degrees above horizontal    ****PLACEHOLDER


//PIGEON Constants
public static final double PIGEON_TURN_KP = 0.007;  //test this 
public static final double QUICKTURN_THRESHOLD_DEGREES = 3.0;
public static final double BALANCE_GOAL_DEGREE = 0;
public static final double BALANCE_KP = 0.015;
public static final double BALANCE_REVERSE_POWER = 1.3;  //tweak during final testing
public static final double BALANCE_PITCH_THRESHOLD = 3.0;
public static final double PIVOT_POWER = 0.2;








}
