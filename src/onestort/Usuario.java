/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;

/**
 *
 * @author Henrique
 */
public class Usuario {

   private String Nome;
   private String Endereco;
   private String Cidade;
   private String UF;
   private String Telefone;
   private String user;
   private String Senha;
   private String ID;
   private String Materias;
    
    
    public Usuario(String Nome, String Endereco, String Cidade, String UF, String Telefone, String user, String Senha, String ID,String Materias) {
        this.Nome = Nome;
        this.Endereco = Endereco;
        this.Cidade = Cidade;
        this.UF = UF;
        this.Telefone = Telefone;
        this.user = user;
        this.Senha = Senha;
        this.ID = ID;
        this.Materias=Materias;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMaterias() {
        return Materias;
    }

    public void setMaterias(String Materias) {
        this.Materias = Materias;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String Endereco) {
        this.Endereco = Endereco;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String Cidade) {
        this.Cidade = Cidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }

    public String getLogin() {
        return user;
    }

    public void setLogin(String user) {
        this.user = user;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    
}
