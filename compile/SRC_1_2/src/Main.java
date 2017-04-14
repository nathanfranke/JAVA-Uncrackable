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

        JLabel warndigits = new JLabel(whiteSpace(140) + "WARNING: Over 1000 digits may take over 10 seconds to complete!" + whiteSpace(140));
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
        
	}
	
	public static String TerminalPassword (String password, int digits, String extra) {
		
		otherARGS = extra;
        
    	int currentHash = new String(password).hashCode();
    	
    	String[] finalChars = new String[digits];
    	
    	for(int x = 0; x < digits; x++) {

    		currentHash = (otherARGS + digits + ":" + currentHash).hashCode();
    		
    		int indexChar = Math.abs(currentHash) % 94 + 33;
    		
    		finalChars[x] = Character.toString((char)indexChar);
    		
    	}
    	
    	String output ="";
        for(String str : finalChars)
            output+=str;
    	
    	return output;
		
	}

}