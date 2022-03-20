/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author saimor
 */
@Named
@SessionScoped
public class ContextoBean implements Serializable {
    private Usuario usuarioLogado;
    
    public Usuario getUsuarioLogado(){
        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contexto.getExternalContext();
        String login = contextoExterno.getRemoteUser();
        
        if(this.usuarioLogado == null || !login.equals(this.usuarioLogado.getLogin())){
            if(login != null && !login.isEmpty()){
                UsuarioRN rn = new UsuarioRN();
                this.usuarioLogado = rn.buscarPorLogin(login);
            }
        }
        
        return this.usuarioLogado;
    }
}
