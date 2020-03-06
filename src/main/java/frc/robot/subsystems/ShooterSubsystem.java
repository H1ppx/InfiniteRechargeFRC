package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import static frc.robot.Constants.ShooterConstants.kShooterPort;

public class ShooterSubsystem extends SubsystemBase {

    private final CANSparkMax launcher = new CANSparkMax(kShooterPort, kBrushless);
    private final CANEncoder launcherEncoder = new CANEncoder(launcher);

    public void setSpeed(double speed){
        launcher.set(speed);

        // For testing only
        SmartDashboard.putNumber("RPM", getRPM());
    }

    public double getRPM() {
        return launcherEncoder.getVelocity();
    }
}
