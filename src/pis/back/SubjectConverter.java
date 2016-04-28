package pis.back;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import pis.data.Subject;
import pis.service.SubjectManager;

@FacesConverter(value = "subjectConverter")
public class SubjectConverter implements Converter {
	@EJB
	private SubjectManager manager;

	@Override
	public Object getAsObject(FacesContext context, UIComponent comp, String str) {
		return manager.findByName(str);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent comp, Object obj) {
		if (!(obj instanceof Subject))
			return null;
		
		return ((Subject)obj).getName();
	}
}
