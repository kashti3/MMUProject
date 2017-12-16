package com.hit.driver;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;

import com.hit.util.MMULogger;

public class CLI extends Observable implements Runnable {

	private static final String LRU = "LRU";
	private static final String NFU = "NFU";
	private static final String RANDOM = "RANDOM";
	private static final String START = "start";
	private static final String STOP = "stop";

	private Scanner cin;
	private OutputStreamWriter cout;

	@SuppressWarnings("resource")
	public CLI(InputStream in, OutputStream out){
		this.cin = new Scanner(in).useDelimiter("\n");
		this.cout = new OutputStreamWriter(out);
	}

	@Override
	public void run() {
		MMULogger logger = MMULogger.getInstance();
		List<String> inputArr = new ArrayList<>();
		String input = null;
		String [] inputArray = null;
		boolean isStop = false;
		try{
			while(!isStop){
				input = cin.next();
				input = input.substring(0, input.length()-1);

				isStop = waitingForStart(input,isStop); 
				 
				
				 while(!isStop) {
					 
					 if(!isStop && input.equals(START)) {
						 write(input); //user insert START
					 }
					 input = cin.next();
					 inputArray = (input.substring(0, input.length()-1)).split(" ");
					 //inputArray = cin.next().split(" ");
					 if(inputArray.length == 2){
						 switch(inputArray[0]){
						 case STOP: 
							 isStop = true;
							 break;
						 case LRU:
						 case NFU:
						 case RANDOM:
							 	//check if the second value is number
							 if(inputArray[1].matches("^[0-9]+$")){
								 inputArr.add(inputArray[0]);
								 inputArr.add(inputArray[1]);
								 setChanged();	//for notifyObserver 
								 notifyObservers(inputArr);//update all the observers
								 inputArr.clear();
								 cout.write("sucessfull\n");
								 cout.flush();
								 input = cin.next();
								 input = input.substring(0, input.length()-1);
								 isStop = waitingForStart(input,isStop);
							 } else {
								 write("not valid");
							 }
							 break;
						 default: write("not valid");
						 break;
						 } //end switch
						 //end if length == 2
					 }else if(inputArray[0].equals(STOP)){
						 isStop = true;
					 }else{
						 write("not valid");
					 }
				 }// end while - user insert stop
				 //write(STOP);
			}
		}// end try
		catch(Exception e){
			e.printStackTrace();
			logger.write("[Error] | [ CLI run method ]", Level.SEVERE);
		}finally{
			try{
				cin.close();
				cout.close();
			}catch(Exception e){
				e.printStackTrace();
				logger.write("[Error] | [ CLI: run method ] - couldn't close scanner/OutputStreamWriter ", Level.SEVERE);
			}
		}
	}

	public void write(String string) 
	{		 //print to the screen
		MMULogger logger = MMULogger.getInstance();
		try{
			switch(string){
			case START: cout.write("Please enter required algorithm and RAM capacity\n");
			cout.flush();
			break;
			case STOP:cout.write("Thank you!\n");
			cout.flush();
			break;
			default: cout.write("Not a valid command!\n");
			cout.flush();
			break;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.write("[Error] | [ CLI : write method ] ", Level.SEVERE);
		}
	}



	private Boolean waitingForStart(String input,Boolean isStop){

		while(!input.equals(START) && (!isStop) ){
			if(input.equals(STOP)) {
				isStop = true;
				write(input);
			} else {
				write("not valid");
				input = cin.next();
				input = input.substring(0, input.length()-1);
				//inputArray = (input.substring(0, input.length()-2)).split(" ");
			}
			if(input.equals(START)) {
				write(input);
			}
		}
		return isStop;
	}
}
