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
