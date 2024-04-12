const API_URL = "http://localhost:8080";

function entrar() {
    console.log("entrando...");
    listarOfertaEducativa();
}

async function listarOfertaEducativa() {
    const HTMLResponse = document.querySelector("#tablaOcupa");
    await fetch(`${API_URL}/api/oferta-educativa`)
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