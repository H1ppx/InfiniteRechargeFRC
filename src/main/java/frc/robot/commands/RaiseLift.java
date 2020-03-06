package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.LiftSubsystem;

public class RaiseLift extends CommandBase {

    LiftSubsystem liftSubsystem;

    @Override
    public void execute() {
        liftSubsystem.setLiftSpeed(1);
    }

    @Override
    public void end(boolean interrupted) {
        liftSubsystem.setLiftSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return liftSubsystem.atLimit();
    }
}
