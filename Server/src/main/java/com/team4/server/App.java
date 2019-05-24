package com.team4.server;

import java.io.File;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class App {
	static String path = "/home/kwkwon/DeviceFolder/";
	static public ArrayList<Student> List = new ArrayList<Student>();
	static String classID = "Mobile Computing";

	public enum State {
<<<<<<< HEAD
		Register, GetPlan, UploadFiles, Clustring, ClusteringComplete, Final
=======
		Register, GetPlan, UploadFiles, Clustring, Final
>>>>>>> 8b035a0514dc41a721f7e548fbed8df8c1b001c0
	}

	public static State state;

	public static void main(String[] args) {
		System.out.println("System Started!!");
		resetAll();
		SpringApplication.run(App.class, args);
	}

	public static void resetAll() {
		System.out.println("\nSystem Reset!!\n");
		List.clear();
		File deleteFolder = new File(path);
		File[] deleteFolderList = deleteFolder.listFiles();
		for (int i = 0; i < deleteFolderList.length; i++) {
			File[] fileList = deleteFolderList[i].listFiles();
			for (int j = 0; j < fileList.length; j++) {
				fileList[j].delete();
			}
			deleteFolderList[i].delete();
		}
		state = State.Register;
	}

	public static int searchDevice(String DEVICEID) {

		for (int i = 0; i < List.size(); i++) {
			if (List.get(i).getDeviceID().equals(DEVICEID)) {
				return i;
			}
		}
		return -1;
	}

	public static void moveNextStage() {
		if (state == State.Register)
			state = State.GetPlan;
		else if (state == State.GetPlan)
<<<<<<< HEAD
=======
			state = State.UploadFiles;
		else if (state == State.UploadFiles)
>>>>>>> 8b035a0514dc41a721f7e548fbed8df8c1b001c0
			state = State.Clustring;
		else if (state == State.Clustring)
			state = State.Final;
	}
}
