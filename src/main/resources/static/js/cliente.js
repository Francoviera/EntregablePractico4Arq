document.addEventListener("DOMContentLoaded", function() {
    let estudiantes = [];
    let clientesToView= [];
    let index= 0;
    loading= false;

    window.onscroll = function(ev) {
        if ((window.innerHeight + window.scrollY) >= ((document.body.offsetHeight *80)/100)) {
            if(!loading && index < estudiantes.length){
                loading= true;


                let cantidad= 8;
                if(index+8 > estudiantes.length){
                    cantidad= (estudiantes.length-index)+1;
                }
                for (let i = index-1; i < (index+8); i++) {
                    clientesToView.push(estudiantes[i]);
                }
                index+= 8;
                loading = false;
                
                let string = "";
                clientesToView.forEach(estudiante => {
                    string += `<li href="#" class="list-group-item text-left">
                    <div class="contentEstudiente">
                        <img class="img-thumbnail " src="${getImage()}">
                        <label class="name ms-2">
                            ${estudiante.nombre} ${estudiante.apellido}<br>
                      </label>
                  </div>
                  <div class="abmEstudient">
                      <span class="pull-right">
                          <i class="far fa-eye mt-4"></i>
                          <a class="btn-delete-student" id="${estudiante.nroEstudiante}" type="button"><i class="fas fa-trash-alt text-danger ms-3"></i></a>
                          <i class="fas fa-envelope-square ms-3"></i>
                      </span>
                  </div>
                  <!-- <div class="break"></div> -->
                </li>`;
                });
                document.querySelector(".ctn-clientes").innerHTML = string;
                const btn = document.querySelectorAll(".btn-delete-student");
                for (let i = 0; i < btn.length; i++) {
                    btn[i].addEventListener("click", function() {
                        deleteCliente(btn[i].id)
                    });
                }
            }
        }
    };


    function getImage(){
        let images= [
            "https://www.bootdey.com/img/Content/User_for_snippets.png",
            "https://bootdey.com/img/Content/user_2.jpg"
        ]
        return images[Math.round(Math.random()*1)];
    }

    function getClientes() {
        let myHeaders = new Headers();
        let requestOptions = {
            method: 'GET',
            redirect: 'follow',
        };

        //ESTO SE AVANZA EN LA SEGUNDA PARTE 
        fetch("https://despensa-springboot.herokuapp.com/clientes", requestOptions)
            .then(response => response.json())
            .then(data => {
                estudiantes = data;
                for (let i = 0; i < 8; i++) {
                    clientesToView.push(estudiantes[i]);       
                }
                index= 8;
                let string = "";

                clientesToView.forEach(estudiante => {
                    string += `<li href="#" class="list-group-item text-left">
                    <div class="contentEstudiente">
                        <img class="img-thumbnail " src="${getImage()}">
                        <label class="name ms-2">
                            ${estudiante.nombre} ${estudiante.apellido}<br>
                      </label>
                  </div>
                  <div class="abmEstudient">
                      <span class="pull-right">
                          <i class="far fa-eye mt-4"></i>
                          <a class="btn-delete-student" id="${estudiante.id}" type="button"><i class="fas fa-trash-alt text-danger ms-3"></i></a>
                          <i class="fas fa-envelope-square ms-3"></i>
                      </span>
                  </div>
                  <!-- <div class="break"></div> -->
                </li>`;
                });
                document.querySelector(".ctn-clientes").innerHTML = string;
                const btn = document.querySelectorAll(".btn-delete-student");
                for (let i = 0; i < btn.length; i++) {
                    btn[i].addEventListener("click", function() {
                        deleteCliente(btn[i].id)
                    });
                }
            })
            .catch(error => console.error(error));
    }

    function deleteCliente(id) {
        let myHeaders = new Headers();
        let requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            redirect: 'follow',
        };
        fetch("https://despensa-springboot.herokuapp.com/clientes/" + id, requestOptions)
            .then(res => {
                window.location.reload();
            })
            .catch((error) => console.log(error))
    }

    function filterEstudiante(idEstudiante) {
        let myHeaders = new Headers();
        let requestOptions = {
            method: 'GET',
            redirect: 'follow',
        };

        fetch("http://localhost:8080/EjercicioIntegrador3/registroestudiantes" + "/estudiantes/" + idEstudiante + "", requestOptions)
            .then(response => response.json())
            .then(estudiante => {

                let string = `<li href="#" class="list-group-item text-left">
                    <div class="contentEstudiente">
                        <img class="img-thumbnail " src="https://bootdey.com/img/Content/User_for_snippets.png">
                        <label class="name ms-2">
                            ${estudiante.nombre} ${estudiante.apellido}<br>
                      </label>
                  </div>
                  <div class="abmEstudient">
                      <span class="pull-right">
                          <i class="far fa-eye mt-4"></i>
                          <a class="btn-delete-student" id="${estudiante.nroEstudiante}" type="button"><i class="fas fa-trash-alt color-danger ms-3"></i></a>
                          <i class="fas fa-envelope-square ms-3"></i>
                      </span>
                  </div>
                  <!-- <div class="break"></div> -->
                </li>`;
                document.querySelector(".ctn-estudiantes").innerHTML = string;
                const btn = document.querySelectorAll(".btn-delete-student");
                for (let i = 0; i < btn.length; i++) {
                    btn[i].addEventListener("click", function() {
                        deleteCliente(btn[i].id)
                    });
                }
            })
            .catch(error => console.error(error));
    }

    document.querySelector(".btn-search").addEventListener('click', function() {
        let id = document.querySelector(".input-search").value;
        filterEstudiante(id);
    })
    getClientes();
});