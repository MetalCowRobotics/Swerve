package frc.systems;

import com.analog.adis16470.frc.ADIS16470_IMU;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.lib14.UtilityMethods;
import frc.robot.IMUFixed;
import frc.robot.RobotDashboard;
import frc.robot.RobotMap;
import frc.robot.RobotMap.Drivetrain;

public class DriveTrain {
	// private static final Logger logger = Logger.getLogger(DriveTrain.class.getName());
	public static final PigeonIMU GYRO = new PigeonIMU(RobotMap.Drivetrain.PIGEON_IMU);
	// public static final IMUFixed GYRO = new IMUFixed();
	private static MCR_SRX A_Drive = new MCR_SRX(RobotMap.Drivetrain.A_DRIVE_MOTOR);
	private static MCR_SRX B_Drive = new MCR_SRX(Drivetrain.B_DRIVE_MOTOR); 
	private static MCR_SRX C_Drive = new MCR_SRX(RobotMap.Drivetrain.C_DRIVE_MOTOR);
	private static MCR_SRX D_Drive = new MCR_SRX(Drivetrain.D_DRIVE_MOTOR); 
	private static final RobotDashboard dashboard = RobotDashboard.getInstance();
	private static final MasterControls controller = MasterControls.getInstance();


	public static final double SPRINT_SPEED = 1.0;
	public static final double NORMAL_SPEED = 0.7;
	public static final double CRAWL_SPEED = .5;

	
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
	
	public void arcadeDrive(double speed, double angle) {
	}

	public void tankDrive(double leftSpeed, double rightSpeed) {
	}

	public void stop() {
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
