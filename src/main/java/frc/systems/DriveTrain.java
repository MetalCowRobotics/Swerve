package frc.systems;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.RobotDashboard;
import frc.robot.RobotMap;
import frc.robot.RobotMap.Drivetrain;

public class DriveTrain {
	// private static final Logger logger = Logger.getLogger(DriveTrain.class.getName());
	public static final PigeonIMU pigeon = new PigeonIMU(RobotMap.Drivetrain.PIGEON_IMU);
	// public static final IMUFixed GYRO = new IMUFixed();
	private static TalonFX A_Drive = new TalonFX(RobotMap.Drivetrain.A_DRIVE_MOTOR);
	private static TalonFX B_Drive = new TalonFX(Drivetrain.B_DRIVE_MOTOR); 
	private static TalonFX C_Drive = new TalonFX(RobotMap.Drivetrain.C_DRIVE_MOTOR);
	private static TalonFX D_Drive = new TalonFX(Drivetrain.D_DRIVE_MOTOR); 
	private static final RobotDashboard dashboard = RobotDashboard.getInstance();
	private static final MasterControls controller = MasterControls.getInstance();

	private static  Orchestra orchestra;
	private static ArrayList<TalonFX> instruments = new ArrayList<TalonFX>();


	public static final double SPRINT_SPEED = 1.0;
	public static final double NORMAL_SPEED = 0.7;
	public static final double CRAWL_SPEED = .5;

	private double[] ypr = new double[3];

	
	private int inverted = 1;
	private static final DriveTrain instance = new DriveTrain();
		
	

	// Singleton
	protected DriveTrain() {
		// set to coast
		A_Drive.configFactoryDefault();
		A_Drive.setNeutralMode(NeutralMode.Coast);
		B_Drive.configFactoryDefault();
		B_Drive.setNeutralMode(NeutralMode.Coast);
		C_Drive.configFactoryDefault();
		C_Drive.setNeutralMode(NeutralMode.Coast);
		D_Drive.configFactoryDefault();
		D_Drive.setNeutralMode(NeutralMode.Coast);

		// set to ramp
		A_Drive.configOpenloopRamp(.25);
		B_Drive.configOpenloopRamp(.25);
		C_Drive.configOpenloopRamp(.25);
		D_Drive.configOpenloopRamp(.25);

		// rightFrontMotor.configOpenloopRamp(Drivetrain.RAMP_SPEED);
		// leftFrontMotor.configOpenloopRamp(Drivetrain.RAMP_SPEED);
		// rightFrontMotor.setNeutralMode(NeutralMode.Brake);
		// leftFrontMotor.setNeutralMode(NeutralMode.Brake);
		// logger.setLevel(RobotMap.LogLevels.driveTrainClass);
	}

	public static DriveTrain getInstance() {
		return instance;
	}

	public void drive() {
		updatePigeon();
		if (controller.invertDrive()) {
			invert();
		}
		double speed = (controller.forwardSpeed() - controller.reverseSpeed()) * inverted * getThrottle();
		// drive.curvatureDrive(speed, controller.direction(), false);
		// drive.arcadeDrive(speed, controller.direction() * getThrottle());
		if (controller.isCrawlToggle()) {
		} else {
		}

		// drive.arcadeDrive(speed, controller.direction() * UtilityMethods.absMin(getThrottle(), .7));
		//testing
	}

	public void initSong(String chirp) {
		orchestra.loadMusic(chirp);
	}

	public void initInstruments() {
		instruments.add(A_Drive);
		instruments.add(B_Drive);
		orchestra = new Orchestra(instruments);
	}
	
	public void playSong() {
		orchestra.play();
	}
	public void arcadeDrive(double speed, double angle) {
	}

	public void tankDrive(double leftSpeed, double rightSpeed) {
	}

	public void stop() {
	}

	private void updatePigeon() {
		pigeon.getYawPitchRoll(ypr);
	}

	public double getYaw() {
		return ypr[0];
	}

	/**
	 * Determine the top speed threshold: CRAWL - Lowest speed threshold Normal -
	 * Normal driving conditions SPRINT - Highest speed threshold
	 * 
	 * @link org.usfirst.frc.team4213.robot.RobotMap
	 */
	private double getThrottle() {
		if (controller.isCrawlToggle()) {
			return CRAWL_SPEED;
		} else if (controller.isSprintToggle()) {
			return SPRINT_SPEED; 
		} else {
			return NORMAL_SPEED;
		}
	}

	private void invert() {
		inverted = inverted * -1;
	}
}
