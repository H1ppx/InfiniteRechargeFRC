package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.utils.Utils;

import static frc.robot.Constants.VisionPIDConstants.*;
import static frc.robot.RobotContainer.*;
import static frc.robot.utils.Utils.*;

public class PortLock extends PIDCommand {


    public PortLock(){
        super(
                new PIDController(kVisionTurnP, kVisionTurnI, kVisionTurnD),
                Utils :: getPortX,
                320,
                output -> driveSubsystem.arcadeDrive(output, 0),
                driveSubsystem
        );
        getController().enableContinuousInput(0, 640);
        getController().setTolerance(3);
    }

    @Override
    public void execute() {
        if(getPortVisible()){
            m_useOutput.accept(m_controller.calculate(m_measurement.getAsDouble(),
                    m_setpoint.getAsDouble()));
        } else {
            m_useOutput.accept(driverController.getX(GenericHID.Hand.kRight));
        }
    }

    @Override
    public boolean isFinished() {
        return  getController().atSetpoint();
    }
}
