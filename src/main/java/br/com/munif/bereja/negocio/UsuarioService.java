/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.bereja.negocio;

import br.com.munif.bereja.entidades.Usuario;
import br.com.munif.bereja.repositorio.Repositorio;
import br.com.munif.bereja.repositorio.UsuarioRepositorio;
import java.util.List;

/**
 *
 * @author munif
 */
public class UsuarioService extends Service<Usuario>{

    public UsuarioService() {
        super(Usuario.class);
    }
 
    @Override
    protected Repositorio<Usuario> getRepositorio() {
        return new UsuarioRepositorio();
    }
    
    public List<Usuario> filtra(String s){
        return ((UsuarioRepositorio)repositorio).filtra(s);
    }
    
    
    
}
