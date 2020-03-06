package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;

import static frc.robot.Constants.DriveConstants.*;
import static frc.robot.RobotContainer.driveSubsystem;

public class DriveStraight extends PIDCommand {

    public DriveStraight(double distance){
        super(
                new PIDController(1,0,0),
                driveSubsystem :: getAverageEncoderDistance,
                distance*1/(8*Math.PI),
                output -> driveSubsystem.arcadeDrive(output,0),
                driveSubsystem
        );
        getController().setTolerance(0.1);
    }

    @Override
    public void initialize() {
        super.initialize();
        driveSubsystem.resetEncoders();
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }

}
