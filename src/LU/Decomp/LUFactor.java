package LU.Decomp;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import java.lang.Math;

public class LUFactor extends Activity {
    /** Called when the activity is first created. */
    RadioButton LUFact;
	RadioButton PLUFact;
    Button buttonCalc, zero, one, two, three, four, five, six, seven, eight, nine, minus;
    Button backspace, newline, space, clear, selA, selB;
    EditText matrix;
    EditText numMessages;
    EditText bVect;
    CheckBox check;
    double [][] matrixHldr,U,L;
    double [] b,y,x;
    int []tempHldr = new int [101];
    int []bTempHldr = new int [11];
    int []p;
	boolean inRMenu = false;
	boolean isLUFactor = true, solveSys=false, editA = true;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { 
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        	case R.id.about:
            	setContentView(R.layout.about);   
            	inRMenu = true;
        		return true;
        	case R.id.settings:
        		
            	setContentView(R.layout.factor);
            	
            	inRMenu = true;
            	
            	OnClickListener radio_listener = new OnClickListener() {
            	    public void onClick(View v) {
            	        // Perform action on clicks
            	        RadioButton rb = (RadioButton) v;
            	        String factorStr = "Program will now factor in " + rb.getText() + " form.";
            	        Toast.makeText(getBaseContext(), factorStr, Toast.LENGTH_LONG).show();
            	        if(LUFact.isChecked())
            	        	isLUFactor = true;
            	        else
            	        	isLUFactor = false;
            	    }
            	};
            	
            	OnClickListener check_listener = new OnClickListener() {
            	    public void onClick(View v) {
            	        // Perform action on clicks
            	        //RadioButton rb = (RadioButton) v;
            	        if(check.isChecked()){
                	        String factorStr = "Program will solve the system;\n\t(P)LU x = b.";
                	        Toast.makeText(getBaseContext(), factorStr, Toast.LENGTH_LONG).show();
            	        	solveSys = true;
            	        }
            	        else {
                	        String factorStr = "The program will no longer solve systems.";
                	        Toast.makeText(getBaseContext(), factorStr, Toast.LENGTH_LONG).show();
            	        	solveSys = false;
            	        }
            	    }
            	};
            	
                LUFact = (RadioButton) findViewById(R.id.LUFact);
            	PLUFact = (RadioButton) findViewById(R.id.PLUFact);
            	check = (CheckBox) findViewById(R.id.solveQ);
            	
            	check.setOnClickListener(check_listener);
            	LUFact.setOnClickListener(radio_listener);
            	PLUFact.setOnClickListener(radio_listener);
            	
            	if(solveSys)
            		check.setChecked(true);
            	
            	if(isLUFactor==true)
            		LUFact.setChecked(true);
            	else
            		PLUFact.setChecked(true);
            	
        		return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && inRMenu) {
        	if(solveSys)
        		showSolve();
        	else
        		showMain();
        	
        	inRMenu = false;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    private void showMain(){
        setContentView(R.layout.main2);        
        
        buttonCalc = (Button) findViewById(R.id.btnCalc);
        matrix = (EditText) findViewById(R.id.matrix);
        zero = (Button) findViewById(R.id.zero);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        backspace = (Button) findViewById(R.id.backspace);
        newline = (Button) findViewById(R.id.newLine);
        space = (Button) findViewById(R.id.space);
        clear = (Button) findViewById(R.id.clear);
        minus = (Button) findViewById(R.id.minus);
        
        OnClickListener characterlistener = new View.OnClickListener() {
            public void onClick(View v) 
            { 
       	        Button button = (Button) v;
       	        String updateStr = button.getText().toString();
       	        matrix.append(updateStr);
       	        
            }
        };
        
        OnClickListener spacelistener = new View.OnClickListener() {
            public void onClick(View v) 
            { 
       	        matrix.append(" ");  
            }
        };
 
        OnClickListener clearListener = new View.OnClickListener() {
            public void onClick(View v) 
            { 
       	        matrix.setText("");  
            }
        };
        
        OnClickListener nlListener = new View.OnClickListener() {
            public void onClick(View v) 
            { 
       	        matrix.append("\n");  
            }
        };
        
        OnClickListener bSpace = new View.OnClickListener() {
            public void onClick(View v) 
            { 
            	String set = matrix.getText().toString();
            	//(0, matrix.length() - 1);
            	if(set.length()>0)
            		matrix.setText(set.subSequence(0, set.length()-1) );  
            }
        };
             
        zero.setOnClickListener(characterlistener);
        one.setOnClickListener(characterlistener);
        two.setOnClickListener(characterlistener);
        three.setOnClickListener(characterlistener);
        four.setOnClickListener(characterlistener);
        five.setOnClickListener(characterlistener);
        six.setOnClickListener(characterlistener);
        seven.setOnClickListener(characterlistener);
        eight.setOnClickListener(characterlistener);
        nine.setOnClickListener(characterlistener);
        minus.setOnClickListener(characterlistener);
        newline.setOnClickListener(nlListener);
        space.setOnClickListener(spacelistener);
        backspace.setOnClickListener(bSpace);
        clear.setOnClickListener(clearListener);

        buttonCalc.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            { 
                int index=0;
                String matrixStr = matrix.getText().toString();
                
            	Pattern p = Pattern.compile("-?\\d+");
            	Matcher m = p.matcher(matrixStr);
            	while (m.find()) {
                	tempHldr[index]=Integer.parseInt(m.group());
                	index++;
            	}

            	double index2 = index;
            	index = 0;
            	if(Math.sqrt(index2)==Math.floor(Math.sqrt(index2))){
            		int rows = (int) Math.sqrt(index2);
            	    matrixHldr = new double [rows][rows];
            	    
            	    for(int i=0; i < rows; i++)
            	    	for(int j = 0; j <rows; j++){
            	    		matrixHldr[i][j] = tempHldr[index];
            	    		index++;
            	    	}
                	if(isLUFactor)
                		LUDecomp((int) Math.sqrt(index2));
                	else
                		PLUDecomp((int) Math.sqrt(index2));

            	} 
            	else
                	Toast.makeText(getBaseContext(), "The matrix you entered is not square.", Toast.LENGTH_LONG).show();
            }
        });  
    	
