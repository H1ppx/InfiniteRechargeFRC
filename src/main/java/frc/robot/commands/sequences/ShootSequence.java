package frc.robot.commands.sequences;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.PortLock;
import frc.robot.subsystems.TransportSubsystem;

import static frc.robot.RobotContainer.shooterSubsystem;
import static frc.robot.RobotContainer.transportSubsystem;

public class ShootSequence extends SequentialCommandGroup {

    public ShootSequence(){
        addCommands(
                new PortLock(),
                new DriveStraight(1), // Drive into the wall
                new DriveStraight(-1), // Back up from the wall
                new PortLock(),
                new InstantCommand(()-> shooterSubsystem.setSpeed(1)),
                new WaitUntilCommand(()-> (shooterSubsystem.getRPM()>3500)),
                new InstantCommand(() -> transportSubsystem.setTransportSpeed(1))
        );
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);

        transportSubsystem.setTransportSpeed(0);
        shooterSubsystem.setSpeed(0);
    }

}
