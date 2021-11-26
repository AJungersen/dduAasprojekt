/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 *
 * @author danie
 */
public class Crypt {
    int[] primes = primes();
    

    public Crypt() {
    }
    
    String encrypt(String code){
      int encryptedNumb = 1;
      int codeLength = code.length();
      for(int i = 0; i < codeLength; i++){
          int t = (int)code.charAt(i);
          encryptedNumb = encryptedNumb * (int) Math.pow(primes[i],t);
      }
        System.out.println(encryptedNumb);
        return String.valueOf(encryptedNumb);
        
    }
    
    int[] primes(){
      int[] primeList = new int[15];
      int primeCounter = 0;
      int counter;
        for (int i = 1; i <= 48; i++)  	   
      { 		 		  
         counter=0; 		  
         for(int num = i; num>1; num--)
         {
	    if(i%num==0)
	    {
		counter = counter + 1;
	    }
	 }
	 if (counter == 1)
	 {
	    primeList[primeCounter] = i;
            primeCounter++;
	 }
    }
        return primeList;
}
}
