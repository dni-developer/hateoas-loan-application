package net.dni;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan extends ResourceSupport {
    private String loadId;
    private String fullName;
    private boolean pass;
}
