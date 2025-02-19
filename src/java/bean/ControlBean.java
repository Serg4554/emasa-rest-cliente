/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;


import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import pojo.Aviso;
import pojo.Operacion;
import pojo.Usuario;
import rest.services.ClienteJerseyAviso;
import rest.services.ClienteJerseyOperacion;
import rest.services.ClienteJerseyUsuario;


/**
 *
 * @author shiba
 */
@Named(value = "controlBean")
@SessionScoped
public class ControlBean implements Serializable {



    Aviso avisoSeleccionado;
    String latitud, longitud, error, ubicacion, dia, mes, anyo, observaciones;
    String emailUsuario;
    List<Aviso> listaAvisosUsuario;
    List<Operacion> listaOperaciones;
    Usuario usuarioActual;
    ClienteJerseyAviso clienteAvisos;
    ClienteJerseyOperacion clienteOperaciones;
    ClienteJerseyUsuario clienteUsuario;
    String latitudGPS;
    String longitudGPS;

    /**
     * Creates a new instance of ControlBean
     */
    public ControlBean() {
    }

    @PostConstruct
    public void Init(){
        clienteAvisos = new ClienteJerseyAviso();
        clienteOperaciones = new ClienteJerseyOperacion();
        clienteUsuario = new ClienteJerseyUsuario();
        
    }

    public String getLatitudGPS() {
        return latitudGPS;
    }

    public void setLatitudGPS(String latitudGPS) {
        this.latitudGPS = latitudGPS;
    }

    public String getLongitudGPS() {
        return longitudGPS;
    }

    public void setLongitudGPS(String longitudGPS) {
        this.longitudGPS = longitudGPS;
    }
    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnyo() {
        return anyo;
    }

    public void setAnyo(String anyo) {
        this.anyo = anyo;
    }


    public Aviso getAvisoSeleccionado() {
        return avisoSeleccionado;
    }

    public void setAvisoSeleccionado(Aviso avisoSeleccionado) {
        this.avisoSeleccionado = avisoSeleccionado;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public List<Aviso> getListaAvisosUsuario() {
        return listaAvisosUsuario;
    }

    public void setListaAvisosUsuario(List<Aviso> listaAvisosUsuario) {
        this.listaAvisosUsuario = listaAvisosUsuario;
    }

    public List<Operacion> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(List<Operacion> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String mostrarAvisos() {
        obtenerListaAvisos();
        return "mostrarAvisos?faces-redirect=true";
    }
    
    public void obtenerListaAvisos()
    {//procedimiento para obtener la lista de avisos de un usuario
        
        Response respuesta = clienteAvisos.findAvisosPorUsuario_XML(Response.class, emailUsuario);
        
        if(respuesta.getStatus() == 200){
            GenericType<List<Aviso>> genericAvisos = new GenericType<List<Aviso>>(){};
            listaAvisosUsuario = respuesta.readEntity(genericAvisos);
        }
        else{
            error = "error al recuperar la lista de avisos";
        }
    }

    public String verAviso(Aviso aviso) {
        avisoSeleccionado = aviso;
        listaOperaciones = getListaOperacionesAviso();
        if(avisoSeleccionado.getPosGPS() != null && !avisoSeleccionado.getPosGPS().isEmpty()) {
            String[] posGPS = avisoSeleccionado.getPosGPS().split(";");
            latitudGPS = posGPS[0];
            longitudGPS = posGPS[1];
        } else {
            latitudGPS = null;
            longitudGPS = null;
        }
        return "detalleAviso?faces-redirect=true";
    }

    private List<Operacion> getListaOperacionesAviso() {
        List<Operacion> res = null;
        
        Response respuesta = clienteOperaciones.findByIdAviso_XML(Response.class, avisoSeleccionado.getId().toString());
        
        if(respuesta.getStatus() == 200){
            GenericType<List<Operacion>> genericOperaciones = new GenericType<List<Operacion>>(){};
            res = respuesta.readEntity(genericOperaciones);
        }
        return res;
    }


    public void comprobarUsuario() {
        
        Response resCliente = clienteUsuario.find_XML(Response.class, emailUsuario);
        if(resCliente.getStatus() == 204){ //si es un nuevo usuario
            usuarioActual = new Usuario();
            usuarioActual.setEmail(emailUsuario);
            usuarioActual.setOperador(false);
            clienteUsuario.create_XML(usuarioActual);
        }
        else if (resCliente.getStatus() == 200)
        {
            GenericType<Usuario> genUsuario = new GenericType<Usuario>(){};
            usuarioActual = resCliente.readEntity(genUsuario);
        }
        avisoSeleccionado.setUsuarioemail(usuarioActual);
    }

    public String crearAviso() {
        error="";
        return "crearAviso?faces-redirect=true";
    }

    public String doGuardar() {
        error="";
        String fecha;
        avisoSeleccionado = new Aviso();
        if(!dia.isEmpty() && !mes.isEmpty() && !anyo.isEmpty()){
            fecha = dia + "-" + mes + "-" + anyo;
        }else{
            error="Fecha no válida";
            return "crearAviso?faces-redirect=true";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        formatter.applyPattern("dd-MM-yyyy");
        if (fecha != null && !fecha.trim().isEmpty()) {
            try {
                Date date = formatter.parse(fecha);
                avisoSeleccionado.setFechacreacion(date);
            } catch (ParseException ex) {
                error = "Fecha no válida";
                return "crearAviso?faces-redirect=true";
            }
        }
        if(ubicacion==null || ubicacion.isEmpty()){
            error="La ubicación no puede ser vacía";
            return "crearAviso?faces-redirect=true";
        }else{
            avisoSeleccionado.setUbicacion(ubicacion);
        }
        if(observaciones==null || observaciones.isEmpty()){
            error="El campo de observaciones no puede estar vacío";
            return "crearAviso?faces-redirect=true";
        }else{
            avisoSeleccionado.setObservaciones(observaciones);
        }
        
        if(latitud==null || latitud.isEmpty() || longitud == null || longitud.isEmpty()){
            error="Los campos del posicionamiento GPS no pueden ser vacíos";
            return "crearAviso?faces-redirect=true";
        }else{
            double lat = Double.parseDouble(latitud);
            double longi = Double.parseDouble(longitud);
            avisoSeleccionado.setPosGPS(lat+";"+longi);
        }
        avisoSeleccionado.setEstado("En espera de confirmación");
        comprobarUsuario();
        clienteAvisos.create_XML(avisoSeleccionado);
        listaAvisosUsuario.add(avisoSeleccionado);
        limpiarDatos();
        return this.mostrarAvisos();
    }  

    private void limpiarDatos() {
        avisoSeleccionado = null;
        error="";
        dia="";
        mes="";
        anyo="";
        ubicacion="";
        observaciones="";
        latitud="";
        longitud="";
    }
    
}

