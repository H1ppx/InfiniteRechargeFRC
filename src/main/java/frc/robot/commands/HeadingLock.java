package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;

import static frc.robot.Constants.DriveConstants.*;
import static frc.robot.RobotContainer.driveSubsystem;
import static frc.robot.RobotContainer.driverController;

public class HeadingLock extends PIDCommand {

    public HeadingLock(double tgt) {
        super(new PIDController(kTurnP, kTurnI, kTurnD),
                driveSubsystem :: getHeading,
                tgt,
                output -> driveSubsystem.arcadeDrive(driverController.getY(GenericHID.Hand.kLeft),output),
                driveSubsystem);

        getController().setSetpoint(tgt);
        getController().enableContinuousInput(-180, 180);
        getController().setTolerance(kTurnToleranceDeg, kTurnRateToleranceDegPerS);
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}
