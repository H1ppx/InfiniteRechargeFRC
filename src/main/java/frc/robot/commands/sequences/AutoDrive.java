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

package frc.robot.commands.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.LowerIntake;

public class AutoDrive extends SequentialCommandGroup {

    // Test Mode that will drive a distance of 5 "units"
    public AutoDrive() {
        addCommands(
                new LowerIntake(),
                new DriveStraight(5)
        );
    }
}
