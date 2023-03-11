// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    private final TalonFX armMotor;
    
    /** Creates a new Arm. */
    public Arm() {
        armMotor = new TalonFX(Constants.ARM_MOTOR);
        configmotor();
    }
    
    private void configmotor() {
        armMotor.configFactoryDefault();
        armMotor.setNeutralMode(NeutralMode.Brake);
        armMotor.configNeutralDeadband(0.1, 30);
        
        armMotor.configClosedloopRamp(1.0);
        armMotor.configOpenloopRamp(0.5);
        
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, Constants.ARM_pidLoopTimeout);
        armMotor.selectProfileSlot(0, 0);
        
        armMotor.config_kF(0, .046, Constants.ARM_pidLoopTimeout);
        armMotor.config_kP(0, 0.049, Constants.ARM_pidLoopTimeout);
        armMotor.config_kI(0, 0, Constants.ARM_pidLoopTimeout);
        armMotor.config_kD(0, 0, Constants.ARM_pidLoopTimeout);
        
        armMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.ARM_pidLoopTimeout);
        armMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.ARM_pidLoopTimeout);
        
        armMotor.setInverted(true);
        armMotor.setSensorPhase(false);
        
        armMotor.configNominalOutputForward(0, Constants.ARM_pidLoopTimeout);
        armMotor.configNominalOutputReverse(0, Constants.ARM_pidLoopTimeout);
        armMotor.configPeakOutputForward(1, Constants.ARM_pidLoopTimeout);
        armMotor.configPeakOutputReverse(-1, Constants.ARM_pidLoopTimeout);
        
        armMotor.configMotionCruiseVelocity(Constants.armCruiseVelocity, Constants.ARM_pidLoopTimeout);
        armMotor.configMotionAcceleration(Constants.armAcceleration, Constants.ARM_pidLoopTimeout);
        
        //Zero the encoder
        armMotor.setSelectedSensorPosition(0, 0, Constants.ARM_pidLoopTimeout);
    }
    
    public void testArm(double power) {
        if (power < 0 && armMotor.getSelectedSensorPosition() <= 0.0 ) {
            armMotor.set(ControlMode.PercentOutput, 0);
        }
        else if (power > 0 && armMotor.getSelectedSensorPosition() >= 250000) {
            armMotor.set(ControlMode.PercentOutput, 0);
        } else {
          armMotor.set(ControlMode.PercentOutput, power);
        }
    }
    
    public boolean isFullyExtended() {
        // TODO: should this be less than
        // TODO: isnt there a constant for this?
      return armMotor.getSelectedSensorPosition() < 350000;
    }
    
    public void setArmMotionMagic(double targetRotations) {
        // TODO: what is this "20" here for?
        double targetTicks = targetRotations * 20 * 2048;
        
        armMotor.set(TalonFXControlMode.MotionMagic, targetTicks);
    }
    
    public void stop() {
        armMotor.set(ControlMode.PercentOutput, 0);
    }
    
    public double getArmPosition() {
        return armMotor.getSelectedSensorPosition(0);
    }
    
    public void zeroArmSensor() {
        armMotor.setSelectedSensorPosition(0);
    }
    
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}