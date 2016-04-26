package pis.back;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import pis.data.Person;

@FacesConverter("accountTypeConverter")
public class AccountTypeConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent ui, String string) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent ui, Object object) {
		Person person = (Person)object;
		if (person.isStudent()) {
			return "Student";
		}
		else if (person.isTeacher()) {
			return "Teacher";
		}
		else if (person.isAdmin()) {
			return "Admin";
		}
		
		return "Unknown";
	}
}
