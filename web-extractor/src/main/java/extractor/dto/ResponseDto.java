package extractor.dto;

import org.apache.http.HttpStatus;

import lombok.Data;

@Data
public class ResponseDto {

	private String status;
	private Integer code;
	private Long timestamp;
	
	public static ResponseDto getSucessResponse() {
		var dto = new ResponseDto();
		
		dto.setStatus("success");
		dto.setCode(HttpStatus.SC_OK);
		dto.setTimestamp(System.currentTimeMillis());
		
		return dto;
	}
	
}
