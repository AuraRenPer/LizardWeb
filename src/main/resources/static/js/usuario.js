const API_URL = "http://localhost:8080";

function entrarUsuario() {
    console.log("entrando...");
    listarUsuario();
}

async function listarUsuario() {
    const HTMLResponse = document.querySelector("#tablaOcupa");
    await fetch(`${API_URL}/api/usuario`)
            .then((response) => response.text())
            .then((datos) => {
                let elemBody = document.getElementById("body");
                let elemBodyNuevo = document.createElement("tbody");
                elemBodyNuevo.innerHTML = datos;
                elemBody.replaceWith(elemBodyNuevo);
            }).catch(error => { 
                console.log(error);
            });
}