
const API_URL = "http://localhost:8080";

function entrarAdmi(){
    console.log("entrando...");
    listarAdmisiones();
    
    const formA = document.getElementById('frm');
    formA.addEventListener('submit', function(e) {
       e.preventDefault();
       enviarFrmAdd();
    });
}

async function listarAdmisiones() {
    const tabla = document.querySelector("#tbl-admi");
    await fetch(`${API_URL}/api/admisiones`)
        .then((response) => response.json())
        .then((admisiones) => {
            console.log(admisiones);
            if (admisiones.length > 0) {
                admisiones.forEach((dato) => {
                    let elemTr = document.createElement("tr");
                    let elemTd1 = document.createElement("td");
                    let elemTd2 = document.createElement("td");
                    elemTd1.innerHTML = `${dato.nombreAdmision}`;
                    elemTd2.innerHTML = `
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                        data-bs-target="#exampleModal2" id="${dato.id}" op="Editar" dato="${dato.nombreAdmision}" onclick="buscarAdmision(${dato.id})">
                        Editar<i class="bi bi-pencil"></i>
                    </button>
                    <a class="btn btn-danger" data-bs-toggle="modal"
                        data-bs-target="#exampleModal" id="${dato.id}"
                        dato="${dato.nombreAdmision}" op="Borrar"
                        url="/admisiones-borrar/${dato.id}">
                        Borrar<i class="bi bi-trash"></i>
                    </a>`;
                    elemTr.appendChild((elemTd1));
                    elemTr.appendChild((elemTd2));
                    tabla.appendChild(elemTr);
                });
            }
    });
}


async function buscarAdmision(id) {
    try {
        let response = await fetch (`${API_URL}/api/admisiones-editar/${id}`);
        let admision = await response.json();
        console.log(admision);
        return admision;
    } catch (error) {
        console.error(error);
        throw error; 
    }
}


async function enviarFrmAdd() {
    let admi = document.getElementById('nombreAdmision').value;
    let act = document.getElementById('activo').checked;
     let id = document.getElementById('idA').value;

    const params = {
        nombreAdmision: admi,
        activo: act,
        id: id 
    };

    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(params)
    };

    try {
        const response = await fetch(`${API_URL}/guardar-admision`, options);

        if (response.ok) {
            //alert(`${admi} guardado correctamente`);
            listarAdmisiones();
            location.href = "/admisiones";
        } else {
            console.log(response);
            alert('Hubo un error, revisa los datos');
        }
    } catch (exception) {
        console.log(exception);
    }
}

function eliminarRegistro(id) {
        // Realiza una solicitud al servidor para eliminar el registro con el ID proporcionado
        const options = {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
        };

        fetch(`${API_URL}/admisiones-borrar/${id}`, options)
            .then(response => {
                if (response.ok) {
                    //alert(`Registro eliminado correctamente`);
                    listarAdmisiones();
                    location.href = "/admisiones";
                } else {
                    console.log(response);
                    //alert('Hubo un error al intentar eliminar la admision');
                }
            })
            .catch(error => {
                console.error(error);
            });
    }





