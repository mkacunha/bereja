package br.com.munif.bereja.entidades;

import br.com.munif.bereja.entidades.util.SuperEntidade;
import javax.persistence.Entity;
import org.hibernate.envers.Audited;

@Audited
@Entity
public class Usuario extends SuperEntidade  {
   
    private String nome;
    private String email;
    private String senha;
    private String nivel;

    public Usuario() {
        nivel="cervejeiro";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
