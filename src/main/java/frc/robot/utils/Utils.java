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

package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;

import static frc.robot.RobotContainer.auxiliaryController;
import static frc.robot.RobotContainer.driverController;

public class Utils {

    public static boolean threshold(double input, double min, double max){
        return (input>=min && input<=max);
    }

    public static void rumbleDriver(boolean rumble){
        if(rumble){
            driverController.setRumble(GenericHID.RumbleType.kLeftRumble,1);
            driverController.setRumble(GenericHID.RumbleType.kRightRumble,1);
        }else{
            driverController.setRumble(GenericHID.RumbleType.kLeftRumble,0);
            driverController.setRumble(GenericHID.RumbleType.kRightRumble,0);
        }
    }

    public static void rumbleAux(boolean rumble){
        if(rumble){
            auxiliaryController.setRumble(GenericHID.RumbleType.kLeftRumble,1);
            auxiliaryController.setRumble(GenericHID.RumbleType.kRightRumble,1);
        }else{
            auxiliaryController.setRumble(GenericHID.RumbleType.kLeftRumble,0);
            auxiliaryController.setRumble(GenericHID.RumbleType.kRightRumble,0);
        }
    }

    public static void rumble(boolean rumble){
        rumbleDriver(rumble);
        rumbleAux(rumble);
    }

    public static double getPortX(){
        return NetworkTableInstance.getDefault()
                .getTable("Vision/Port").getEntry("X").getDouble(320);
    }

    public static boolean getPortVisible(){
        return  NetworkTableInstance.getDefault()
                .getTable("Vision/Port").getEntry("Detected").getBoolean( false);
    }

}
