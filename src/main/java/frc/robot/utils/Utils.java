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
