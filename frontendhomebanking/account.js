var app = new Vue({
    el: '#app',
    data: {
        datos: null,
        dni: "",
        accounts: null,
        accountByDni: null,
        tipoDni: "",
        baseTipoDni: "",
        transferencia: {
            "nombreOrigen": "",
            "cuentaOrigen": 0,
            "nombreDestinatario": "",
            "cuentaDestino": 0,
            "cbuDestinatario": 0,
            "moneda": "",
            "importe": 0,
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
                var URL_GET = "http://localhost:8080//api/middleware/" + app.dni + "/" + app.tipoDni;
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
                        console.log(json);
                        alert(app.accountByDni.tipoDeCuenta + "\n" + app.accountByDni.saldo);
                    }).catch(function (error) {
                        alert("Revise los datos ingresados");
                        console.log("Request failed: " + error.message);
                    });
            }
        },
        sendTransferencia: function () {
            $.post({
                    headers: {
                        'Content-type': 'application/json; charset=utf-8',
                    },
                    url: "http://localhost:8080/api/middleware/xml",
                    crossDomain: true,
                    data: JSON.stringify({
                        "nombreOrigen": "Dataldo",
                        "cuentaOrigen": "8965325",
                        "nombreDestinatario": "pedro",
                        "cuentaDestino": "54564456",
                        "cbuDestinatario": "1234567891234567892223",
                        "moneda": "Pesos Argentinos",
                        "importe": "5456456445",
                        "plazoAcreditacion": "A cinco DECADAS",
                        "concepto": "Varios",
                    }),
                    success: function (data){
                        alert (data);
                    }                
                })
        }
    },
    filters: {},
});


var URL_GET = "https://middleware-homeb.herokuapp.com/api/middleware";
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
        console.log(json);
    }).catch(function (error) {
        console.log("Request failed: " + error.message);
    });