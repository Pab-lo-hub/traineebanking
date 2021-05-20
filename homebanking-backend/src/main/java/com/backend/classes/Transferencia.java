package com.backend.classes;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String fecha;

    private String nombreOrigen;

    private Integer cuentaOrigen;

    private String nombreDestinatario;

    private Integer cuentaDestino;

    private String cbuDestinatario;

    private Integer moneda;

    private Integer importe;

    private String plazoAcreditacion;

    private String concepto;

    private Integer nroComprobante;

    public Transferencia(){};

    public Transferencia(String fecha, String nombreOrigen, Integer cuentaOrigen, String nombreDestinatario, Integer cuentaDestino, String cbuDestinatario, Integer moneda, Integer importe, String plazoAcreditacion, String concepto, Integer nroComprobante) {
        this.fecha = fecha;
        this.nombreOrigen = nombreOrigen;
        this.cuentaOrigen = cuentaOrigen;
        this.nombreDestinatario = nombreDestinatario;
        this.cuentaDestino = cuentaDestino;
        this.cbuDestinatario = cbuDestinatario;
        this.moneda = moneda;
        this.importe = importe;
        this.plazoAcreditacion = plazoAcreditacion;
        this.concepto = concepto;
        this.nroComprobante = nroComprobante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreOrigen() {
        return nombreOrigen;
    }

    public void setNombreOrigen(String nombreOrigen) {
        this.nombreOrigen = nombreOrigen;
    }

    public Integer getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Integer cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    public Integer getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Integer cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getCbuDestinatario() {
        return cbuDestinatario;
    }

    public void setCbuDestinatario(String cbuDestinatario) {
        this.cbuDestinatario = cbuDestinatario;
    }

    public Integer getMoneda() {
        return moneda;
    }

    public void setMoneda(Integer moneda) {
        this.moneda = moneda;
    }

    public Integer getImporte() {
        return importe;
    }

    public void setImporte(Integer importe) {
        this.importe = importe;
    }

    public String getPlazoAcreditacion() {
        return plazoAcreditacion;
    }

    public void setPlazoAcreditacion(String plazoAcreditacion) {
        this.plazoAcreditacion = plazoAcreditacion;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(Integer nroComprobante) {
        this.nroComprobante = nroComprobante;
    }
}
