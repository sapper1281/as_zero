/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.service.errorhandlers;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class SessionPhaseListener implements PhaseListener {

    private static final String homepage = "errorpagetimeout.xhtml";

    @Override
    public void afterPhase(PhaseEvent event) {
        //Do anything
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        ExternalContext ext = context.getExternalContext();
        HttpSession session = (HttpSession) ext.getSession(false);
        boolean newSession = (session == null) || (session.isNew());
        boolean postback = !ext.getRequestParameterMap().isEmpty();
        boolean timedout = postback && newSession;
        if (timedout) {
            Application app = context.getApplication();
            ViewHandler viewHandler = app.getViewHandler();
            UIViewRoot view = viewHandler.createView(context, "/" + homepage);
            context.setViewRoot(view);
            context.renderResponse();
            try {
                viewHandler.renderView(context, view);
                context.responseComplete();
            } catch (Throwable t) {
                throw new FacesException("Session timed out", t);
            }
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}

