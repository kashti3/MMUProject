package com.hit.driver;

import com.hit.controller.MMUController;
import com.hit.model.MMUModel;
import com.hit.view.MMUView;

public class MMUDriver {
	//add comment
	private static final String configuration = "src\\main\\resources\\com\\hit\\config\\Configuration.json";
	public static void main(String[] args ){
		/**
		 * Build MVC model to demonstrate MMU system actions
		 */
		CLI cli  = new CLI(System.in,System.out);
		MMUModel model = new MMUModel(configuration);

		MMUView view = new MMUView();
		MMUController controller = new MMUController( model , view );
		model .addObserver( controller );
		cli.addObserver( controller );
		new Thread(cli).start();
	}
	
}
