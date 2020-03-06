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
