package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import static frc.robot.Constants.DriveConstants.*;

public class DriveSubsystem extends SubsystemBase {

    private final CANSparkMax frontLeftMotor = new CANSparkMax(kFrontLeftMotorPort, kBrushless);
    private final CANSparkMax frontRightMotor = new CANSparkMax(kFrontRightMotorPort, kBrushless);
    private final CANSparkMax backLeftMotor = new CANSparkMax(kBackLeftMotorPort, kBrushless);
    private final CANSparkMax backRightMotor = new CANSparkMax(kBackRightMotorPort, kBrushless);

    private final SpeedControllerGroup leftMotors =
            new SpeedControllerGroup(frontLeftMotor, backLeftMotor);

    private final SpeedControllerGroup rightMotors =
            new SpeedControllerGroup(frontRightMotor, backRightMotor);

    private final DifferentialDrive differentialDrive = new DifferentialDrive(leftMotors, rightMotors);

    private final CANEncoder leftEncoder =
            new CANEncoder(frontLeftMotor);

    private final CANEncoder rightEncoder =
            new CANEncoder(frontRightMotor);

    private final AHRS navx = new AHRS(SPI.Port.kMXP);

    public DriveSubsystem() {
        leftEncoder.setPositionConversionFactor(kEncoderDistancePerPulse);
        rightEncoder.setPositionConversionFactor(kEncoderDistancePerPulse);

        differentialDrive.setRightSideInverted(true);
        differentialDrive.setMaxOutput(0.75);
    }

    public void arcadeDrive(double fwd, double rot) {
        differentialDrive.arcadeDrive(fwd, rot);
    }

    public void resetEncoders() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    public double getAverageEncoderDistance() {
        return (leftEncoder.getPosition() + rightEncoder.getPosition()) / 2.0;
    }

    public CANEncoder getLeftEncoder() {
        return leftEncoder;
    }

    public CANEncoder getRightEncoder() {
        return rightEncoder;
    }

    public double getHeading(){
        return navx.getYaw();
    }

    public void resetNavx(){
        navx.reset();
    }

    public void setMaxOutput(double maxOutput) {
        differentialDrive.setMaxOutput(maxOutput);
    }
}