    	return;
    
    }
    
    public void showSolve(){
    	
        setContentView(R.layout.solver);        
        
        buttonCalc = (Button) findViewById(R.id.btnSolv);
        matrix = (EditText) findViewById(R.id.A);
        bVect = (EditText) findViewById(R.id.b);
        
        zero = (Button) findViewById(R.id.zeroo);
        one = (Button) findViewById(R.id.onee);
        two = (Button) findViewById(R.id.twoo);
        three = (Button) findViewById(R.id.threee);
        four = (Button) findViewById(R.id.fourr);
        five = (Button) findViewById(R.id.fivee);
        six = (Button) findViewById(R.id.sixx);
        seven = (Button) findViewById(R.id.sevenn);
        eight = (Button) findViewById(R.id.eightt);
        nine = (Button) findViewById(R.id.ninee);
        backspace = (Button) findViewById(R.id.backspacee);
        newline = (Button) findViewById(R.id.newLinee);
        space = (Button) findViewById(R.id.spacee);
        clear = (Button) findViewById(R.id.clearr);
        minus = (Button) findViewById(R.id.minuss);
        
        selA = (Button) findViewById(R.id.btnASel);
        selB = (Button) findViewById(R.id.btnBSel);
        
        OnClickListener aSelc = new View.OnClickListener() {
            public void onClick(View v) 
            { 
            	editA=true;
            	Toast.makeText(getBaseContext(), "Now editing A", Toast.LENGTH_SHORT).show();
       	        
            }
        };
        
        OnClickListener bSelc = new View.OnClickListener() {
            public void onClick(View v) 
            { 
            	editA=false;
            	Toast.makeText(getBaseContext(), "Now editing vector b", Toast.LENGTH_SHORT).show();
            }
        };
        
        selA.setOnClickListener(aSelc);
        selB.setOnClickListener(bSelc);
        
        OnClickListener characterlistener = new View.OnClickListener() {
            public void onClick(View v) 
            { 
       	        Button button = (Button) v;
       	        String updateStr = button.getText().toString();
       	        if(editA)
       	        	matrix.append(updateStr);
       	        else
       	        	bVect.append(updateStr);
       	        
            }
        };
        
        OnClickListener spacelistener = new View.OnClickListener() {
            public void onClick(View v) 
            { 
            	if(editA)
            		matrix.append(" ");  
            	else
       	        	bVect.append("\n");
            		
            }
        };
 
        OnClickListener clearListener = new View.OnClickListener() {
            public void onClick(View v) 
            { 
       	        matrix.setText("");  
       	        bVect.setText("");
            }
        };
        
        OnClickListener nlListener = new View.OnClickListener() {
            public void onClick(View v) 
            { 
            	if(editA)
            		matrix.append("\n");
            	else
            		bVect.append("\n");
            }
        };
        
        OnClickListener bSpace = new View.OnClickListener() {
            public void onClick(View v) 
            { 
        		String set;
            	if(editA)
            		set = matrix.getText().toString();
            	else
            		set = bVect.getText().toString();
            		
            	//(0, matrix.length() - 1);
            	if(editA){
            		if(set.length()>0){
            			matrix.setText(set.subSequence(0, set.length()-1) );
            		}
            	}
            	else
                	if(set.length()>0)
                		bVect.setText(set.subSequence(0, set.length()-1) ); 
            }
        };
             
        zero.setOnClickListener(characterlistener);
        one.setOnClickListener(characterlistener);
        two.setOnClickListener(characterlistener);
        three.setOnClickListener(characterlistener);
        four.setOnClickListener(characterlistener);
        five.setOnClickListener(characterlistener);
        six.setOnClickListener(characterlistener);
        seven.setOnClickListener(characterlistener);
        eight.setOnClickListener(characterlistener);
        nine.setOnClickListener(characterlistener);
        minus.setOnClickListener(characterlistener);
        newline.setOnClickListener(nlListener);
        space.setOnClickListener(spacelistener);
        backspace.setOnClickListener(bSpace);
        clear.setOnClickListener(clearListener);

        buttonCalc.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            { 
                int index=0;
                int bCount=0;
                
                String matrixStr = matrix.getText().toString();
                String bStr = bVect.getText().toString();
                
            	Pattern p = Pattern.compile("-?\\d+");
            	Matcher m = p.matcher(matrixStr);
            	while (m.find()) {
                	tempHldr[index]=Integer.parseInt(m.group());
                	index++;
            	}
            
            	Pattern z = Pattern.compile("-?\\d+");
            	Matcher l = z.matcher(bStr);
            	while (l.find()) {
                	bTempHldr[bCount]=Integer.parseInt(l.group());
                	bCount++;
            	}

            	double index2 = index;
            	index = 0;
            	
            	if(Math.sqrt(index2)==Math.floor(Math.sqrt(index2)) && bCount == (int)Math.sqrt(index2)){
            		
            		int rows = (int) Math.sqrt(index2);
            	    matrixHldr = new double [rows][rows];
            	    b = new double [rows];
            	    
            	    for(int i=0; i < rows; i++){
            	    	b[i] = bTempHldr[i];
            	    	for(int j = 0; j <rows; j++){
            	    		matrixHldr[i][j] = tempHldr[index];
            	    		index++;
            	    	}
            	    }
            	    
                	if(isLUFactor)
                		LUDecompSol((int) Math.sqrt(index2));
                	else
                		PLUDecompSol((int) Math.sqrt(index2));

            	} 
            	else{
            		if(Math.sqrt(index2)!=Math.floor(Math.sqrt(index2)))
            			Toast.makeText(getBaseContext(), "The matrix you entered is not square.", Toast.LENGTH_LONG).show();
            		else
                    	Toast.makeText(getBaseContext(), "The length of b does not match A", Toast.LENGTH_LONG).show();
            	}


            }
        });  
    	
    	return;
    }
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
 
        showMain();     
    }
    
    public void PLUDecomp(int len){
    	
    	p = new int [len];
    	
    	for(int i=0; i < len; i++) 
    		p[i] = i;

    	double highest; 
    	int rowHldr, temp;
    	
		for(int k=0; k < (len); k++){
          
    		//for keeping the row and highest number  
    		highest = 0;
        	rowHldr = k;
        
        	//find the largest value in the column of submatrix
        	for(int i = k; i < len; i++) {
        		if ( Math.abs(matrixHldr[i][k]) > highest){
        			highest = matrixHldr[i][k];
                	rowHldr = i;
        		}
        	}
        	
    		//if(highest==0) {
    		//	Toast.makeText(getBaseContext(), "Cannot factor a matrix with linearly dependent row vectors.", Toast.LENGTH_LONG).show();
    		//	return;
    		//}
       
			//if the pivot is greatest in current row, no swap 
			if(rowHldr != k) {
				
				swap(k, rowHldr, len);
				//have swap happen in p vector to keep track of swaps
				temp = p[rowHldr];
				p[rowHldr] = p[k];
				p[k]= temp;
    		}
       
			eliminate(k,k,len);
            
		}
    	

		String answer = buildPLU(len);
		AlertDialog.Builder abouts = new AlertDialog.Builder(this);
		abouts.setTitle("PLU Decomposition");
		abouts.setMessage(answer);
		abouts.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
      
			}
		});
		//Display about dialog
		abouts.show();
		
		
        //showLU();
    	return;
    }
    
    public void LUDecomp(int len){
    	
    	for(int k=0; k < (len); k++){
    		if(matrixHldr[k][k]!=0)
    			eliminate(k,k,len);
    		else
            	Toast.makeText(getBaseContext(), "Warning. Has a zero for a pivot, which is unstable...", Toast.LENGTH_LONG).show();
    			
    	}
    	
		String answer = buildLU(len);
		
		AlertDialog.Builder abouts = new AlertDialog.Builder(this);
		abouts.setTitle("LU Decomposition");
		abouts.setMessage(answer);
		abouts.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
      
			}
		});
		//Display about dialog
		abouts.show();
    	
    	
    	return;
    }

	public void swap(int row1, int row2, int length) {
		
		double temp;
		
		for(int l = 0; l < length; l++){
            temp = matrixHldr[row1][l];
            matrixHldr[row1][l] = matrixHldr[row2][l];
            matrixHldr[row2][l] = temp;
		}
		
		return;
	}
	
	public String buildLU(int len){
		String L = "",U = "", toNotify="";
		
		double determn = matrixHldr[0][0];
		
		for(int k = 1; k < len; k++)
			determn *= matrixHldr[k][k];
		
		for(int i=0; i< len; i++){
			for(int j=0; j<len; j++){
				
				if(i>j){
					L+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
					U+= "+0.00";
				}
				else if(j>i) {
					L+= "+0.00";
					U+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
				}
				else{
					L+= "+1.00";
					U+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
				}
				
				if((j+1)!=len){
					L+=" ";
					U+=" ";
				}
			}
			L+='\n';
			U+='\n';
		}
		
		toNotify += "L=\n" + L + "U=\n" + U;
		
		if(determn == 0)
			toNotify += "\nThe Matrix is singular; det(A) = 0";
		else
			toNotify += "\n The Matrix is nonsingular; det(A) = " + String.format(Locale.US, "%+3.2f", determn);
		
		return toNotify;
	}
	
	public String buildPLU(int len){
		
		String L = "",U = "",P = "",toNotify="";
		
		double determn = matrixHldr[0][0];
		
		for(int k = 1; k < len; k++)
			determn *= matrixHldr[k][k];
		
		for(int i=0; i< len; i++){
			for(int j=0; j<len; j++){
				
				if(p[i] == j)
					P +="1";
				else
					P +="0";
				
				if(i>j){
					L+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
					U+= "+0.00";
				}
				else if(j>i) {
					L+= "+0.00";
					U+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
				}
				else{
					L+= "+1.00";
					U+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
				}
				
				if((j+1)!=len){
					L+=" ";
					U+=" ";
					P+=" ";
				}
			}
			L+='\n';
			U+='\n';
			P+='\n';
		}
		
		toNotify += "P=\n" + P + "L=\n" + L + "U=\n" + U;
		
		if(determn == 0)
			toNotify += "\nThe Matrix is singular; det(A) = 0";
		else
			toNotify += "\n The Matrix is nonsingular; det(A) = " + String.format(Locale.US, "%+3.2f", determn);
		
		return toNotify;
	}
	
	public void eliminate(int row, int col, int len){
		
		double temp;
		
	    for(int i=row+1; i<len;i++){
	      //if(mat[i][col]!=0)
	        for(int k=(len-1);k>=col;k--){
	        	if(matrixHldr[row][col]!=0)
	        		if(k==col)
	        			matrixHldr[i][k] = matrixHldr[i][col]/matrixHldr[row][col];
	        		else
	        		{
	        			temp = matrixHldr[i][col]/matrixHldr[row][col];
	        			matrixHldr[i][k] -= matrixHldr[row][k]*temp;
	        		}
			}
	    }
		
		return;
	}
	
	public void forwardSub(int len){
		
		y = new double [len];
	   
	   for(int i=0; i<len;i++){
	        for(int l=0; l <= i; l++){
	           if(l!=i)
	               b[i] = b[i] - L[i][l]*y[l];
	           else{
	               y[i] = b[i];
	               
	           }
	        }
		}
		
		return;
	}
	
	public void backwardSub(int len){
		x = new double [len];
		
		   for(int i=(len-1); i>=0; i--){
	       		for(int l= (len-1); l >= i; l--){
	       			if(l!=i)
	       				y[i]= y[i] - U[i][l]*x[l];
	       			else {
	       				x[i] = y[i] / U[i][i];

	       			}
	       		}
			}
		
		return;
	}
	
    public void LUDecompSol(int len){
    	
    	for(int k=0; k < (len); k++){
    		if(matrixHldr[k][k]!=0)
    			eliminate(k,k,len);
    		else
            	Toast.makeText(getBaseContext(), "Warning. Has a zero for a pivot, which is unstable...", Toast.LENGTH_LONG).show();
    			
    	}
    	
    	L = new double [len][len];
    	U = new double [len][len];
    	
		for(int i=0; i< len; i++){
			for(int j=0; j<len; j++){
				
				if(i>j){
					L[i][j]= matrixHldr[i][j];
					U[i][j]= 0;
				}
				else if(j>i) {
					L[i][j]= 0;
					U[i][j]= matrixHldr[i][j];
				}
				else{
					L[i][j]= 1;
					U[i][j]= matrixHldr[i][j];
				}
			}
		}
    	
		forwardSub(len);
		backwardSub(len);
    	
    	
		String answer = buildLUSol(len);
		
		AlertDialog.Builder abouts = new AlertDialog.Builder(this);
		abouts.setTitle("LU Decomposition");
		abouts.setMessage(answer);
		abouts.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
      
			}
		});
		//Display about dialog
		abouts.show(); 
    	
    	
    	return;
    }
    
	public String buildLUSol(int len){
		String L = "",U = "", X="", toNotify="";
		
		double determn = matrixHldr[0][0];
		
		for(int k = 1; k < len; k++)
			determn *= matrixHldr[k][k];
		
		for(int i=0; i< len; i++){
			
			X += String.format(Locale.US, "%+3.2f", x[i]);
			
			for(int j=0; j<len; j++){
				
				if(i>j){
					L+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
					U+= "+0.00";
				}
				else if(j>i) {
					L+= "+0.00";
					U+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
				}
				else{
					L+= "+1.00";
					U+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
				}
				
				if((j+1)!=len){
					L+=" ";
					U+=" ";
				}
			}
			L+='\n';
			U+='\n';
			X+='\n';
		}
		
		toNotify += "L=\n" + L + "U=\n" + U + "x=\n" + X;
		
		if(determn == 0)
			toNotify += "\nThe Matrix is singular; det(A) = 0";
		else
			toNotify += "\n The Matrix is nonsingular; det(A) = " + String.format(Locale.US, "%+3.2f", determn);
		
		return toNotify;
	}
	
    public void PLUDecompSol(int len){
    	
    	p = new int [len];
    	double []tempB = new double [len];
    	
    	for(int i=0; i < len; i++) 
    		p[i] = i;

    	double highest; 
    	int rowHldr, temp;
    	
		for(int k=0; k < (len); k++){
          
    		//for keeping the row and highest number  
    		highest = 0;
        	rowHldr = k;
        
        	//find the largest value in the column of submatrix
        	for(int i = k; i < len; i++) {
        		if ( Math.abs(matrixHldr[i][k]) > highest){
        			highest = matrixHldr[i][k];
                	rowHldr = i;
        		}
        	}
        	
    		//if(highest==0) {
    		//	Toast.makeText(getBaseContext(), "Cannot factor a matrix with linearly dependent row vectors.", Toast.LENGTH_LONG).show();
    		//	return;
    		//}
       
			//if the pivot is greatest in current row, no swap 
			if(rowHldr != k) {
				
				swap(k, rowHldr, len);
				//swapB(k, rowHldr);
				//have swap happen in p vector to keep track of swaps
				temp = p[rowHldr];
				p[rowHldr] = p[k];
				p[k]= temp;
    		}
       
			eliminate(k,k,len);
            
		}
		
		//for(int i = len-1, j=0; i>=0; i--, j++)
		//	tempB[j]=len-b[i];
		
    	L = new double [len][len];
    	U = new double [len][len];
    	
		for(int i=0; i< len; i++){
			for(int j=0; j<len; j++){
				
				if(i>j){
					L[i][j]= matrixHldr[i][j];
					U[i][j]= 0;
				}
				else if(j>i) {
					L[i][j]= 0;
					U[i][j]= matrixHldr[i][j];
				}
				else{
					L[i][j]= 1;
					U[i][j]= matrixHldr[i][j];
				}
			}
		}
    	
		forwardSub(len);
		
		backwardSub(len);
    	
    	
		String answer = buildPLUSol(len);
		
		AlertDialog.Builder abouts = new AlertDialog.Builder(this);
		abouts.setTitle("PLU Decomposition");
		abouts.setMessage(answer);
		abouts.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
      
			}
		});
		//Display about dialog
		abouts.show(); 
    	
    	
    	return;
    }
    
    public void swapB(int loc1, int loc2){
    	double temp;
    	
    	temp = b[loc1];
    	b[loc1] = b[loc2];
    	b[loc2] = temp;
    	
    	return;
    }
    
	public String buildPLUSol(int len){
		String L = "",U = "", X="",P="", toNotify="";
		
		double determn = matrixHldr[0][0];
		
		for(int k = 1; k < len; k++)
			determn *= matrixHldr[k][k];
		
		for(int i=0; i< len; i++){
			
			X += String.format(Locale.US, "%+3.2f", x[i]);
			
			for(int j=0; j<len; j++){
				
				if(p[i] == j)
					P +="1";
				else
					P +="0";
				
				if(i>j){
					
					L+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
					U+= "+0.00";
				}
				else if(j>i) {
					L+= "+0.00";
					U+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
				}
				else{
					L+= "+1.00";
					U+= String.format(Locale.US, "%+3.2f", matrixHldr[i][j]);
				}
				
				if((j+1)!=len){
					L+=" ";
					U+=" ";
					P+=" ";
				}
			}
			L+='\n';
			U+='\n';
			X+='\n';
			P+='\n';
		}
		
		toNotify += "P=\n"+ P +" L=\n" + L + "U=\n" + U + "The solver for PLU currently doesn't work.";// + "x=\n" + X;
		
		if(determn == 0)
			toNotify += "\nThe Matrix is singular; det(A) = 0";
		else
			toNotify += "\n The Matrix is nonsingular; det(A) = " + String.format(Locale.US, "%+3.2f", determn);
		
		return toNotify;
	}
}