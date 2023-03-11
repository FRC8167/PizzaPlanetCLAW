// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
    private final WPI_TalonFX leftFront = new WPI_TalonFX(Constants.LEFT_FRONT);
    private final WPI_TalonFX rightFront = new WPI_TalonFX(Constants.RIGHT_FRONT);
    // NOTE: you should never use the back motors directly, only the front motors
    private final WPI_TalonFX leftBack = new WPI_TalonFX(Constants.LEFT_BACK);
    private final WPI_TalonFX rightBack = new WPI_TalonFX(Constants.RIGHT_BACK);
    
    private final DifferentialDrive drivetrain = new DifferentialDrive(leftFront, rightFront);
    
    private Pigeon2 pigeon = new Pigeon2(Constants.PIGEON_CANID);
    private double snail = 1.0;
    private boolean isSlow;
    private int drivetrian_flip = 1;
    
    /** Creates a new Drivetrain. */
    public Drivetrain() {
        configMotors();
    }
  

    private void configMotors() {
        // reset the settings of all the motors
        leftFront.configFactoryDefault();
        rightFront.configFactoryDefault();
        leftBack.configFactoryDefault();
        rightBack.configFactoryDefault();
        
        // set the motors to brake mode
        leftFront.setNeutralMode(NeutralMode.Brake);
        rightFront.setNeutralMode(NeutralMode.Brake);
        leftBack.setNeutralMode(NeutralMode.Brake);
        rightBack.setNeutralMode(NeutralMode.Brake);
        
        // set the back motors to follow the front motors
        // (this is to avoid both motors on the same side spinning in opposite directions)
        // (if you do not do this, the motors will destroy the gearboxes!!!)
        leftBack.follow(leftFront);
        rightBack.follow(rightFront);
        
        // invert the left motors so that they spin in the same direction as the right motors
        // TODO: ALL OF THESE ARE EXACTLY THE OPPOSITE OF WHAT THEY SHOULD, MAKING THERE BE LIKE 1000 MINUS SIGNS EVERYWHERE
        leftBack.setInverted(false);
        leftFront.setInverted(false);
        leftFront.setInverted(true);
        rightFront.setInverted(true);
        
        // set the ramp rate of the motors to 0.5 seconds
        // NOTE: this is how long it takes for the motors to go from 0 to 100% power
        leftFront.configOpenloopRamp(Constants.DRIVETRAIN_RAMP_TIME_SECONDS);
        rightFront.configOpenloopRamp(Constants.DRIVETRAIN_RAMP_TIME_SECONDS);
        leftBack.configOpenloopRamp(Constants.DRIVETRAIN_RAMP_TIME_SECONDS);
        rightBack.configOpenloopRamp(Constants.DRIVETRAIN_RAMP_TIME_SECONDS);
        
        // set the motor encoder type to use the integrated sensor
        leftFront.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, Constants.DRIVE_PID_TIMEOUT_MS);
        rightFront.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, Constants.DRIVE_PID_TIMEOUT_MS);
        leftFront.selectProfileSlot(0, 0);
        rightFront.selectProfileSlot(0, 0);
        
        // Set the PID values for the motors
        leftFront.config_kF(0, Constants.DRIVETRAIN_PID_KF);
        leftFront.config_kP(0, Constants.DRIVETRAIN_PID_KP);
        leftFront.config_kI(0, Constants.DRIVETRAIN_PID_KI);
        leftFront.config_kD(0, Constants.DRIVETRAIN_PID_KD);
        rightFront.config_kF(0, Constants.DRIVETRAIN_PID_KF);
        rightFront.config_kP(0, Constants.DRIVETRAIN_PID_KP);
        rightFront.config_kI(0, Constants.DRIVETRAIN_PID_KI);
        rightFront.config_kD(0, Constants.DRIVETRAIN_PID_KD);
        
        // TODO: ngl i have no idea what this does.
        // UPDATE: i *think* the status frame period is how often the motor 
        //         controller sends data to the roboRIO, so this sets it to 10ms
        rightFront.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.DRIVE_PID_TIMEOUT_MS);
        rightFront.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.DRIVE_PID_TIMEOUT_MS);
        leftFront.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.DRIVE_PID_TIMEOUT_MS);
        leftFront.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.DRIVE_PID_TIMEOUT_MS);
        
        // NOTE: the nominal output is the max power that the motors can output continuously
        //       the peak output is the max power that the motors can output for a short period of time
        // TODO: these values are probably wrong, especially the nominal output.
        
        // set the nominal and peak output of the motors
        rightFront.configNominalOutputForward(0.0);
        leftFront.configNominalOutputForward(0.0);
        rightFront.configNominalOutputReverse(0.0);
        rightFront.configNominalOutputReverse(0.0);
        rightFront.configPeakOutputForward(1.0);
        leftFront.configPeakOutputForward(1.0);
        rightFront.configPeakOutputReverse(-1.0);
        leftFront.configPeakOutputReverse(-1.0);
        
        // set the selected sensor position of the motors to be 0
        // (this is so that the motors start at 0 encoder ticks)
        leftFront.setSelectedSensorPosition(0);
        rightFront.setSelectedSensorPosition(0);
        leftBack.setSelectedSensorPosition(0);
        rightBack.setSelectedSensorPosition(0);
        
        // TODO: explain what this does
        leftFront.setSensorPhase(false);
        
        // Configure the maximum velocity and acceleration of the motors for motion magic
        rightFront.configMotionCruiseVelocity(Constants.DRIVE_CRUISE_VELOCITY, Constants.DRIVE_PID_TIMEOUT_MS);
        leftFront.configMotionCruiseVelocity(Constants.DRIVE_CRUISE_VELOCITY, Constants.DRIVE_PID_TIMEOUT_MS);
        rightFront.configMotionAcceleration(Constants.DRIVE_ACCELERATION, Constants.DRIVE_PID_TIMEOUT_MS);
        leftFront.configMotionAcceleration(Constants.DRIVE_ACCELERATION, Constants.DRIVE_PID_TIMEOUT_MS);
    }
    
    /**
     * TODO: add an actual docstring here
     * 
     * @param throttle -1.0 to 1.0, forward is positive.
     * @param rotation -1.0 to 1.0, counterclockwise is positive.
     */
    public void arcadeDrive(double throttle, double rotation) {
        drivetrain.arcadeDrive(snail * drivetrian_flip * -throttle, snail * rotation);
    }
    
    public void tankDrive(double leftSpeed, double rightSpeed) {
        drivetrain.tankDrive(snail * -leftSpeed, snail * -rightSpeed);
    }
  
    public void driveForward(double speed) {    
        drivetrain.tankDrive(-speed, -speed);
    }
  
    public void stop() {
        drivetrain.stopMotor();
    }

    public void zeroPigeon(double reset) {
        pigeon.setYaw(reset);
    }

    public double getYaw() {
        return pigeon.getYaw();
    }

    public double getPitch() {
        return pigeon.getPitch();
    }

    public double getRoll() {
        return pigeon.getRoll();
    }
    
    public void invert_drivetrain() {
        drivetrian_flip *= -1;
    }
    
    public void snailSpeed() {
        snail = 0.6;
        isSlow = true;
    }
    
    public void normalSpeed() {
        snail = 1;
        isSlow = false;
    }
    
    public void toggleSnailSpeed() {
        if (isSlow) {
            normalSpeed();
        } 
        else {
            snailSpeed();
        }
    }
    
    public void setDriveMotionMagic(double distance, double maxvelocity, double maxAcceleration) {
        // disable safety so that the motors let us do what we want
        drivetrain.setSafetyEnabled(false);
        
        // // configure the velocity and acceleration of the motors with the given values
        // rightFront.configMotionCruiseVelocity(Constants.DRIVE_CRUISE_VELOCITY, Constants.DRIVE_PID_TIMEOUT_MS);
        // leftFront.configMotionCruiseVelocity(Constants.DRIVE_CRUISE_VELOCITY, Constants.DRIVE_PID_TIMEOUT_MS);
        
        // rightFront.configMotionAcceleration(Constants.DRIVE_ACCELERATION, Constants.DRIVE_PID_TIMEOUT_MS);
        // leftFront.configMotionAcceleration(Constants.DRIVE_ACCELERATION, Constants.DRIVE_PID_TIMEOUT_MS);
        
        // // set the encoder positions to 0 so we dont have to worry about it
        // leftFront.getSensorCollection().setIntegratedSensorPosition(0, Constants.DRIVE_PID_TIMEOUT_MS);
        // leftFront.getSensorCollection().setIntegratedSensorPosition(0, Constants.DRIVE_PID_TIMEOUT_MS);
        
        // actually set the motion magic to the given distance
        // TODO: why is this negative??
        rightFront.set(ControlMode.MotionMagic, -distance);
        leftFront.set(ControlMode.MotionMagic, -distance);
    }

    public void stopDriveMotionMagic() {
        leftFront.set(ControlMode.PercentOutput, 0);
        rightFront.set(ControlMode.PercentOutput, 0);

        drivetrain.setSafetyEnabled(true);
    }
    
    public void setTurnMotionMagic(double distance, double maxvelocity, double maxAcceleration) {
        drivetrain.setSafetyEnabled(false);
        
        rightFront.set(ControlMode.MotionMagic, distance); // opposite direction since we are turning
        leftFront.set(ControlMode.MotionMagic, -distance);
    }
    
    public void stopTurnMotionMagic() {
        leftFront.set(ControlMode.PercentOutput, 0);
        rightFront.set(ControlMode.PercentOutput, 0);

        drivetrain.setSafetyEnabled(true);
    }
    
    @Override
    public void periodic() {}
    
    public boolean isDriveMagicMotionDone(double distanceTicks) {
        double sensorDistance = rightFront.getSelectedSensorPosition(0);
        double percentError = 100*(-distanceTicks - sensorDistance) / -distanceTicks;
        return (distanceTicks <14000 && percentError <5) || percentError <1;  //where did the 14000 come from?
    }
    
    public double getLeadRightSensorPosition() {
        return rightFront.getSelectedSensorPosition(0);
    }
    
    public void zeroDrivetrainEncoders() {
        leftFront.setSelectedSensorPosition(0);
        leftBack.setSelectedSensorPosition(0);
        rightFront.setSelectedSensorPosition(0);
        rightBack.setSelectedSensorPosition(0);
    }
}
