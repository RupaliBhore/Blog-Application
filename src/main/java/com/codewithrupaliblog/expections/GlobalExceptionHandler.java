package com.codewithrupaliblog.expections;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codewithrupaliblog.payload.ApiResponse;

//yaha huame custom exception banaya he globaly controller me agar kuch error aa jaye to handle hogi is exception

//@RestControllerAdvice ye annotaion huamre class ko as aexception handler represent karegi is annotaion se hum apane 
// puri controller ki  exception handle kar sakate he
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	
	
	//jab bhi hamare controller me ResourceNotFoundException ye exception aayegi to  ye method chalegi
                     //kis type ki exception handle karana chahte ho ye likho
	@ExceptionHandler(ResourceNotFoundException.class)                //ResourceNotFoundException class ka object milega
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) 
	{
		//ex ki help se hum jo msg waha se throw ho raha he use nikalnege
		String message = ex.getMessage();
		
		                            //resourse nhi mil raha he to response flase jayega
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	
	
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
		Map<String, String> resp = new HashMap<>();
		
		//getallerros karoge to erros ki list mil jayegi use hum ek ek karake featch karenge
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			
			//ek ek feild aur uska msg nikalenge  direct erroe ke object pe getfeild call nahi hoga to use typecast 
			//karte he feilderror se
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			
			//map me sari feild add kardo
			resp.put(fieldName, message);
		});

		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
	}
	
	
	

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

}
