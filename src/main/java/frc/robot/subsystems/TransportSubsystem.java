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

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import static frc.robot.Constants.TransportConstants.*;

public class TransportSubsystem extends SubsystemBase{

    private final CANSparkMax top = new CANSparkMax(kTransportTopPort, kBrushless);
    private final CANSparkMax bot = new CANSparkMax(kTransportBotPort, kBrushless);

    public void setTransportSpeed(double speed) {
        top.set(-speed);
        bot.set(speed);
    }

    public void setTransportSpeed(double topSpeed, double botSpeed){
        top.set(-topSpeed);
        bot.set(botSpeed);
    }

    public double getTopRPM(){
        return top.getEncoder().getVelocity();
    }

    public double getBotRPM(){
        return bot.getEncoder().getVelocity();
    }
}
