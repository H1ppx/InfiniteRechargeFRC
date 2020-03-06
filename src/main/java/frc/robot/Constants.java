/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class DriveConstants {
        public static final int kFrontLeftMotorPort = 7;
        public static final int kFrontRightMotorPort = 1;
        public static final int kBackLeftMotorPort = 9;
        public static final int kBackRightMotorPort = 3;

        public static final int kEncoderCPR = 1024;
        public static final double kWheelDiameterInches = 6;
        public static final double kEncoderDistancePerPulse =
                // Assumes the encoders are directly mounted on the wheel shafts
                (kWheelDiameterInches * Math.PI) / (double) kEncoderCPR;

        public static final double kTurnP = 1;
        public static final double kTurnI = 0;
        public static final double kTurnD = 0;

        public static final double kTurnToleranceDeg = 5;
        public static final double kTurnRateToleranceDegPerS = 10; // degrees per second
    }

    public static final class ShooterConstants {
        public static final int kShooterPort = 6;
    }

    public static final class TransportConstants {
        public static final int kTransportTopPort = 5;
        public static final int kTransportBotPort = 15;
    }

    public static final class IntakeConstants{
        public static final int kIntakeDeployPort = 11;
        public static final int kIntakeWheelPort = 18;
        public static final int kIntakeLimitSwitchPort = 1;
    }

    public static final class LiftConstants{
        public static final int kLiftPort = 2;
        public static final int kWinchPort = 4; //TODO: TBD
    }

    public static final class VisionPIDConstants {
        public static final double kVisionTurnP = 1;
        public static final double kVisionTurnI = 0;
        public static final double kVisionTurnD = 0;
    }

    public static final class IOConstants {
        public static final int kDriverControllerPort = 0;
        public static final int kAuxiliaryControllerPort = 1;
    }
}