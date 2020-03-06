/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpiutil.net.PortForwarder;
import frc.robot.commands.*;
import frc.robot.commands.sequences.AutoDrive;
import frc.robot.commands.sequences.ShootSequence;
import frc.robot.subsystems.*;

import static frc.robot.Constants.IOConstants.kAuxiliaryControllerPort;
import static frc.robot.Constants.IOConstants.kDriverControllerPort;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
  public static final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  public static final TransportSubsystem transportSubsystem = new TransportSubsystem();
  public static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static final LiftSubsystem liftSubsystem = new LiftSubsystem();

  public static final XboxController driverController = new XboxController(kDriverControllerPort);
  public static final XboxController auxiliaryController = new XboxController(kAuxiliaryControllerPort);

  private static final SendableChooser<Command> autoChooser = new SendableChooser<>();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Start Camera feed for intake camera (back)
    CameraServer.getInstance().startAutomaticCapture();

    // Grant Access to Raspberry pi via RoboRio
    PortForwarder.add(8888, "frcvision.local", 22);
    PortForwarder.add(9999, "frcvision.local", 80);

    configureButtonBindings();

    autoChooser.setDefaultOption("Auto Test", new AutoDrive());
    SmartDashboard.putData("Auto Modes", autoChooser);

    driveSubsystem.setDefaultCommand(
            new RunCommand(() -> driveSubsystem
                    .arcadeDrive(driverController.getY(Hand.kLeft),
                            driverController.getX(Hand.kRight)), driveSubsystem));

    intakeSubsystem.setDefaultCommand(
            new RunCommand(() -> intakeSubsystem.setIntakeSpeed(1), intakeSubsystem));



  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // ----------------------------------- Driver Controller --------------------------------------------------

    // Max Speed Button
    new JoystickButton(driverController, XboxController.Button.kBumperRight.value)
            .whenPressed(() -> driveSubsystem.setMaxOutput(1))
            .whenReleased(() -> driveSubsystem.setMaxOutput(0.75));

    // 1/2 Speed
    new JoystickButton(driverController, XboxController.Button.kBumperLeft.value)
            .whenPressed(() -> driveSubsystem.setMaxOutput(0.75/2))
            .whenReleased(() -> driveSubsystem.setMaxOutput(0.75));

    // heading lock
    new JoystickButton(driverController, XboxController.Button.kStickLeft.value)
            .whenHeld(
                    new HeadingLock(0))
            .whenReleased(() -> driveSubsystem.resetNavx());

    // targeting system // TODO: DELETE AFTER TEST
    new JoystickButton(driverController, XboxController.Button.kB.value)
            .whenHeld(new PortLock());

    new JoystickButton(driverController, XboxController.Button.kStart.value)
            .whenHeld(new DriveStraight(12));

    // Shoot sequence
    new JoystickButton(driverController, XboxController.Button.kA.value)
            .whenHeld(new ShootSequence());

    // -------------------------------- Auxiliary Controller ---------------------------------------------------

    // Manual Intake Up
    new POVButton(auxiliaryController, 0)
            .whenHeld(new RunCommand(()-> intakeSubsystem.setDeploySpeed(0.5)))
            .whenReleased(new RunCommand(()-> intakeSubsystem.setIntakeSpeed(0)));

    // Manual Intake Down
    new POVButton(auxiliaryController, 180)
            .whenHeld(new RunCommand(()-> intakeSubsystem.setDeploySpeed(-0.5)));

    // Automatic Intake Down
    new POVButton(auxiliaryController, 90)
            .whenHeld(new LowerIntake());

    // Automatic Transport Stall
    new JoystickButton(auxiliaryController, XboxController.Button.kB.value)
            .whenHeld(new RunCommand(()-> transportSubsystem.setTransportSpeed(1,-1)))
            .whenReleased(new RunCommand(()-> transportSubsystem.setTransportSpeed(0)));

    new JoystickButton(auxiliaryController, XboxController.Button.kA.value)
            .whenHeld(new RunCommand(()-> intakeSubsystem.setIntakeSpeed(1)))
            .whenReleased(new RunCommand(()-> intakeSubsystem.setIntakeSpeed(0)));

    // Manual Transport Release
    new JoystickButton(auxiliaryController, XboxController.Button.kBumperLeft.value)
            .whenHeld(new RunCommand(()-> transportSubsystem.setTransportSpeed(1)))
            .whenReleased(new RunCommand(()-> transportSubsystem.setTransportSpeed(0)));

    // Manual Shoot
    new JoystickButton(auxiliaryController, XboxController.Button.kBumperRight.value)
            .whenHeld(new RunCommand(()-> shooterSubsystem.setSpeed(1)))
            .whenReleased(()->shooterSubsystem.setSpeed(0));

    new JoystickButton(auxiliaryController, XboxController.Button.kStart.value)
            .whenHeld(new RaiseLift());

    new JoystickButton(auxiliaryController, XboxController.Button.kBack.value)
            .whenHeld(new RunCommand(()-> liftSubsystem.setWinchSpeed(1)))
            .whenReleased(new RunCommand(()-> liftSubsystem.setWinchSpeed(0)));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}