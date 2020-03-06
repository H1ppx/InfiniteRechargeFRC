package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.RobotContainer.intakeSubsystem;

public class LowerIntake extends CommandBase {

    @Override
    public void execute() {
        intakeSubsystem.setDeploySpeed(-0.25);
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.setDeploySpeed(0);
    }

    @Override
    public boolean isFinished() {
        return intakeSubsystem.atLimit();
    }
}
