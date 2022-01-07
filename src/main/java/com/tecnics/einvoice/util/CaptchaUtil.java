package com.tecnics.einvoice.util;

import java.util.Random;

public class CaptchaUtil {
	
    public  static Integer generateCaptchaKey() 
    {
        return ( new Random ().nextInt ( 8999 ) + 1000 );      
    }

}
