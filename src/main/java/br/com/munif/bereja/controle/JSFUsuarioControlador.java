/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.bereja.controle;

import br.com.munif.bereja.entidades.Usuario;
import br.com.munif.bereja.negocio.UsuarioService;
import br.com.munif.util.FacesUtil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author maiko
 */
@SessionScoped
@ManagedBean
public class JSFUsuarioControlador {

    private final UsuarioService service;

    private Usuario entidade;
    private List<Usuario> lista;
    private String filtro;
    private Boolean novo;
    private String senhaAnteriorInformar;
    private String senhaAnteriorSalva;

    public JSFUsuarioControlador() {
        this.service = new UsuarioService();
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Usuario getEntidade() {
        return entidade;
    }

    public void setEntidade(Usuario entidade) {
        this.entidade = entidade;
        novo = false;
    }

    public List<Usuario> getLista() {
        if (lista == null) {
            lista = service.lista();
        }
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public void novo() {
        entidade = new Usuario();
        novo = true;
    }

    public void excluir(Usuario aRemover) {
        service.excluir(aRemover.getId());
        FacesUtil.addMessageInfo("Informação", "O objeto foi excluído.");
        lista = null;
    }

    public void salvar() {
        service.salvar(entidade);
        if (novo) {
            FacesUtil.addMessageInfo("Informação", "O registro foi inserido.");
        } else {
            FacesUtil.addMessageInfo("Informação", "O registro foi alterado.");
        }
        lista = null;
    }

    public void cancelar() {
        entidade = null;
        FacesUtil.addMessageWarn("Aviso", "A operação foi cancelada");
    }

    public void filtrar() {
        if (filtro != null) {
            lista = filtrarPor(filtro);
        }
    }

    public List<Usuario> filtrarPor(String s) {
        return service.filtra(s);
    }

    public Boolean isNovoRegistro() {
        return novo;
    }

    public void salvarAlteracaoSenha() {
        salvar();
    }

    public void setSenhaAnteriorInformar(String senhaAnteriorInformar) {
        this.senhaAnteriorInformar = senhaAnteriorInformar;
    }

    public String getSenhaAnteriorInformar() {
        return senhaAnteriorInformar;
    }

    public void editarSenhaUsuario(Usuario entidade) {
        this.senhaAnteriorSalva = entidade.getSenha();
        setEntidade(entidade);
    }

    public void validarSenhaAtual() {
        System.out.println(senhaAnteriorSalva + " - " + senhaAnteriorInformar);
        if (!senhaAnteriorSalva.equals(senhaAnteriorInformar)) {
            throw new ValidatorException(new FacesMessage("Senha atual informada não confere com a senha atual !"));
        }
    }
}
