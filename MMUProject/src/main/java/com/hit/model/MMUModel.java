package com.hit.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.NFUAlgoCacheImpl;
import com.hit.algorithm.Random;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.processes.Process;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;
import com.hit.util.MMULogger;

public class MMUModel extends Observable implements Model{

	private int numProcesses;
	private int ramCapacity;
	private IAlgoCache<Long,Long> algo = null;
	private static String FILE_NAME ;
	private String algoName;

	public MMUModel(String configuration) {
		FILE_NAME = configuration;
	}
		//get from controller ram and algo cache
	public void setConfiguration(List<String> configuration){
		ramCapacity = Integer.parseInt(configuration.get(1));
		MMULogger logger = MMULogger.getInstance();
		//writing to log
		logger.write("RC: "+ramCapacity , Level.INFO);
		setIAlgoCache(configuration.get(0));
		algoName = configuration.get(0);
	}
	
	public List<Process> createProcesses( List<ProcessCycles> appliocationsScenarios, MemoryManagementUnit mmu){
		List<Process> processes = new ArrayList<>();
		numProcesses = appliocationsScenarios.size();
		for(int i = 0; i < appliocationsScenarios.size(); ++i ){ //for each process insert to the array
			processes.add(new Process(i+1, mmu, appliocationsScenarios.get(i)));  
		}
		return processes;
	}
	
	public RunConfiguration readConfigurationFile() {
		//read from Json file
		RunConfiguration runConfiguration = null;
		MMULogger logger = MMULogger.getInstance();
		try{
			runConfiguration = new Gson().fromJson(new JsonReader(new FileReader(FILE_NAME)), RunConfiguration.class);
		}catch(Exception e){
			e.printStackTrace();
			logger.write("[Error] | MMUDriver: readConfigurationFile method - couldn't read json file" , Level.SEVERE);
		}
		return runConfiguration;
	}
	
	public void runProcesses(List<Process> applications){ 
		ExecutorService executor = Executors.newCachedThreadPool();
		MMULogger logger = MMULogger.getInstance();
		@SuppressWarnings("unchecked")
		Future<Boolean> futures[]  = new Future[applications.size()];
		int index = 0; 
		for(Process p : applications)
			try {
			futures[index++] = executor.submit(p);
			}catch(Exception e) {
				e.printStackTrace();
				logger.write("[Error] | [ MMUDriver: runProcesses method ] - executor.submit method " , Level.SEVERE);
		}
		
		executor.shutdown();
		
		for ( index = 0; index < futures.length; index++) {
			try {
				futures[index].get();
			} catch (Exception e) {
				e.printStackTrace();
				logger.write("[Error] | [ MMUDriver: runProcesses method ] - futures[i].get method" , Level.SEVERE);
			}
		}
	}
	
	public void setIAlgoCache(String command) {
		// allocate the algorithm that user insert 
				switch(command){
				case "NFU": algo = new NFUAlgoCacheImpl<>(ramCapacity);
					break;
				case "LRU": algo = new LRUAlgoCacheImpl<>(ramCapacity);
					break;
				case "RANDOM": algo = new Random<>(ramCapacity);
					break;
				}
	}
	
	
	public void readLog() {
		try {
		List<String> logList = new ArrayList<>();
		String command;
		Scanner scan = new Scanner(new FileReader("logs/log.txt"));
		while(scan.hasNextLine()) {
			command = scan.nextLine();
			command = command.replace(":"," ");
			command = command.replace("[", " ");
			command = command.replace(",", " ");
			command = command.replace("]", " ");
			logList.add(command);
		}
		 setChanged();	//for notifyObserver 
		 notifyObservers(logList);//update all the observers
		 scan.close();
		 MMULogger.getInstance().close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start() {
		MMULogger logger = MMULogger.getInstance();
			//building the  MMU
		MemoryManagementUnit mmu = new MemoryManagementUnit(ramCapacity, algo);
			//read into runCofing the data from the json file
		RunConfiguration runConfig = readConfigurationFile();
			//get the processes from runConfig object
		List<ProcessCycles> processCycles = runConfig.getProcessesCycles();
			//create and connect between the processes and MMU
		List<Process> processes = createProcesses(processCycles, mmu);
			//writing to log
		logger.write("PN: "+numProcesses, Level.INFO);

		runProcesses(processes);
		mmu.shutDown();
			//send log to view class
		readLog();
			//send algo name to view class
		 setChanged();	//for notifyObserver 
		 notifyObservers(algoName);//update all the observers
		
	}



}
