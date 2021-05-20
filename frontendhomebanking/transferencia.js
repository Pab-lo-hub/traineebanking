var app = new Vue({
    el: '#app',
    data: {
        datos: null,
        dni: "",
        accounts: null,
        accountByDni: null,
        tipoDni: "",
        baseTipoDni: "",
        ticket:"",
        transferencia: {
            "nombreOrigen": "Lorenzo Machado",
            "cuentaOrigen": 2334045,
            "nombreDestinatario": "",
            "cuentaDestino": "",
            "cbuDestinatario": "",
            "moneda": "",
            "importe": "",
            "plazoAcreditacion": "",
            "concepto": "",
        },
        estadoTrans: "Pendiente",
    },
    methods: {
        getAccount: function () {
            if (app.tipoDni == "") {
                alert("Complete los dos campos");
            } else {
                var URL_GET = "http://localhost:8080/api/middleware/" + app.dni + "/" + app.tipoDni;
                fetch(URL_GET, {
                        method: 'GET',
                        headers: {},
                    })
                    .then(function (response) {
                        if (response.ok) {
                            return response.json();
                        } else {
                            throw new Error(response.statusText)
                        };
                    })
                    .then(function (json) {
                        app.accountByDni = json;
                        // console.log(json);
                        alert(app.accountByDni.tipoDeCuenta + "\n" + app.accountByDni.saldo);
                    }).catch(function (error) {
                        alert("Revise los datos ingresados");
                        console.log("Request failed: " + error.message);
                    });
            }
        },
        sendTransferencia: function () {
            if(app.transferencia.cbuDestinatario.length != 22){
                alert("El CBU debe contener 22 digitos")
            }
            else{
                $.post({
                    headers: {
                        'Content-type': 'application/json; charset=utf-8',
                    },
                    url: "http://localhost:8080/api/middleware/xml",
                    data: JSON.stringify({
                        "nombreOrigen": app.transferencia.nombreOrigen,
                        "cuentaOrigen": app.transferencia.cuentaOrigen,
                        "nombreDestinatario": app.transferencia.nombreDestinatario,
                        "cuentaDestino": app.transferencia.cuentaDestino,
                        "cbuDestinatario": app.transferencia.cbuDestinatario,
                        "moneda": app.transferencia.moneda,
                        "importe": app.transferencia.importe,
                        "plazoAcreditacion": app.transferencia.plazoAcreditacion,
                        "concepto": app.transferencia.concepto,
                    }),
                    success: function (data){
                        app.ticket = JSON.parse(data);
                        alert (
                            "TRANSFERENCIA REALIZADA CON EXITO" + "\n"+
                        "Numero de comprobante:" + " " + app.ticket.comprobante + "\n"+
                        "Nombre de Origen:" + " "+ app.transferencia.nombreOrigen +"\n"+
                        "Cuenta Origen:"+ " "+ app.transferencia.cuentaOrigen+"\n"+
                        "Nombre Destinatario:"+ " "+ "Jorge"+"\n"+
                        "Cuenta Destino:"+ " "+ app.transferencia.cuentaDestino+"\n"+
                        "CBU Destinatario:"+ " "+ "xxxxxxx" + app.transferencia.cbuDestinatario.substring(13,23)+"\n"+
                        "Moneda:"+ " "+ app.transferencia.moneda+"\n"+
                        "Importe:"+ " "+ app.transferencia.importe+"\n"+
                        "Plazo de Acreditacion:"+ " "+ app.transferencia.plazoAcreditacion+"\n"+
                        "Concepto:"+ " "+ app.transferencia.concepto
                        );
                        location.href = "http://localhost:8081/account.html";
                    }                
                })
            }
        }
    },
    filters: {},
});


var URL_GET = "http://localhost:8080/api/middleware";
fetch(URL_GET, {
        method: 'GET',
        headers: {},
    })
    .then(function (response) {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error(response.statusText)
        };
    })
    .then(function (json) {
        app.datos = json;
        app.accounts = json.account;
        // console.log(json);
    }).catch(function (error) {
        console.log("Request failed: " + error.message);
    });