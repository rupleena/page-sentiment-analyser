package analyser.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PageRequest {

    @NotBlank
    String url;
}
