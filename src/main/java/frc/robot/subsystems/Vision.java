// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;
import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Vision extends SubsystemBase {
    private final PhotonCamera camera = new PhotonCamera(Constants.USB_CAMERA_NAME);
    
    private boolean hasTarget;                // whether or not the camera has a target
    private PhotonPipelineResult lastResult;  // stores all collected data from the camera
    
    /** Creates a new Vision Subsystem. */
    public Vision() {}
    
    @Override
    public void periodic() {
        PhotonPipelineResult result = camera.getLatestResult();
        hasTarget = result.hasTargets();
        // only set the lastResult variable if we actually found something
        if (hasTarget) this.lastResult = result;
    }
    
    /**
     * Returns the target with the given ID, if it exists.
     * <p>
     * NOTE: this function returns a {@code java.util.Optional<PhotonTrackedTarget>}, which
     *       is a special type of object that can either contain a value or be empty.
     *       As such, you have to use the {@code isPresent()} and {@code get()} methods 
     *       to access the actual value. 
     *       This is to prevent any accidental null pointer exceptions.
     * 
     * @param id The ID of the target to find.
     * @return The target with the given April tage ID, if it exists.
     */
    public Optional<PhotonTrackedTarget> getTargetWithID(int id) {
        List<PhotonTrackedTarget> targets = lastResult.getTargets();
        
        for (PhotonTrackedTarget target : targets)    {
            if (target.getFiducialId() == id)    {
                return Optional.of(target);
            }
        }
        
        //No target found
        return Optional.empty(); 
    }
    
    /**
     * Returns the best target, if one exists.
     * 
     * @return The best target, as a {@code java.util.Optional<PhotonTrackedTarget>}.
     */
    public Optional<PhotonTrackedTarget> getBestTarget() {
        if (hasTarget) {
            return Optional.ofNullable(lastResult.getBestTarget());
        }
        
        return Optional.empty();
    }
    
    public boolean hasTarget() {
        return hasTarget;
    }
}
