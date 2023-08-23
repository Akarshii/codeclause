package project;

import javax.swing.JButton;
import javax.swing.JLabel;

public class timer extends javax.swing.JFrame {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int miliseconds=0;
	 static int seconds=0;
	 static int minutes=0;
	 static int hours=0;
	
	 static boolean state=true;
	 
	 public timer() {
		 initcomponents();
 }
	 @SuppressWarnings("unchecked")
	 
	 private void initcomponents() {
		 
		  minute = new javax.swing.JLabel("");
		 second = new javax.swing.JLabel("");
		  milisecond = new javax.swing.JLabel("");
		  hour = new javax.swing.JLabel("");
		 JButton jbutton1 = new javax.swing.JButton();
		 JButton jbutton4 = new javax.swing.JButton();
		 JButton jbutton5 = new javax.swing.JButton();
		 
		 setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		 setTitle("StopWatch");
		 
		 minute.setFont(new java.awt.Font("Times New Roman",0,48));
		 minute.setText("00 :");
		 
		 
		 second.setFont(new java.awt.Font("Times New Roman",0,48));
		 second.setText("00 :");
		 
		 milisecond.setFont(new java.awt.Font("Times New Roman",0,36));
		 milisecond.setText("00 :");
		 
		 hour.setFont(new java.awt.Font("Times New Roman",0,48));
		 hour.setText("00 :");
		 
		 jbutton1.setFont(new java.awt.Font("Times New Roman",0,24));
		 jbutton1.setText("Stop");
		 jbutton1.addActionListener(new java.awt.event.ActionListener(){
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
				 jButton1ActionPerformed(evt);
			 }
		 });
		 
		 jbutton4.setFont(new java.awt.Font("Times New Roman",0,24));
		 jbutton4.setText("Reset");
		 jbutton4.addActionListener(new java.awt.event.ActionListener(){
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
				 jButton4ActionPerformed(evt);
			 }
		 });
		 
		 jbutton5.setFont(new java.awt.Font("Times New Roman",0,24));
		 jbutton5.setText("Start");
		 jbutton5.addActionListener(new java.awt.event.ActionListener(){
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
				 jButton5ActionPerformed(evt);
			 }
		 });
		 
		 javax.swing.GroupLayout layout=new javax.swing.GroupLayout(getContentPane());
		 getContentPane().setLayout(layout);
		 layout.setHorizontalGroup(
				 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				 .addGroup(layout.createSequentialGroup()
						 .addContainerGap()
						 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								 .addComponent(jbutton5)
								 .addComponent(hour,javax.swing.GroupLayout.Alignment.TRAILING))
						  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING))
						  .addGroup(layout.createSequentialGroup()
                         .addGap(6,6,6)
                         .addComponent(minute)
                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                         .addComponent(second)
                         .addGap(10,10,10)
                         .addComponent(milisecond))
						  .addGroup(layout.createSequentialGroup()
								  .addGap(27,27,27)
								  .addComponent(jbutton1)
								  .addGap(28,28,28)
								  .addComponent(jbutton4,javax.swing.GroupLayout.PREFERRED_SIZE,104,javax.swing.GroupLayout.PREFERRED_SIZE))
				 .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE))
				 );
		
		 layout.setVerticalGroup(
				 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				 .addGroup(layout.createSequentialGroup()
						 .addContainerGap()
						 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								 .addComponent(hour)
								 .addComponent(minute)
								 .addComponent(second)
								 .addComponent(milisecond))
						 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jbutton5)
								.addComponent(jbutton1)
								.addComponent(jbutton4))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE))
                         
                         );
		 pack(); 
	 }
	 private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
		 state=true;
		
		 Thread t= new Thread() {
			 public void run() {
				  for (;;) {
					 if(state==true) {
						 try {
							 Thread.sleep(1);
							  
							 if(miliseconds>1000) {
								 miliseconds=0;
								 seconds++;
							 }
							 if (seconds>60) {
								 miliseconds=0;
								 seconds=0;
								 minutes++;
							 }
							  if (minutes>60) {
								  miliseconds=0;
								  seconds=0;
								  minutes=0;
								  hours++;
								  }
							  System.out.println("Working");
							  milisecond.setText(" : "+miliseconds);
							  miliseconds++;
							  second.setText(" : "+seconds);
							  minute.setText(" : "+minutes);
							  hour.setText(""+hours);
						 }
						 catch(Exception e) {
						System.out.println(e);	 
						 }
					 }
						 else{
							 break;
						 }
					 }
				 }
			 };
		 t.start();
	 }

     public void  jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    	 state= false;
     }
     public void  jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
    	 state=false;
    	 
    	  hours=0;
    	  minutes=0;
    	  seconds=0;
    	  miliseconds=0;
    	  
    	  hour.setText("00 :");
    	  minute.setText("00 :");
    	  second.setText("00 :");
    	  milisecond.setText("00");
    	  
     }
     public static void main(String args []) {
    	 try {
    		 for(javax.swing.UIManager.LookAndFeelInfo info: javax.swing.UIManager.getInstalledLookAndFeels()){
    			 if("Nimbus".equals(info.getName())) {
    				 javax.swing.UIManager.setLookAndFeel(info.getClassName());
    				 break;
    			 }
    		 }
    	 }
    		 catch(ClassNotFoundException ex) {
    			 java.util.logging.Logger.getLogger(timer.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
    		 }
    		 catch(InstantiationException ex) {
    			 java.util.logging.Logger.getLogger(timer.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
    		 }
    		 catch(IllegalAccessException ex) {
    			 java.util.logging.Logger.getLogger(timer.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
    		 }
    		 catch(javax.swing.UnsupportedLookAndFeelException ex) {
    			 java.util.logging.Logger.getLogger(timer.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
    		 }
    		 java.awt.EventQueue.invokeLater(new Runnable() {
    			 public void run() {
    				 new timer().setVisible(true);
    			 }
    		 } );
    	 }
     
     
    	 private javax.swing.JLabel hour;
    	 private javax.swing.JButton jButton1;
    	 private javax.swing.JButton jButton4;
    	 private javax.swing.JButton jButton5;
    	 private javax.swing.JLabel milisecond;
    	 private javax.swing.JLabel minute;
    	 private javax.swing.JLabel second;
    	 
     }
    	 

     
		 
	 
	 

