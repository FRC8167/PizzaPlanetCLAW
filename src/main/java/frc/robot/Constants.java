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
public static final double armCruiseVelocity = 8900;
public static final double armAcceleration = 5000;

public static final double ARM_POWER = 0.4;  //used for MANUAL control of arm
public static final double ARM_FULLY_RETRACTED_0 = 0.0;  //inches
public static final double ARM_SUBSTATION_8 = 8.0;  //inches pickup cone/cube from substation
public static final double ARM_SHELF1_21 = 21.0;   //inches score cube level 1 shelf
public static final double ARM_SHELF2_35 = 35.0;  // inches score cube level 2 shelf
public static final double ARM_POLE1_21 = 21.0;  //inches score cone on level 1 pole
public static final double ARM_POLE2_35 = 35.0;  //inches score cone on level 2 pole
public static final double ARM_GROUND_21 = 21.0;  //inches pickup cone/cube from ground



//Vision Constants
public static final String USB_CAMERA_NAME = "Microsoft_LifeCam_HD-3000";
public static final double TRACK_TAG_ROTATION_KP = 0.0175;

//Grabber Constants
public static final int SOLENOID_ID1 = 0;
public static final int SOLENOID_ID2 = 1;


//Arm Pivot Constants
public static final int PIVOT_pidLoopTimeout = 30;
public static final double pivotCruiseVelocity = 4450.0;
public static final double pivotAcceleration = 4450.0;
public static final double PIVOT_POWER = 0.2; //used for MANUAL control of pivot arm
public static final int PIVOT_kpIDLoopIDx = 0;
//pivot angles are relative to resting start position = 0 therefore all angles positive
public static final double PIVOT_ANGLE_START_0 = 0.0;  //resting down position ZERO Sensor  INIT?
public static final double PIVOT_SHELF1_45 = 45.0;  //horizontal position
public static final double PIVOT_SHELF2_55 = 55.0;    //10 degrees above horizontal
public static final double PIVOT_POLE1_55 = 55.0;  //10 degrees above horizontal
public static final double PIVOT_POLE2_70 = 70.0;  //25 degrees above horizontal



//PIGEON Constants
public static final double PIGEON_TURN_KP = 0.007;  //test this 
public static final double QUICKTURN_THRESHOLD_DEGREES = 3.0;
public static final double BALANCE_GOAL_DEGREE = 0;
public static final double BALANCE_KP = 0.015;
public static final double BALANCE_REVERSE_POWER = 1.3;  //tweak during final testing
public static final double BALANCE_PITCH_THRESHOLD = 3.0;









}
