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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
    private String senhaAtual;

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
        FacesUtil.addMessageWarn("Aviso", "A operação foi cancelada.");
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

    public String salvarAlteracaoSenha() {
        Usuario usu = service.recuperar(entidade.getId());
        if (!usu.getSenha().equals(senhaAtual)) {
            FacesUtil.addMessageError("Senha atual informada não confere", "");
        } else if (usu.getSenha().equals(entidade.getSenha())) {
            FacesUtil.addMessageError("Nova senha deve ser diferente da senha atual", "");
        } else {
            salvar();
            return "lista";
        }
        return "";
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void editarSenha(Usuario usuario) {
        this.senhaAtual = "";
        setEntidade(usuario);
    }
}
