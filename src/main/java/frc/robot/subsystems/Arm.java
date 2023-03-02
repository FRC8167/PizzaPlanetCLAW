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

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
  private final TalonFX armMotor;
  /** Creates a new Arm. */
  public Arm() {
    armMotor = new TalonFX(Constants.ARM_MOTOR);
    configmotor();
  }

  private void configmotor(){
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
      armMotor.set(ControlMode.PercentOutput, power);
      double motorOutput = armMotor.getMotorOutputPercent();
      StringBuilder info = new StringBuilder();
      info.append("\tOutput Power: ");
      info.append(motorOutput);
      info.append("\tMotor Velocity: ");
      info.append(armMotor.getSelectedSensorVelocity(0));
      info.append("\tSENSOR POSITION: ");
      info.append(armMotor.getSelectedSensorPosition(0));

      System.out.println(info.toString());
    }

    public void setArmMotionMagic(double targetRotations) {
      double targetTicks = targetRotations * 20 * 2048;
      armMotor.set(TalonFXControlMode.MotionMagic, targetTicks);
      //double retract holdOutput = 0.13;  //uncomment if needed
      //armMotor.set(TalonFXControlMode.MotionMagic, targetTicks, DemandType.ArbitraryFeedForward, retractHoldOutput);
      //Display PID Commanded Target and Resulting Error
      StringBuilder moreinfo = new StringBuilder();
      moreinfo.append("\tCommanded Target:  ");
      moreinfo.append(targetTicks);
      moreinfo.append("\tPID Error ");
      moreinfo.append(armMotor.getClosedLoopError());
      moreinfo.append("\tSENSOR POSITION:  ");
      moreinfo.append(armMotor.getSelectedSensorPosition());

      System.out.println(moreinfo.toString());
    
    }

    public void stop(){
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
