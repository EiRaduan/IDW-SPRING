
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.util.DigestUtils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author saimor
 */

@Named
@RequestScoped

public class UsuarioBean {
    private Usuario usuario = new Usuario();
    private String confirmarSenha;
    private String senhaMD5;
    private List<Usuario> lista;
    private String cargo;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public List<Usuario> getLista() {
        if(this.lista == null){
            UsuarioRN usuarioRN = new UsuarioRN();
            this.lista = usuarioRN.listar();
        }
        
        return this.lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public String getDestinoSalvar() {
        return destinoSalvar;
    }

    public void setDestinoSalvar(String destinoSalvar) {
        this.destinoSalvar = destinoSalvar;
    }
    private String destinoSalvar;

    
    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getConfirmarSenha() {
        return this.confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }
    
    public String novo(){
        this.destinoSalvar = "usuarioSucesso";
        this.usuario = new Usuario();
        this.usuario.setAtivo(true);
        return "usuario";
    }
    
    public String salvar(){
        FacesContext context = FacesContext.getCurrentInstance();        
        String senha = this.usuario.getSenha();
  
        if(senha != null && !senha.isEmpty()){
            if(!senha.equals(this.confirmarSenha)){
                FacesMessage facesMessage = new FacesMessage("A senha não foi confirmada corretamente");
                context.addMessage(null, facesMessage);
                return null;
            }
            String senhaHash = DigestUtils.md5DigestAsHex(senha.getBytes());
            this.usuario.setSenha(senhaHash);
        } else {
            String senhaAleatoria = DigestUtils.md5DigestAsHex(senha.getBytes());
            this.usuario.setSenha(senhaAleatoria);
        }
        
        this.usuario.getPermissao().add(cargo);
        if(cargo.equals("HOLE_ADMINISTRADOR")){
            this.usuario.getPermissao().add("ROLE_USUARIO");
        }
        
        UsuarioRN usuarioRN = new UsuarioRN();
        usuarioRN.salvar(this.usuario);
        
        return this.destinoSalvar;
    }
    
    public List<Usuario> getList(){
        if(this.lista == null){
            UsuarioRN usuarioRN = new UsuarioRN();
            this.lista = usuarioRN.listar();
        }
        return this.lista;
    }
    
   public String editar(){
       this.senhaMD5 = this.usuario.getSenha();
       return "/Publico/usuario";
   }
   
   public String excluir(){
       UsuarioRN usuarioRN = new UsuarioRN();
       usuarioRN.excluir(this.usuario);
       this.lista = null;
       return null;
   }
   
   public String ativar(){
       if(this.usuario.isAtivo()){
           this.usuario.setAtivo(false);
       } else {
           this.usuario.setAtivo(true);
       }
       
       UsuarioRN usuarioRN = new UsuarioRN();
       usuarioRN.salvar(this.usuario);
       return null;
   }
   
   public String atribuiPermissao(Usuario usuario, String permissao){
        this.usuario = usuario;
        java.util.Set<String> permissoes = this.usuario.getPermissao();
        
        if(permissoes.contains(permissao)){
            permissoes.remove(permissao);
        }else{
            permissoes.add(permissao);
        }
        
        UsuarioRN usuarioRN = new UsuarioRN();
        usuarioRN.salvar(this.usuario);
        return null;
    }

    public String getSenhaMD5() {
        return senhaMD5;
    }

    public void setSenhaMD5(String senhaMD5) {
        this.senhaMD5 = senhaMD5;
    }
   
   
    
}
