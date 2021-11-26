document.addEventListener("DOMContentLoaded", function() {
    let reportesCarrera = [];
    let reportesCarreraToView = [];
    let index= 0;
    loading= false;

    window.onscroll = function(ev) {
        if ((window.innerHeight + window.scrollY) >= ((document.body.offsetHeight *80)/100)) {
            if(!loading && index < reportesCarrera.length){
                loading= true;

                let cantidad= 8;
                if(index+8 > reportesCarrera.length){
                    cantidad= (reportesCarrera.length-index)+1;
                }
                for (let i = index-1; i < (index+8); i++) {
                    reportesCarreraToView.push(reportesCarrera[i]);
                }
                index+= 8;
                loading = false;
                
                let string= "";
                reportesCarreraToView.forEach(item => {
                    string += `<li href="#" class="list-group-item text-left">
                    <div class="contentEstudiente">
                        <img class="img-thumbnail mb-3"
                            src="${getImage()}">
                        <label class="name ms-4">
                            <h6>Cliente</h6>
                            <p class="text-dark">${item.cliente.nombre+" "+ item.cliente.apellido}</p>
                        </label>
                        <label class="name ms-4">
                            <h6>Cantidad de Pedidos</h6>
                            <p class="text-dark">${item.pedidos.length}</p>
                        </label>
                        <label class="name ms-4">
                            <h6>Fecha de Compra</h6>
                            <p class="text-dark">${dateToString(item.momentoCompra)+"HS"}</p>
                        </label>
                        <label class="name ms-4">
                            <h6>Total</h6>
                            <p class="text-danger">$ ${item.precioTotal.toFixed(2)}</p>
                        </label>
                    </div>
                    <div class="abmEstudient">
                        <span class="pull-right ">
                            <a class="btn-delete text-danger"  id="${item.id}" type="button"><i class="fas fa-trash-alt color-danger ms-3 mt-4"></i></a>
                        </span>
                    </div>
                    <!-- <div class="break"></div> -->
                </li>`;
                });
                document.querySelector(".ctn-pedidos").innerHTML = string;
                const btn = document.querySelectorAll(".btn-delete");
                for (let i = 0; i < btn.length; i++) {
                    btn[i].addEventListener("click", function() {
                        deletePedidos(btn[i].id)
                    });
                }
            }
        }
    };


    function dateToString(date){
        return new Date(date).toLocaleDateString("es-ES", { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric'})
    }

    function getImage(){
        let images= [
            "https://www.bootdey.com/img/Content/User_for_snippets.png",
            "https://bootdey.com/img/Content/user_2.jpg"
        ]
        return images[Math.round(Math.random()*1)];
    }

    function getVentas() {
        let myHeaders = new Headers();
        let requestOptions = {
            method: 'GET',
            redirect: 'follow',
        };

        //ESTO SE AVANZA EN LA SEGUNDA PARTE 
        fetch("https://despensa-springboot.herokuapp.com/pedidos", requestOptions)
            .then(response => response.json())
            .then(data => { 
                reportesCarrera= data;
                for (let i = 0; i < 8; i++) {
                    reportesCarreraToView.push(reportesCarrera[i]);       
                }
                index= 8;
                let string = ""
                reportesCarreraToView.forEach(item => {
                    string += `<li href="#" class="list-group-item text-left">
                    <div class="contentEstudiente">
                        <img class="img-thumbnail mb-3"
                            src="${getImage()}">
                        <label class="name ms-4">
                            <h6>Cliente</h6>
                            <p class="text-dark">${item.cliente.nombre+" "+ item.cliente.apellido}</p>
                        </label>
                        <label class="name ms-4">
                            <h6>Cantidad de Pedidos</h6>
                            <p class="text-dark">${item.pedidos.length}</p>
                        </label>
                        <label class="name ms-4">
                            <h6>Fecha de Compra</h6>
                            <p class="text-dark">${dateToString(item.momentoCompra)+"HS"}</p>
                        </label>
                        <label class="name ms-4">
                            <h6>Total</h6>
                            <p class="text-danger">$ ${item.precioTotal.toFixed(2)}</p>
                        </label>
                    </div>
                    <div class="abmEstudient">
                        <span class="pull-right ">
                            <a class="btn-delete text-danger"  id="${item.id}" type="button"><i class="fas fa-trash-alt color-danger ms-3 mt-4"></i></a>
                        </span>
                    </div>
                    <!-- <div class="break"></div> -->
                </li>`;
                });
                document.querySelector(".ctn-pedidos").innerHTML = string;
                const btn = document.querySelectorAll(".btn-delete");
                for (let i = 0; i < btn.length; i++) {
                    btn[i].addEventListener("click", function() {
                        deletePedidos(btn[i].id)
                    });
                }

            })
            .catch(error => console.error(error));
    }

    function deletePedidos(id) {
        let myHeaders = new Headers();
        let requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            redirect: 'follow',
        };
        fetch("https://despensa-springboot.herokuapp.com/pedidos/" + id, requestOptions)
            .then(res => {
                window.location.reload();
            })
            .catch((error) => console.log(error))
    }

    getVentas();
});