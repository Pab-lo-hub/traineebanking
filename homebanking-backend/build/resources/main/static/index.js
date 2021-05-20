var app = new Vue({
    el: '#app',
    data: {
        datos: null,
        dni: "",
        password: "",
    },
    methods: {
        login: function () {
            if (app.dni.length != 0 && app.password.length != 0) {
                $.post("/api/login", {
                        dni: app.dni,
                        password: app.password
                    }).done(function () {
                            alert("Log In exitoso")
                            location.assign("/account.html");
                        })
                    .catch(function (error) {
                        if (error.status == 401)
                            alert("usurio o clave invalidos")
                        else
                            alert("Ocurrio un error, contacte al administrador")
                    })
                    .catch(function (error) {
                        alert(error.responseText)
                    })
            } else {
                alert("Datos no válidos. Volver a ingresar")
            }
        },
    },
});

