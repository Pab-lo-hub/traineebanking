var app = new Vue({
    el: '#app',
    data: {
        datos: null,
        dni: "",
        accounts: null,
        accountByDni: null,
    },
    methods: {
        getAccount: function(){
            $.get({
                url:"/api/account/"+app.dni

            })
            .done(function (response){
                app.accountByDni = response;
                console.log(response);

            })
            .fail(function(error){
                var newError =  JSON.parse( error.responseText);
                alert(newError.error);
            })
        }
    },
    filters: {
    },
});

fetch("/api/clients", {
        method: 'GET',
        headers: {}
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
        app.accounts =json.account;
        console.log(json);
    }).catch(function (error) {
        console.log("Request failed: " + error.message);
    });