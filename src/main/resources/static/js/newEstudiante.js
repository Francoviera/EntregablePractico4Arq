document.addEventListener("DOMContentLoaded", function() {
    let name = document.getElementById("firstName");
    let lastName = document.getElementById("lastName");
    let dni = document.getElementById("dni");

    function createUser() {
        console.log(name.value)
    }

    document.getElementById("buttonSubmit").addEventListener("click", function() {
        if (name.value != " " && lastName.value != " " && dni.value != " " ) {
            let user = {
                "nombre": name.value,
                "apellido": lastName.value,
                "dni": dni.value,
            }
            let myHeaders = new Headers();
            myHeaders.append('Content-Type', 'application/json');
            myHeaders.append('Access-Control-Allow-Origin', '*');
            let data = JSON.stringify(user);
            let requestOptions = {
                method: 'POST',
                headers: myHeaders,
                redirect: 'follow',
                body: data,
            };
            fetch("https://despensa-springboot.herokuapp.com/clientes", requestOptions)
                .then(res => res.json())
                .then(json => {
                    console.log(json);
                })
        } else {
            alert("complete todos los campos")
        }
    })
});