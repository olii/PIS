package pis.data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Person {
	@Override
	public boolean isAdmin() {
		return true;
	}
}
