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

package frc.robot;

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