package com.example.consumingrest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.bind.annotation.RequestBody;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	private String fecha;
	private String nombreOrigen;
	private Float cuentaOrigen;
	private String nombreDestinatario;
	private Float cuentaDestino;
	private String cbuDestinatario;
	private String moneda;
	private Float importe;
	private String plazoAcreditacion;
	private String concepto;
	private Float nroComprobante;

	public Account() {
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

	public String getNombreDestinatario() {
		return nombreDestinatario;
	}

	public void setNombreDestinatario(String nombreDestinatario) {
		this.nombreDestinatario = nombreDestinatario;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
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

	public Float getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(Float cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	public Float getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(Float cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	public String getCbuDestinatario() {
		return cbuDestinatario;
	}

	public void setCbuDestinatario(String cbuDestinatario) {
		this.cbuDestinatario = cbuDestinatario;
	}

	public Float getImporte() {
		return importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	public Float getNroComprobante() {
		return nroComprobante;
	}

	public void setNroComprobante(Float nroComprobante) {
		this.nroComprobante = nroComprobante;
	}
}