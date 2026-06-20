/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entities.Usuario;
import enums.Rol;
import exception.EntidadNoEncontradaException;
import exception.MailDuplicadoException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ian
 */

public class UsuarioService {

    private List<Usuario> usuarios = new ArrayList<>();
    private Long contadorId = 1L;

    public Usuario crear(String nombre, String apellido, String mail, String celular,
                         String contrasenia, Rol rol) throws MailDuplicadoException {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (mail == null || mail.isBlank()) {
            throw new IllegalArgumentException("El mail no puede estar vacío.");
        }
        
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail)) {
                throw new MailDuplicadoException("Ya existe un usuario con ese mail.");
            }
        }

        Usuario usuario = new Usuario(nombre, apellido, mail, celular, contrasenia, rol, contadorId);
        usuarios.add(usuario);
        contadorId++;
        return usuario;
    }

    public List<Usuario> listar() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                activos.add(u);
            }
        }
        return activos;
    }

    public void editar(Long id, String nombre, String apellido, String mail,
                       String celular, String contrasenia, Rol rol)
            throws EntidadNoEncontradaException, MailDuplicadoException {

        Usuario usuario = buscarPorId(id);

        if (mail == null || mail.isBlank()) {
            throw new IllegalArgumentException("El mail no puede estar vacío.");
        }

        for (Usuario u : usuarios) {
            if (!u.isEliminado()
                    && !u.getId().equals(id)
                    && u.getMail().equalsIgnoreCase(mail)) {
                throw new MailDuplicadoException("Ya existe otro usuario con ese mail.");
            }
        }

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setCelular(celular);
        usuario.setContrasenia(contrasenia);
        usuario.setRol(rol);
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Usuario usuario = buscarPorId(id);
        usuario.setEliminado(true);
    }

    public Usuario buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getId().equals(id)) {
                return u;
            }
        }
        throw new EntidadNoEncontradaException("No existe un usuario con id " + id);
    }
}
