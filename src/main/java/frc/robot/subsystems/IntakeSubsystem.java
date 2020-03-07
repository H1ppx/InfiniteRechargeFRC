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

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushed;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import static frc.robot.Constants.IntakeConstants.*;


public class IntakeSubsystem extends SubsystemBase {

    private final CANSparkMax deploy = new CANSparkMax(kIntakeDeployPort, kBrushed);
    private final CANSparkMax intake = new CANSparkMax(kIntakeWheelPort, kBrushless);

    private final DigitalInput limitSwitch = new DigitalInput(kIntakeLimitSwitchPort);

    public IntakeSubsystem(){
        intake.setInverted(true);
    }

    // Note: ONLY INTAKE DIRECTION (Will break chain if inverted)
    public void setIntakeSpeed(double speed){
        intake.set(Math.abs(speed));
    }

    public boolean atLimit(){
        return limitSwitch.get();
    }

    public void setDeploySpeed(double speed){
        deploy.set(speed);
    }
}
