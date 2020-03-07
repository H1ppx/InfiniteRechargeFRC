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

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import static frc.robot.Constants.LiftConstants.kLiftPort;
import static frc.robot.Constants.LiftConstants.kWinchPort;

public class LiftSubsystem extends SubsystemBase {

    private final CANSparkMax lift = new CANSparkMax(kLiftPort, kBrushless);
    private final CANSparkMax winch = new CANSparkMax(kWinchPort, kBrushless);

    private final DigitalInput limitSwitch = new DigitalInput(2);

    public void setLiftSpeed(double speed){
        lift.set(speed);
    }

    public void setWinchSpeed(double speed){
        winch.set(-speed);
    }

    public boolean atLimit(){
        return limitSwitch.get();
    }

}
