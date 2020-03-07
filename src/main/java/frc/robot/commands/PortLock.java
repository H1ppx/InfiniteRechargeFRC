/****************************************************************************
 *
 *     This file is part of COVID.
 *
 *     Copyright (c) 2020 William Chu
 *
 *     COVID is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     COVID is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with COVID.  If not, see <https://www.gnu.org/licenses/>.
 *
 ****************************************************************************/

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
