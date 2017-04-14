import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
	
	public static String whiteSpace (int width) {
		
		String space = new String(new char[width]).replace('\0', ' ');
		
		return space;
		
	}

    public static String otherARGS = "";
    
    public static JTextField[] extra;
    
    public static int amountExtra = 0;

    public static String spass = "";
    public static String sdigits = "";
    public static String textra = "";
    
	public static void main (String[] args) {
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("-e") || args[i].equals("--extra")) {
				if(args.length >= i + 1) {
					amountExtra = Integer.parseInt(args[i+1]);
				}
			}
			
			if(args[i].equals("-p") || args[i].equals("--pass")) {
				if(args.length >= i + 1) {
					spass = args[i+1];
				}
			}
			
			if(args[i].equals("-d") || args[i].equals("--digits")) {
				if(args.length >= i + 1) {
					sdigits = args[i+1];
				}
			}
			
			if(args[i].equals("-te") || args[i].equals("--textra")) {
				if(args.length >= i + 1) {
					textra = args[i+1];
				}
			}
			
			if(args[i].equals("-h") || args[i].equals("--help")) {
				System.out.print(""
						+ "\n** "
						+ "\n** Created by nathanthesnooper"
						+ "\n** "
						+ "\n** Flags:"
						+ "\n** [-e,--extra] [integer > 0]: Amount of extra fields (GUI)"
						+ "\n** [-p,--pass] [\"password\"]: Password input (TERMINAL) [\"Quotes\" REQUIRED for passwords with spaces!]"
						+ "\n** [-d,--digits] [integer > 0]: Amount of digits for output password (TERMINAL)"
						+ "\n** [-te,--textra] [extra1,extra2,extra3]...: Extra field(s) for more security (TERMINAL)"
						+ "\n** [-h, --help]: Shows this menu"
						+ "\n**\n\n"
						);
				return;
			}
		}
		
		if(!spass.equals("")) {
			if(!sdigits.equals("")) {
				System.out.println("\nI: " + spass + "\nO: " + TerminalPassword(spass, Integer.parseInt(sdigits), (textra+",")));
				return;
			}
		}

        JFrame f = new JFrame("Uncrackable");
        f.getContentPane().setLayout(new FlowLayout());

        f.setSize(1280, 720);
        
        JLabel jl1 = new JLabel("                   Password: ");
        f.getContentPane().add(jl1);
        JPasswordField pass = new JPasswordField(96);
        pass.setText(spass);
        pass.setEchoChar('*');
        pass.setHorizontalAlignment(JTextField.CENTER);
        f.getContentPane().add(pass);

        JLabel jl2 = new JLabel("    Confirm Password: ");
        f.getContentPane().add(jl2);
        JPasswordField passconf = new JPasswordField(96);
        passconf.setEchoChar('*');
        passconf.setHorizontalAlignment(JTextField.CENTER);
        f.getContentPane().add(passconf);
        
        JLabel jl3 = new JLabel("       Password Digits: ");
        f.getContentPane().add(jl3);
        JTextField digits = new JTextField(96);
        digits.setText(sdigits);
        digits.setHorizontalAlignment(JTextField.CENTER);
        f.getContentPane().add(digits);

        JLabel warndigits = new JLabel(whiteSpace(140) + "Est. Time: " + "0s" + whiteSpace(140));
        f.getContentPane().add(warndigits);
        
        extra = new JTextField[amountExtra];
        
    	for(int i = 0; i < amountExtra; i++) {
	        JLabel argL = new JLabel(" " + (i + 1) + ": Other Arguments: ");
	        f.getContentPane().add(argL);
	        JTextField argTF = new JTextField(96);
	        digits.setHorizontalAlignment(JTextField.CENTER);
	        f.getContentPane().add(argTF);
	        extra[i] = argTF;
        }
        
        JButton button1 = new JButton(whiteSpace(140) + "Un-Crackable-Ify" + whiteSpace(140));
        f.add(button1);

        JLabel jl4 = new JLabel("                       Output: ");
        f.getContentPane().add(jl4);
        JTextField out = new JTextField(96);
        out.setHorizontalAlignment(JTextField.CENTER);
        
        f.getContentPane().add(out);
        
        f.setVisible(true);
        
        button1.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	
            	if(new String(pass.getPassword()).equals(new String(passconf.getPassword()))) {
            		
            		int getDigits = 0;
            		
            		try {
            			getDigits = Integer.parseInt(digits.getText());
            		} catch (Exception ex) {
            			out.setText("Please use an integer number for digits!");
            			return;
            		}
            		
            		otherARGS = "";
	            	
	            	for(int i = 0; i < amountExtra; i++) {
	        	        otherARGS = otherARGS + (extra[i].getText() + ",");
	                }
	            	
	            	if(amountExtra == 0) {
	            		otherARGS = ",";
	            	}
            		
            		out.setText(TerminalPassword(new String(pass.getPassword()), getDigits, otherARGS));
            	
            	} else {
            		out.setText("Please Re-type password!");
        			return;
                }
            }
        });
        
        while (true) {
        	String time = whiteSpace(20) + "(Error)" + whiteSpace(20);
        	try {
        		int timeint = Integer.parseInt(digits.getText()) / 10000;
        		time = ("Est. Time: " + timeint + "s");
        		//if(timeint > 60) {
        		//	time = ((timeint / 60) + "m");
        		//}
        		if(timeint > 60) {
        			time = "Est. Time: " + ">5m" + "; " + Math.round(Integer.parseInt(digits.getText()) / 102.4) / 10.0 + "kb";
        		}
        		if(timeint >= 100) {
        			time = "MEMORY LEAK; WILL NOT CALCULATE!";
        			//I was having trouble having it work at all
        		}
        		if(Integer.parseInt(digits.getText()) > 4760) {
        			time+=("; Numbers greater than 4760 will not render correctly");
        		}
        	} catch (Exception e) {
        		
        	}
        	warndigits.setText(time);
        }
        
	}
	
	public static String TerminalPassword (String password, int digits, String extra) {
		
		otherARGS = extra;
        
    	int currentHash = new String(password).hashCode();
    	
    	String[] finalChars = new String[digits];
    	
    	String completeHash = "";
    	
    	for (int i = 0; i < digits; i++) {
    		
    		/*if(currentHash < 1024) {
    			currentHash = (otherARGS + digits + ":" + currentHash).hashCode();
    		} else {
    			currentHash = currentHash / 256;
    		}*/ //Not working
    		
    		currentHash = (otherARGS + digits + ":" + currentHash + ";" + completeHash).hashCode();
    		
    		System.out.println("hash: " + currentHash);
    		completeHash += currentHash;
    		
    		int indexChar = Math.abs(currentHash) % 94 + 33;

    		finalChars[i] = Character.toString((char)indexChar);
    		
    		/*while (currentHash > 1024 && i < digits) {
    			currentHash = currentHash / 256;
    			System.out.println("hash2: " + currentHash);
    			if(currentHash > 256) {
    				i++;
    	    		finalChars[i] = Character.toString((char)indexChar);
    			}
    		}*/ //Memory Leak
    		
    	}
    	
    	String output ="";
        for(String str : finalChars)
            output+=str;
    	
    	return output;
		
	}

}