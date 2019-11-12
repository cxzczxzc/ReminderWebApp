package ca.sheridancollege.beans;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
	private String phoneNumber;
	private String code;
	private String error;
}
