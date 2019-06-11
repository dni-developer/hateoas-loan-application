package net.dni;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mortgage extends ResourceSupport {
    private String mortgageId;
    private String fullName;
    private boolean pass;
}
