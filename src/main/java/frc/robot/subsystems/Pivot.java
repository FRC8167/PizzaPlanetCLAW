// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pivot extends SubsystemBase {
  private final WPI_TalonFX pivotMotor;
  /** Creates a new Pivot. */
  public Pivot() {
    pivotMotor = new WPI_TalonFX(Constants.PIVOT_MOTOR);

    configmotor();
  }
  
  private void configmotor() {
    pivotMotor.configFactoryDefault();
    pivotMotor.setNeutralMode(NeutralMode.Brake);
    pivotMotor.configNeutralDeadband(0.1, 30);
    
    pivotMotor.configClosedloopRamp(1.0);
    pivotMotor.configOpenloopRamp(0.5);

    pivotMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, Constants.PIVOT_pidLoopTimeout);
    pivotMotor.selectProfileSlot(0, 0);

    pivotMotor.config_kF(0, .046, Constants.PIVOT_pidLoopTimeout);
    pivotMotor.config_kP(0, 0.049, Constants.PIVOT_pidLoopTimeout);
    pivotMotor.config_kI(0, 0, Constants.PIVOT_pidLoopTimeout);
    pivotMotor.config_kD(0, 0, Constants.PIVOT_pidLoopTimeout);

    pivotMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.PIVOT_pidLoopTimeout);
    pivotMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.PIVOT_pidLoopTimeout);

    pivotMotor.setInverted(true);
    pivotMotor.setSensorPhase(false);

    pivotMotor.configNominalOutputForward(0, Constants.PIVOT_pidLoopTimeout);
    pivotMotor.configNominalOutputReverse(0, Constants.PIVOT_pidLoopTimeout);
    pivotMotor.configPeakOutputForward(1, Constants.PIVOT_pidLoopTimeout);
    pivotMotor.configPeakOutputReverse(-1, Constants.PIVOT_pidLoopTimeout);

    pivotMotor.configMotionCruiseVelocity(Constants.pivotCruiseVelocity, Constants.PIVOT_pidLoopTimeout);
    pivotMotor.configMotionAcceleration(Constants.pivotAcceleration, Constants.PIVOT_pidLoopTimeout);

    //Zero the encoder
    pivotMotor.setSelectedSensorPosition(0, 0, Constants.PIVOT_pidLoopTimeout);  //really 45 real pos

    }
    public void testPivot(double power) {
      pivotMotor.set(ControlMode.PercentOutput, power);
      double motorOutput = pivotMotor.getMotorOutputPercent();
      //display motor power, velocity, and sensor position
      StringBuilder pivotinfo = new StringBuilder();
      pivotinfo.append("\tPIVOT Output Power: ");
      pivotinfo.append(motorOutput);
      pivotinfo.append("\tPIVOT Motor Velocity: ");
      pivotinfo.append(pivotMotor.getSelectedSensorVelocity(Constants.PIVOT_kpIDLoopIDx));
      pivotinfo.append("\tPIVOT SENSOR POSITION: ");
      pivotinfo.append(pivotMotor.getSelectedSensorPosition(Constants.PIVOT_kpIDLoopIDx));

      System.out.println(pivotinfo.toString());
    }

    public void setPivotMotionMagic(double targetRotations) {
      double targetTicks = targetRotations * 80 * 2048;
      double horizontalHoldOutput = 0.13;
      double arbFeedFwdTerm = getFeedForward(horizontalHoldOutput);
      //pivotMotor.set(TalonFXControlMode.MotionMagic, targetTicks);
      pivotMotor.set(TalonFXControlMode.MotionMagic, targetTicks, DemandType.ArbitraryFeedForward, arbFeedFwdTerm);
      //pivotMotor.set(TalonFXControlMode.MotionMagic, targetTicks, DemandType.ArbitraryFeedForward, horizontalHoldOutput);

      //Display PID Commanded Target and Resulting Error
      StringBuilder pivotmoreinfo = new StringBuilder();
      pivotmoreinfo.append("\tPIVOT Commanded Target:  ");
      pivotmoreinfo.append(targetTicks);
      pivotmoreinfo.append("\tPIVOT PID Error ");
      pivotmoreinfo.append(pivotMotor.getClosedLoopError());
      pivotmoreinfo.append("\tPIVOT SENSOR POSITION:  ");
      pivotmoreinfo.append(pivotMotor.getSelectedSensorPosition());

      System.out.println(pivotmoreinfo.toString());
    
    }

    public void stop(){
      pivotMotor.set(ControlMode.PercentOutput, 0);
    }

    public double getPivotPosition() {
      return pivotMotor.getSelectedSensorPosition(0);
    }

    public void zeroPivotSensor() {
      pivotMotor.setSelectedSensorPosition(0);
    }


    //**************the 45-degree offset is just a guess from sketches.  Change this angle per actual build.*****

    private double getFeedForward(double horizontalHoldOutput) {
      double pivotSensorPosition = pivotMotor.getSelectedSensorPosition(0);
      double pivotCurrentAngle = (pivotSensorPosition*360/2048);  //lowest position is 0 via sensor; needs 45 deg offset
      double theta = Math.toRadians(90 - (pivotCurrentAngle + 45));  //angle of elevation for arm; 90 is max hold
      double gravityCompensation = Math.cos(theta);
      double arbFeedFwd = gravityCompensation * horizontalHoldOutput;
      return arbFeedFwd;
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
