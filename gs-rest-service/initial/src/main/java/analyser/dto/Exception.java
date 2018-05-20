package analyser.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exception {

    private String errorCode;
    private String errorMessage;
    private List<String> errors;
}